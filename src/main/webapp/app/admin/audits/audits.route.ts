import { Route } from '@angular/router';

import { AuditsComponent } from './audits.component';

import { UserRouteAccessService } from '../../shared';

export const auditsRoute: Route = {
  path: 'audits',
  component: AuditsComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'audits.title',
    toolbarTitle: 'global.menu.admin.main'
  },
  canActivate: [UserRouteAccessService]
};
