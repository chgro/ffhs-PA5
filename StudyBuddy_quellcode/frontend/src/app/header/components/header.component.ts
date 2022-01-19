import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {StudyPlanService} from 'src/app/shared/services/studyPlanService';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isCollapsed = false;
  studyPlanName: string;

  constructor(
    private router: Router) {
  }

  ngOnInit(): void {
  }

  loadStudyPlan() {
    if (this.studyPlanName.length > 0) {
      this.router.navigate(["studyplan/edit"], {queryParams: {name: this.studyPlanName}});
    } else {
      // ToDo: Fehlerausgabe an User: neuer Studienplan konnte nicht angelegt werden
      console.log("error");
    }
  }
}
