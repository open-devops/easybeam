import { Route } from '@angular/router';

import { JhiConfigurationComponent } from './configuration.component';

import { UserRouteAccessService } from '../../shared';

export const configurationRoute: Route = {
  path: 'jhi-configuration',
  component: JhiConfigurationComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'configuration.title',
    toolbarTitle: 'global.menu.admin.main'
  },
  canActivate: [UserRouteAccessService]
};
