import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartNewsletterSharedModule } from '../../shared';
import {
    Planification_emailsService,
    Planification_emailsPopupService,
    Planification_emailsComponent,
    Planification_emailsDetailComponent,
    Planification_emailsDialogComponent,
    Planification_emailsPopupComponent,
    Planification_emailsDeletePopupComponent,
    Planification_emailsDeleteDialogComponent,
    planification_emailsRoute,
    planification_emailsPopupRoute,
    Planification_emailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...planification_emailsRoute,
    ...planification_emailsPopupRoute,
];

@NgModule({
    imports: [
        SmartNewsletterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        Planification_emailsComponent,
        Planification_emailsDetailComponent,
        Planification_emailsDialogComponent,
        Planification_emailsDeleteDialogComponent,
        Planification_emailsPopupComponent,
        Planification_emailsDeletePopupComponent,
    ],
    entryComponents: [
        Planification_emailsComponent,
        Planification_emailsDialogComponent,
        Planification_emailsPopupComponent,
        Planification_emailsDeleteDialogComponent,
        Planification_emailsDeletePopupComponent,
    ],
    providers: [
        Planification_emailsService,
        Planification_emailsPopupService,
        Planification_emailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsletterPlanification_emailsModule {}
