import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Planification_emails } from './planification-emails.model';
import { Planification_emailsPopupService } from './planification-emails-popup.service';
import { Planification_emailsService } from './planification-emails.service';
import { Email, EmailService } from '../email';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-planification-emails-dialog',
    templateUrl: './planification-emails-dialog.component.html'
})

export class Planification_emailsDialogComponent implements OnInit {

    planification_emails: Planification_emails;
    isSaving: boolean;

    emails: Email[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private planification_emailsService: Planification_emailsService,
        private emailService: EmailService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.planification_emails.status = 'Non envoyée';
        this.isSaving = false;
        this.emailService.query()
            .subscribe((res: ResponseWrapper) => { this.emails = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.planification_emails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.planification_emailsService.update(this.planification_emails));
        } else {
            this.subscribeToSaveResponse(
                this.planification_emailsService.create(this.planification_emails));
        }
    }

    private subscribeToSaveResponse(result: Observable<Planification_emails>) {
        result.subscribe((res: Planification_emails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Planification_emails) {
        this.eventManager.broadcast({ name: 'planification_emailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
    trackEmailByObject(index: number, item: Email) {
        return item.objet;
    }

    trackEmailById(index: number, item: Email) {
        return item.objet;
    }
}

@Component({
    selector: 'jhi-planification-emails-popup',
    template: ''
})
export class Planification_emailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planification_emailsPopupService: Planification_emailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.planification_emailsPopupService
                    .open(Planification_emailsDialogComponent as Component, params['id']);
            } else {
                this.planification_emailsPopupService
                    .open(Planification_emailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
