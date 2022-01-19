import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HeaderModule } from './header/header.module';
import { HomeModule } from './home/home.module';
import { AppRoutingModule } from './app-routing.module';
import { ImpressumModule } from './impressum/impressum.module';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { SharedModule } from './shared/shared.module';
import de from "./translations.de.json";
import { StudyPlanModule } from './studyplan/studyplan.module';
import { HttpWrapper } from './http.wrapper';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        TranslateModule.forRoot({
            defaultLanguage: 'de'
        }),
        SharedModule,
        AppRoutingModule,
        HeaderModule,
        StudyPlanModule,
        HomeModule,
        ImpressumModule,
        BrowserAnimationsModule,
    ],
    providers: [HttpWrapper],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(
        translateService: TranslateService
    ){
        translateService.setTranslation("de", de, true);
    }
 }