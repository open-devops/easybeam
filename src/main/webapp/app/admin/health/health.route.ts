import { Route } from '@angular/router';

import { JhiHealthCheckComponent } from './health.component';

import { UserRouteAccessService } from '../../shared';

export const healthRoute: Route = {
  path: 'jhi-health',
  component: JhiHealthCheckComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'health.title',
    toolbarTitle: 'global.menu.admin.main'
  },
  canActivate: [UserRouteAccessService]
};
