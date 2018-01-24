import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { Planification_emailsComponent } from './planification-emails.component';
import { Planification_emailsDetailComponent } from './planification-emails-detail.component';
import { Planification_emailsPopupComponent } from './planification-emails-dialog.component';
import { Planification_emailsDeletePopupComponent } from './planification-emails-delete-dialog.component';

@Injectable()
export class Planification_emailsResolvePagingParams implements Resolve<any> {

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

export const planification_emailsRoute: Routes = [
    {
        path: 'planification-emails',
        component: Planification_emailsComponent,
        resolve: {
            'pagingParams': Planification_emailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.planification_emails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'planification-emails/:id',
        component: Planification_emailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.planification_emails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planification_emailsPopupRoute: Routes = [
    {
        path: 'planification-emails-new',
        component: Planification_emailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.planification_emails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'planification-emails/:id/edit',
        component: Planification_emailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.planification_emails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'planification-emails/:id/delete',
        component: Planification_emailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.planification_emails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
