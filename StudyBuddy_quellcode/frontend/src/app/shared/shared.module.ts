import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { TranslateModule, TranslatePipe } from '@ngx-translate/core';
import { ModuleService } from './services/moduleService';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { SpecialisationService } from './services/specialisationService';
import { StudyPlanService } from './services/studyPlanService';
import { StudyProgramService } from './services/studyProgramService';
import { ValidatorService } from './services/validatorService';
import { PopoverModule } from 'ngx-bootstrap/popover';

@NgModule({
    imports: [
        CommonModule,
        TypeaheadModule,
        FormsModule,
        TranslateModule,
        RouterModule,
        HttpClientModule,
        CollapseModule,
        PopoverModule],
    declarations: [
    ],
    exports: [
        CommonModule,
        TypeaheadModule,
        FormsModule,
        TranslateModule,
        RouterModule,
        CollapseModule,
        PopoverModule],
    providers: [
        TranslatePipe,
        ModuleService,
        SpecialisationService,
        StudyPlanService,
        StudyProgramService,
        ValidatorService
    ]
})
export class SharedModule { }