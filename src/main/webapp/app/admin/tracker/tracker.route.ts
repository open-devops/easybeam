import { Route } from '@angular/router';

import { JhiTrackerComponent } from './tracker.component';
import { JhiTrackerService, Principal, UserRouteAccessService } from '../../shared';

export const trackerRoute: Route = {
  path: 'jhi-tracker',
  component: JhiTrackerComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'tracker.title',
    toolbarTitle: 'global.menu.admin.main'
  },
  canActivate: [UserRouteAccessService]
};
