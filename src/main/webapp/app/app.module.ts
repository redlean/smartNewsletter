import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';
import { ImageUploadModule } from "angular2-image-upload";
import { SmartNewsletterSharedModule, UserRouteAccessService } from './shared';
import { SmartNewsletterAppRoutingModule} from './app-routing.module';
import { SmartNewsletterHomeModule } from './home/home.module';
import { SmartNewsletterAdminModule } from './admin/admin.module';
import { SmartNewsletterAccountModule } from './account/account.module';
import { SmartNewsletterEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { CKEditorModule } from 'ng2-ckeditor';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';


@NgModule({
    imports: [
        BrowserModule,
        SmartNewsletterAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        SmartNewsletterSharedModule,
        SmartNewsletterHomeModule,
        SmartNewsletterAdminModule,
        SmartNewsletterAccountModule,
        SmartNewsletterEntityModule,
        ImageUploadModule.forRoot(),
        CKEditorModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class SmartNewsletterAppModule {}
