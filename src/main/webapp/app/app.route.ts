import { Routes, RouterModule } from '@angular/router';

import { MainComponent } from './layouts';
import { PortalComponent } from './portal';

import { LAYOUT_ROUTES } from './layouts';
import { HOME_ROUTE } from './home';
import { ACCOUNT_ROUTES } from './account';
import { ADMIN_ROUTES } from './admin';
import { DASHBOARD_ROUTES } from './portal';
import { PROJECT_MGR_ROUTES } from './project-mgr';

import { UserRouteAccessService } from './shared';

const routes: Routes = [
    {
        path: 'home',
        component: MainComponent,
        children: [
            ...HOME_ROUTE,
            ...LAYOUT_ROUTES
        ]
    },
    {
        path: 'portal',
        component: PortalComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        children: [
            ...DASHBOARD_ROUTES,
            ...PROJECT_MGR_ROUTES,
            ...ACCOUNT_ROUTES,
            ...ADMIN_ROUTES
        ]
    },
    {
        path: '', redirectTo: '/home', pathMatch: 'full'
    }
];

export const appRoutingProviders: any[] = [];

export const appRoutes: any = RouterModule.forRoot(routes, { useHash: true });
