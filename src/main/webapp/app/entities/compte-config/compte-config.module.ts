import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartNewsletterSharedModule } from '../../shared';
import {
    CompteConfigService,
    CompteConfigPopupService,
    CompteConfigComponent,
    CompteConfigDetailComponent,
    CompteConfigDialogComponent,
    CompteConfigPopupComponent,
    CompteConfigDeletePopupComponent,
    CompteConfigDeleteDialogComponent,
    compteConfigRoute,
    compteConfigPopupRoute,
} from './';

const ENTITY_STATES = [
    ...compteConfigRoute,
    ...compteConfigPopupRoute,
];

@NgModule({
    imports: [
        SmartNewsletterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompteConfigComponent,
        CompteConfigDetailComponent,
        CompteConfigDialogComponent,
        CompteConfigDeleteDialogComponent,
        CompteConfigPopupComponent,
        CompteConfigDeletePopupComponent,
    ],
    entryComponents: [
        CompteConfigComponent,
        CompteConfigDialogComponent,
        CompteConfigPopupComponent,
        CompteConfigDeleteDialogComponent,
        CompteConfigDeletePopupComponent,
    ],
    providers: [
        CompteConfigService,
        CompteConfigPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsletterCompteConfigModule {}
