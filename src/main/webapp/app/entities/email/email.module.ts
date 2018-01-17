import {NgModule, CUSTOM_ELEMENTS_SCHEMA, Directive} from '@angular/core';
import { RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
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
import {SafeHtmlPipe} from './email-dialog.component';
// import {FileSelectDirective, FileUploader} from 'ng2-file-upload';

const ENTITY_STATES = [
    ...emailRoute,
    ...emailPopupRoute,
];

@NgModule({
    imports: [
        SmartNewsSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        BrowserModule, FormsModule,
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
export class SmartNewsEmailModule {}
