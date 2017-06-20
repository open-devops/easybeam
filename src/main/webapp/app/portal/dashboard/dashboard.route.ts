import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DashboardComponent } from './dashboard.component';

export const dashboardRoutes: Routes = [{
  path: '',
  component: DashboardComponent,
  data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dashboard.title',
      toolbarTitle: 'dashboard.title'
  },
  canActivate: [UserRouteAccessService]
}];
