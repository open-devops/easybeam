import { Route } from '@angular/router';

import { LogsComponent } from './logs.component';

import { UserRouteAccessService } from '../../shared';

export const logsRoute: Route = {
  path: 'logs',
  component: LogsComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'logs.title',
    toolbarTitle: 'global.menu.admin.main'
  },
  canActivate: [UserRouteAccessService]
};
