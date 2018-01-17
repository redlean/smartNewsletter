import {Component, OnInit, OnDestroy, PipeTransform, Pipe} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Email } from './email.model';
import { EmailPopupService } from './email-popup.service';
import { EmailService } from './email.service';
import {FileUploader, FileSelectDirective} from 'ng2-file-upload';
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';

import { DomSanitizer } from '@angular/platform-browser'

@Pipe({ name: 'safeHtml'})
export class SafeHtmlPipe implements PipeTransform  {
    constructor(private sanitized: DomSanitizer) {}
    transform(value) {
        return this.sanitized.bypassSecurityTrustHtml(value);
        // return this.sanitizer.bypassSecurityTrustStyle(value);
        // return this.sanitizer.bypassSecurityTrustHtml(value);
        // return this.sanitizer.bypassSecurityTrustXxx(value);
    }
}

@Component({
    selector: 'jhi-email-dialog',
    templateUrl: './email-dialog.component.html'
})
export class EmailDialogComponent implements OnInit {
    email: Email;
    isSaving: boolean;
    public uploader: FileUploader = new FileUploader({url: 'http://localhost:9000/upload'});
    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private emailService: EmailService,
        private eventManager: JhiEventManager
    ) {
    }
    // public fileOverBase(e: any): void {
    //     this.hasBaseDropZoneOver = e;
    // }
    //
    // public fileOverAnother(e: any): void {
    //     this.hasAnotherDropZoneOver = e;
    // }
    ngOnInit() {
        this.isSaving = false;
        // this.uploader = new FileUploader({url: `YOUR URL`});
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.email.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emailService.update(this.email));
        } else {
            this.subscribeToSaveResponse(
                this.emailService.create(this.email));
        }
    }

    private subscribeToSaveResponse(result: Observable<Email>) {
        result.subscribe((res: Email) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Email) {
        this.eventManager.broadcast({ name: 'emailListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-email-popup',
    template: ''
})
export class EmailPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailPopupService: EmailPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.emailPopupService
                    .open(EmailDialogComponent as Component, params['id']);
            } else {
                this.emailPopupService
                    .open(EmailDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

}
