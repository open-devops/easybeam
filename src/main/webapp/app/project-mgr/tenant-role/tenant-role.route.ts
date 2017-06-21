import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TenantRoleComponent, TenantRoleFormComponent } from '.';

@Injectable()
export class TenantRoleResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,desc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const TENANT_ROLE_ACTION_ROUTE = [
    {
        path: 'add',
        component: TenantRoleFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantRole.home.title',
            toolbarTitle: 'easybeam.tenantRole.home.toolbar',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TenantRoleFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantRole.home.title',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TenantRoleFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantRole.home.title',
            editable: false
        },
        canActivate: [UserRouteAccessService]
    }
];

export const TENANT_ROLES_ROUTES = [{
    path: 'roles',
    resolve: {
        'pagingParams': TenantRoleResolvePagingParams
    },
    canActivate: [UserRouteAccessService],
    children: [
        {
            path: '',
            component: TenantRoleComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easybeam.tenantRole.home.title',
                toolbarTitle: 'easybeam.tenantRole.home.toolbar'
            },
            canActivate: [UserRouteAccessService],
        },
        ...TENANT_ROLE_ACTION_ROUTE
    ]}
];
