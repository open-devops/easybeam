import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TenantGroupComponent, TenantGroupFormComponent } from '.';

@Injectable()
export class TenantGroupResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

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

export const TENANT_GROUP_ACTION_ROUTE = [
    {
        path: 'add',
        component: TenantGroupFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantGroup.home.title',
            toolbarTitle: 'easybeam.tenantGroup.home.toolbar',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TenantGroupFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantGroup.home.title',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TenantGroupFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.tenantGroup.home.title',
            editable: false
        },
        canActivate: [UserRouteAccessService]
    }
];

export const TENANT_GROUPS_ROUTES = [{
    path: 'groups',
    resolve: {
        'pagingParams': TenantGroupResolvePagingParams
    },
    canActivate: [UserRouteAccessService],
    children: [
        {
            path: '',
            component: TenantGroupComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easybeam.tenantGroup.home.title',
                toolbarTitle: 'easybeam.tenantGroup.home.toolbar'
            },
            canActivate: [UserRouteAccessService],
        },
        ...TENANT_GROUP_ACTION_ROUTE
    ]}
];
