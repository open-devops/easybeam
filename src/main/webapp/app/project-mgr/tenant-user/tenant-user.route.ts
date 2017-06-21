import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TenantUserComponent, TenantUserFormComponent } from '.';

@Injectable()
export class TenantUserResolvePagingParams implements Resolve<any> {

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

export const TENANT_USER_ACTION_ROUTE = [
    {
        path: 'add',
        component: TenantUserFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantUser.home.title',
            toolbarTitle: 'easybeam.tenantUser.home.toolbar',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TenantUserFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantUser.home.title',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TenantUserFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantUser.home.title',
            editable: false
        },
        canActivate: [UserRouteAccessService]
    }
];

export const TENANT_USERS_ROUTES = [{
    path: 'users',
    resolve: {
        'pagingParams': TenantUserResolvePagingParams
    },
    canActivate: [UserRouteAccessService],
    children: [
        {
            path: '',
            component: TenantUserComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easybeam.tenantUser.home.title',
                toolbarTitle: 'easybeam.tenantUser.home.toolbar'
            },
            canActivate: [UserRouteAccessService],
        },
        ...TENANT_USER_ACTION_ROUTE
    ]}
];
