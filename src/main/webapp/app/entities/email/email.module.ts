import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartNewsSharedModule } from '../../shared';
import {
    EmailService,
    EmailPopupService,
    EmailComponent,
    EmailDetailComponent,
    EmailDialogComponent,
    EmailPopupComponent,
    EmailDeletePopupComponent,
    EmailDeleteDialogComponent,
    emailRoute,
    emailPopupRoute,
    EmailResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...emailRoute,
    ...emailPopupRoute,
];

@NgModule({
    imports: [
        SmartNewsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmailComponent,
        EmailDetailComponent,
        EmailDialogComponent,
        EmailDeleteDialogComponent,
        EmailPopupComponent,
        EmailDeletePopupComponent,
    ],
    entryComponents: [
        EmailComponent,
        EmailDialogComponent,
        EmailPopupComponent,
        EmailDeleteDialogComponent,
        EmailDeletePopupComponent,
    ],
    providers: [
        EmailService,
        EmailPopupService,
        EmailResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsEmailModule {}
