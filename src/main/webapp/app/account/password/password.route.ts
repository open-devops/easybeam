import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PasswordComponent } from './password.component';

export const passwordRoute: Route = {
  path: 'password',
  component: PasswordComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'global.menu.account.password',
    toolbarTitle: 'global.menu.account.main'
  },
  canActivate: [UserRouteAccessService]
};
