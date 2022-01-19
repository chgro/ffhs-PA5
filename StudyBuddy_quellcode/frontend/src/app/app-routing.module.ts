import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/components/home.component';
import { ImpressumComponent } from './impressum/components/impressum.component';
import { StudyPlanComponent } from './studyplan/components/edit/studyplan.component';
import { StudyPlanStartComponent } from './studyplan/components/add/studyplanStart.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },
    { path: 'studyplan/add', component: StudyPlanStartComponent },
    { path: 'studyplan/edit', component: StudyPlanComponent },
    { path: 'impressum', component: ImpressumComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
