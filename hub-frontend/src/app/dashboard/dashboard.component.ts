import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../services/dashBoardService/dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  insights: any;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getDashboardInsights().subscribe((data) => {
      this.insights = data;
    });
  }
}
