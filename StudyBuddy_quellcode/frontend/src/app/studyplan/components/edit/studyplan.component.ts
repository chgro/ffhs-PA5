import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, combineLatest, Observable, ReplaySubject, Subscription } from 'rxjs';
import { Module, NewModulePlan as AddModuleToStudyPlanRequest, Relevance, StudyPlan } from 'src/app/shared/backendTypes';
import { ModuleService } from 'src/app/shared/services/moduleService';
import { ModuleTypeConstans } from 'src/app/shared/moduleTypeConstants';
import { SpecialisationService } from 'src/app/shared/services/specialisationService';
import { debounceTime, map } from 'rxjs/operators';
import { StudyPlanService } from 'src/app/shared/services/studyPlanService';
import { TypeaheadConfig } from 'ngx-bootstrap/typeahead';
import { TranslateService } from '@ngx-translate/core';
import { SemesterTypeConstants } from 'src/app/shared/semesterTypeConstants';

export function getTypeaheadConfig(): TypeaheadConfig {
    return Object.assign(new TypeaheadConfig(), { cancelRequestOnFocusLost: true });
}

@Component({
    selector: 'app-StudyPlan',
    templateUrl: './studyplan.component.html',
    styleUrls: ['./studyplan.component.scss'],
    providers: [{ provide: TypeaheadConfig, useFactory: getTypeaheadConfig }],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class StudyPlanComponent implements OnInit, OnDestroy {
    private subscription: Subscription[] = [];
    public moduleTypeConstants = ModuleTypeConstans;

    public specialisationChoice: string = "NONE";
    public specialisations: Observable<string[]>;
    public semesterChoice: number;
    public semesters: number[] = Array.from([5, 6, 7, 8, 9, 10])
    public semesterType: string[] = Object.values(SemesterTypeConstants)
    public semesterTypeChoice: string;
    public yearChoice: number = new Date(Date.now()).getFullYear();
    public modules: BehaviorSubject<Module[]> = new BehaviorSubject<Module[]>([]);
    public studyPlanIdFromParams: string;
    public studyPlanName: string;
    public studyPlan: ReplaySubject<StudyPlan> = new ReplaySubject<StudyPlan>();
    public totalECTS: Observable<number>;
    public totalSECURITY: Observable<number>;
    public totalDATASCIENCE: Observable<number>;
    public totalENTERPRISECOMPUTING: Observable<number>;

    constructor(
        private route: ActivatedRoute,
        private specialisationService: SpecialisationService,
        private moduleService: ModuleService,
        private studyPlanService: StudyPlanService,
        private router: Router,
        private translateService: TranslateService) {
    }

    ngOnInit(): void {
        // Route Params
        this.subscription.push(
            this.route.queryParams
                .subscribe(async params => {
                    this.studyPlanIdFromParams = params.id;
                    this.studyPlanName = params.name;
                    this.getStudyPlan();
                }
                ));

        // Specialisations from Backend
        this.specialisations = this.specialisationService.getAllSpecialisations();
        this.getModules();
    }

    ngOnDestroy(): void {
        this.subscription?.forEach(x => x.unsubscribe());
    }


    getStudyPlan() {
        if (this.studyPlanIdFromParams != undefined && this.studyPlanIdFromParams.length > 0) {
            const studyPlanObs = this.studyPlanService.getStudyPlanById(this.studyPlanIdFromParams);
            studyPlanObs.subscribe(x => {
                this.studyPlan.next(x);
            })
        } else if (this.studyPlanName != undefined && this.studyPlanName.length > 0) {
            const studyPlanObs = this.studyPlanService.getStudyPlanByName(this.studyPlanName);
            studyPlanObs.subscribe(x => {
                this.studyPlan.next(x);

            })
        }
        this.studyPlan.subscribe(studyPlan => {
            this.totalECTS = this.studyPlanService.getTotalEcts(studyPlan.id);
            this.totalDATASCIENCE = this.studyPlanService.getTotalRelevanceBySpecialisation(studyPlan.id, 'DATASCIENCE');
            this.totalENTERPRISECOMPUTING = this.studyPlanService.getTotalRelevanceBySpecialisation(studyPlan.id, 'ENTERPRISECOMPUTING');
            this.totalSECURITY = this.studyPlanService.getTotalRelevanceBySpecialisation(studyPlan.id, 'SECURITY');
        });
    }

    public onYearChange = ($event: any) => {
        const year: number = $event;
        this.yearChoice = year;
        this.getModules();
    }

    public onSemesterTypeChange = ($event: any) => {
        const semesterType: string = $event;
        this.semesterTypeChoice = semesterType;
        this.getModules();
    }

    public onSpecialisationChange = ($event: any) => {
        const specialisation: string = $event;
        this.specialisationChoice = specialisation;

        this.getModules();
    }

    public getModules() {
        this.subscription.push(
            this.moduleService.getModulesByFilter(this.specialisationChoice, this.yearChoice, this.semesterTypeChoice).subscribe(
                modules => this.modules.next(modules)
            ));
    }

    public addModuleToStudyPlan = (studyPlanId: string, module: Module) => {
        this.subscription.push(
            this.studyPlanService.canAddModuleToStudyPlan(studyPlanId, module.id).subscribe(
                response => {
                    if (this.semesterTypeChoice == SemesterTypeConstants.all) {
                        const message = this.translateService.instant("ADDMODULEFAILUREBECAUSEALLISSELECTED")
                        alert(message);
                    }
                    else if (response == true) {
                        if (this.semesterChoice == undefined || this.semesterTypeChoice == undefined || this.yearChoice == 0) {
                            const message = this.translateService.instant("ADDMODULEFAILUREBECAUSEINCOMPLETE")
                            alert(message);
                        } else {
                            const modulePlan = new AddModuleToStudyPlanRequest(
                                module.id,
                                this.specialisationChoice,
                                this.semesterChoice,
                                this.semesterTypeChoice,
                                this.yearChoice);

                            this.studyPlanService.addModuleToStudyPlan(studyPlanId, modulePlan).then(() => this.getStudyPlan());
                        }
                    } else {
                        const message = this.translateService.instant("ADDMODULEFAILUREBECAUSEALREADYADDED")
                        alert(message);
                    }
                }
            )
        )
    }

    public removeModuleFromStudyPlan = (studyPlanId: string, moduleId: number) => {
        this.studyPlanService.removeModuleFromStudyPlan(studyPlanId, moduleId).then(() => this.getStudyPlan());
    }

    public deleteStudyPlan = (id: string) => {
        if (confirm("Soll der Studienplan wirklich gelöscht werden?")) {
            var res = this.studyPlanService.deleteStudyPlan(id);
            res.then(x => console.log(x));
            this.router.navigate(["studyplan/add"]);
        }
    }

    public searchModule = (text$: Observable<string>): Observable<Module[]> => {
        const debouncedText$ = text$.pipe(debounceTime(200));


        return combineLatest([debouncedText$, this.modules]).pipe(
            map(([term, modules]) =>
            (term === '' ? modules.slice(0, 10)
                : modules.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1)))
        );
    }

    public getRelevancePointsByRelevance = (relevance: Relevance[], specialisazion: string) => {
        const relPoints = relevance.filter(r => r.specialisation == specialisazion).pop();

        return relPoints == undefined ? null : relPoints.relevancePoint;
    }
}
