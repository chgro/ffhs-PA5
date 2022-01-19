import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ImpressumComponent } from "./components/impressum.component";
@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        ImpressumComponent
    ],
    exports: [
        ImpressumComponent
    ]
})
export class ImpressumModule { }