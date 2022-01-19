import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core";
import {HeaderComponent} from "./components/header.component";
import {RouterModule} from '@angular/router';
import {SharedModule} from "../shared/shared.module";

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    SharedModule
  ],
  declarations: [
    HeaderComponent
  ],
  exports: [
    HeaderComponent
  ]
})
export class HeaderModule {
}
