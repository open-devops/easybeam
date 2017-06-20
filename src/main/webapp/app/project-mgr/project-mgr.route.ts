import { UserRouteAccessService } from '../shared';

import {
    ProjectManagementComponent,
    PROJECTS_ROUTES,
    TENANT_USERS_ROUTES,
    TENANT_ROLES_ROUTES,
    TENANT_GROUPS_ROUTES
} from './';

export const PROJECT_MGR_ROUTES = [{
    path: 'project-mgr',
    component: ProjectManagementComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'easybeam.project.home.title',
        toolbarTitle: 'easybeam.project.home.toolbar'
    },
    canActivate: [UserRouteAccessService],
    children: [
        ...PROJECTS_ROUTES,
        ...TENANT_USERS_ROUTES,
        ...TENANT_ROLES_ROUTES,
        ...TENANT_GROUPS_ROUTES
    ]
}];
