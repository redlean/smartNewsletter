import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartNewsSharedModule } from '../../shared';
import {
    TacheService,
    TachePopupService,
    TacheComponent,
    TacheDetailComponent,
    TacheDialogComponent,
    TachePopupComponent,
    TacheDeletePopupComponent,
    TacheDeleteDialogComponent,
    tacheRoute,
    tachePopupRoute,
    TacheResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tacheRoute,
    ...tachePopupRoute,
];

@NgModule({
    imports: [
        SmartNewsSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TacheComponent,
        TacheDetailComponent,
        TacheDialogComponent,
        TacheDeleteDialogComponent,
        TachePopupComponent,
        TacheDeletePopupComponent,
    ],
    entryComponents: [
        TacheComponent,
        TacheDialogComponent,
        TachePopupComponent,
        TacheDeleteDialogComponent,
        TacheDeletePopupComponent,
    ],
    providers: [
        TacheService,
        TachePopupService,
        TacheResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsTacheModule {}
