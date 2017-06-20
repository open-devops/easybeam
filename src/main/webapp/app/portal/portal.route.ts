import { Routes, RouterModule } from '@angular/router';

import { UserRouteAccessService } from '../shared';

import {
    PortalComponent,
    dashboardRoutes
} from './';

export const DASHBOARD_ROUTES = [
    ...dashboardRoutes
];
