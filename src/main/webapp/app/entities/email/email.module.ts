import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartNewsletterSharedModule } from '../../shared';
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
import { CKEditorModule } from 'ng2-ckeditor';
import {SafeHtmlPipe} from "./email-detail.component";
const ENTITY_STATES = [
    ...emailRoute,
    ...emailPopupRoute,
];

@NgModule({
    imports: [
        SmartNewsletterSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        CKEditorModule
    ],
    declarations: [
        EmailComponent,
        EmailDetailComponent,
        EmailDialogComponent,
        EmailDeleteDialogComponent,
        EmailPopupComponent,
        EmailDeletePopupComponent,
        SafeHtmlPipe
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
export class SmartNewsletterEmailModule {}
