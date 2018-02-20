import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SmartNewsletterEmailModule } from './email/email.module';
import { SmartNewsletterPlanification_emailsModule } from './planification-emails/planification-emails.module';
import { SmartNewsletterCompteConfigModule } from './compte-config/compte-config.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SmartNewsletterEmailModule,
        SmartNewsletterPlanification_emailsModule,
        SmartNewsletterCompteConfigModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsletterEntityModule {}
