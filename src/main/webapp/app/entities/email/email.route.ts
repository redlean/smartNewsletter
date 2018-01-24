import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EmailComponent } from './email.component';
import { EmailDetailComponent } from './email-detail.component';
import { EmailPopupComponent } from './email-dialog.component';
import { EmailDeletePopupComponent } from './email-delete-dialog.component';

@Injectable()
export class EmailResolvePagingParams implements Resolve<any> {

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

export const emailRoute: Routes = [
    {
        path: 'email',
        component: EmailComponent,
        resolve: {
            'pagingParams': EmailResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'email/:id',
        component: EmailDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailPopupRoute: Routes = [
    {
        path: 'email-new',
        component: EmailPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.email.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email/:id/edit',
        component: EmailPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.email.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email/:id/delete',
        component: EmailDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.email.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
