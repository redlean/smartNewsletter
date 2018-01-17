import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartNewsSharedModule } from '../../shared';
import {
    PlanificationEmailsService,
    PlanificationEmailsPopupService,
    PlanificationEmailsComponent,
    PlanificationEmailsDetailComponent,
    PlanificationEmailsDialogComponent,
    PlanificationEmailsPopupComponent,
    PlanificationEmailsDeletePopupComponent,
    PlanificationEmailsDeleteDialogComponent,
    planificationEmailsRoute,
    planificationEmailsPopupRoute,
    PlanificationEmailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...planificationEmailsRoute,
    ...planificationEmailsPopupRoute,
];

@NgModule({
    imports: [
        SmartNewsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlanificationEmailsComponent,
        PlanificationEmailsDetailComponent,
        PlanificationEmailsDialogComponent,
        PlanificationEmailsDeleteDialogComponent,
        PlanificationEmailsPopupComponent,
        PlanificationEmailsDeletePopupComponent,
    ],
    entryComponents: [
        PlanificationEmailsComponent,
        PlanificationEmailsDialogComponent,
        PlanificationEmailsPopupComponent,
        PlanificationEmailsDeleteDialogComponent,
        PlanificationEmailsDeletePopupComponent,
    ],
    providers: [
        PlanificationEmailsService,
        PlanificationEmailsPopupService,
        PlanificationEmailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsPlanificationEmailsModule {}
