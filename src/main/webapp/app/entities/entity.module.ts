import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

import { SmartNewsEmailModule } from './email/email.module';
import { SmartNewsPlanificationEmailsModule } from './planification-emails/planification-emails.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
import { BrowserModule } from '@angular/platform-browser';

import { FormsModule } from '@angular/forms';
import { FileUploadModule } from 'ng2-file-upload';
@NgModule({
    imports: [
        SmartNewsEmailModule,
        SmartNewsPlanificationEmailsModule,
        BrowserModule, FormsModule, FileUploadModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsEntityModule {}
