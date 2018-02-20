import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompteConfigComponent } from './compte-config.component';
import { CompteConfigDetailComponent } from './compte-config-detail.component';
import { CompteConfigPopupComponent } from './compte-config-dialog.component';
import { CompteConfigDeletePopupComponent } from './compte-config-delete-dialog.component';

export const compteConfigRoute: Routes = [
    {
        path: 'compte-config',
        component: CompteConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.compteConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'compte-config/:id',
        component: CompteConfigDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.compteConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compteConfigPopupRoute: Routes = [
    {
        path: 'compte-config-new',
        component: CompteConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.compteConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte-config/:id/edit',
        component: CompteConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.compteConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte-config/:id/delete',
        component: CompteConfigDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'smartNewsletterApp.compteConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
