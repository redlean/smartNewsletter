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
    public min = new Date();
    planification_emails: Planification_emails;
    isSaving: boolean;
    isValidate: boolean = false;
    emails: Email[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private planification_emailsService: Planification_emailsService,
        private emailService: EmailService,
        private eventManager: JhiEventManager
    ) {
    }
    ValidateDate( date : any){
        console.log(this.min);
        let dateSaisie = new Date(date);
       console.log(dateSaisie > this.min);
        if (dateSaisie > this.min){
            this.isValidate = true;
        }
        else {
            this.isValidate = false;

        }
        return this.isValidate;
    }
    ngOnInit() {
        // console.log(this.min);
        // console.log(this.min.getDate());
        // console.log((this.min.getMonth()+1));
        // console.log(this.min.getFullYear());
        // console.log(this.min.getHours());
        // console.log(this.min.getMinutes());
        this.isSaving = false;
        console.log(typeof this.planification_emails.datePlanif);
        this.planification_emails.status = "Non envoyée";
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

    trackEmailById(index: number, item: Email) {
        return item.id;
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
