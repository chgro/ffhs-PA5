import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Subscription } from 'rxjs';
import { NewStudyPlan as NewStudyPlanRequest } from 'src/app/shared/backendTypes';
import { ProgramConstants } from 'src/app/shared/programConstants';
import { StudyPlanService } from 'src/app/shared/services/studyPlanService';
import { StudyProgramService } from 'src/app/shared/services/studyProgramService';
import { ValidatorService } from 'src/app/shared/services/validatorService';

@Component({
    selector: 'app-StudyPlanStart',
    templateUrl: './studyplanStart.component.html'
})
export class StudyPlanStartComponent implements OnInit, OnDestroy {

    public studyProgramChoice: string;
    public studyPrograms: string[];

    public semesters: number[] = Array.from({ length: 10 }, (v, i) => i + 1)

    public planName: string;
    public validPlanName: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private subscriptions: Subscription[] = [];

    constructor(
        private router: Router,
        private studyProgramService: StudyProgramService,
        private validatorService: ValidatorService,
        private studyPlanService: StudyPlanService) {
    }

    ngOnInit(): void {
        this.subscriptions.push(
            this.studyProgramService.getAllStudyPrograms().subscribe(
                programs => {
                    this.studyPrograms = programs
                }
            ));
    }

    ngOnDestroy(): void {
        this.subscriptions?.forEach(x => x.unsubscribe);
    }


    public programChanged = ($event: any) => {
        const name: string = $event;
        this.studyProgramChoice = name;

        if (name == ProgramConstants.passarelle) {
            this.semesters = [5, 6, 7, 8, 9, 10]
        } else {
            this.semesters = Array.from({ length: 10 }, (v, i) => i + 1)
        }
    }

    public checkName = ($event: any) => {
        const studyPlanName: string = $event;

        this.subscriptions.push(
            this.validatorService.validateStudyPlan(studyPlanName).subscribe(
                response => {
                    if (response == true) {
                        this.validPlanName.next(true);
                        this.planName = studyPlanName;
                    } else {
                        this.validPlanName.next(false);
                    }
                }
            ));
    }

    public createNewStudyPlan = () => {
        const newStudyPlanRequest = new NewStudyPlanRequest(
            this.planName,
            new Date(),
            this.studyProgramChoice
        );
        const studyPlan = this.studyPlanService.addNewStudyPlan(newStudyPlanRequest);
        studyPlan.subscribe(newStudyPlan => {
            if (newStudyPlan.id.length > 0) {
                this.router.navigate(["studyplan/edit"], { queryParams: { id: newStudyPlan.id } });
            } else {
                // ToDo: Fehlerausgabe an User: neuer Studienplan konnte nicht angelegt werden
                console.log("error");
            }
        });
    }
}
