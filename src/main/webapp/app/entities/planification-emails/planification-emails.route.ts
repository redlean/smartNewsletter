import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlanificationEmailsComponent } from './planification-emails.component';
import { PlanificationEmailsDetailComponent } from './planification-emails-detail.component';
import { PlanificationEmailsPopupComponent } from './planification-emails-dialog.component';
import { PlanificationEmailsDeletePopupComponent } from './planification-emails-delete-dialog.component';

@Injectable()
export class PlanificationEmailsResolvePagingParams implements Resolve<any> {

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

export const planificationEmailsRoute: Routes = [
    {
        path: 'planification-emails',
        component: PlanificationEmailsComponent,
        resolve: {
            'pagingParams': PlanificationEmailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.planificationEmails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'planification-emails/:id',
        component: PlanificationEmailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.planificationEmails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planificationEmailsPopupRoute: Routes = [
    {
        path: 'planification-emails-new',
        component: PlanificationEmailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.planificationEmails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'planification-emails/:id/edit',
        component: PlanificationEmailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.planificationEmails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'planification-emails/:id/validate',
        component: PlanificationEmailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.planificationEmails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'planification-emails/:id/delete',
        component: PlanificationEmailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsApp.planificationEmails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
