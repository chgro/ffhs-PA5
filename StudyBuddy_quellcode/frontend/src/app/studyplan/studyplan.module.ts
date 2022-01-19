import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { SharedModule } from "../shared/shared.module";
import de from "../translations.de.json";
import { StudyPlanComponent } from "./components/edit/studyplan.component";
import { StudyPlanStartComponent } from "./components/add/studyplanStart.component";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
    ],
    declarations: [
        StudyPlanStartComponent,
        StudyPlanComponent,
    ],
    exports: [
    ],
    providers: [
    ]
})
export class StudyPlanModule {
    constructor(translateService: TranslateService) {
        translateService.setTranslation("de", de, true);
    }
}