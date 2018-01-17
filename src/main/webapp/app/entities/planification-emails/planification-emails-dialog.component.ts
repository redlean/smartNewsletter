import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlanificationEmails } from './planification-emails.model';
import { PlanificationEmailsPopupService } from './planification-emails-popup.service';
import { PlanificationEmailsService } from './planification-emails.service';
import { Email, EmailService } from '../email';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-planification-emails-dialog',
    templateUrl: './planification-emails-dialog.component.html'
})
export class PlanificationEmailsDialogComponent implements OnInit {

    planificationEmails: PlanificationEmails;
    isSaving: boolean;

    emails: Email[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private planificationEmailsService: PlanificationEmailsService,
        private emailService: EmailService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.planificationEmails.status = 'Non réalisée';
        this.isSaving = false;
        this.emailService.query()
            .subscribe((res: ResponseWrapper) => { this.emails = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.planificationEmails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.planificationEmailsService.update(this.planificationEmails));
        } else {
            this.subscribeToSaveResponse(
                this.planificationEmailsService.create(this.planificationEmails));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlanificationEmails>) {
        result.subscribe((res: PlanificationEmails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PlanificationEmails) {
        this.eventManager.broadcast({ name: 'planificationEmailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEmailById(index: number, item: Email) {
        return item.objet;
    }
}

@Component({
    selector: 'jhi-planification-emails-popup',
    template: ''
})
export class PlanificationEmailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planificationEmailsPopupService: PlanificationEmailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.planificationEmailsPopupService
                    .open(PlanificationEmailsDialogComponent as Component, params['id']);
            } else {
                this.planificationEmailsPopupService
                    .open(PlanificationEmailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
