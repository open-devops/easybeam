import { Route } from '@angular/router';

import { JhiMetricsMonitoringComponent } from './metrics.component';

import { UserRouteAccessService } from '../../shared';

export const metricsRoute: Route = {
  path: 'jhi-metrics',
  component: JhiMetricsMonitoringComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'metrics.title',
    toolbarTitle: 'global.menu.admin.main'
  },
  canActivate: [UserRouteAccessService]
};
