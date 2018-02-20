import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartNewsletterSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {DemoModule} from "./calendar/demo.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
    imports: [
        SmartNewsletterSharedModule,
        RouterModule.forChild([ HOME_ROUTE ]),
        DemoModule,
        BrowserAnimationsModule
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartNewsletterHomeModule {}
