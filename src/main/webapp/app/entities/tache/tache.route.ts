import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TacheComponent } from './tache.component';
import { TacheDetailComponent } from './tache-detail.component';
import { TachePopupComponent } from './tache-dialog.component';
import { TacheDeletePopupComponent } from './tache-delete-dialog.component';

@Injectable()
export class TacheResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const tacheRoute: Routes = [
    {
        path: 'tache',
        component: TacheComponent,
        resolve: {
            'pagingParams': TacheResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.tache.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tache/:id',
        component: TacheDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.tache.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tachePopupRoute: Routes = [
    {
        path: 'tache-new',
        component: TachePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.tache.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tache/:id/edit',
        component: TachePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.tache.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tache/:id/delete',
        component: TacheDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.tache.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
