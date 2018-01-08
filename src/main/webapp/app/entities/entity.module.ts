import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


import { SmartNewsTacheModule } from './tache/tache.module';
import { SmartNewsEmailModule } from './email/email.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SmartNewsTacheModule,
        SmartNewsEmailModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsEntityModule {}
