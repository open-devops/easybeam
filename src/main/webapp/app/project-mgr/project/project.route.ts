import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProjectComponent, ProjectFormComponent } from '.';

@Injectable()
export class ProjectResolvePagingParams implements Resolve<any> {

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

export const PROJECT_ACTION_ROUTE = [
    {
        path: 'add',
        component: ProjectFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.project.home.title',
            toolbarTitle: 'easybeam.project.home.toolbar',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProjectFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.project.home.title',
            editable: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProjectFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easybeam.project.home.title',
            editable: false
        },
        canActivate: [UserRouteAccessService]
    }
];

export const PROJECTS_ROUTES = [{
    path: 'projects',
    resolve: {
        'pagingParams': ProjectResolvePagingParams
    },
    canActivate: [UserRouteAccessService],
    children: [
        {
            path: '',
            component: ProjectComponent,
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easybeam.project.home.title',
                toolbarTitle: 'easybeam.project.home.toolbar'
            },
            canActivate: [UserRouteAccessService],
        },
        ...PROJECT_ACTION_ROUTE
    ]}
];
