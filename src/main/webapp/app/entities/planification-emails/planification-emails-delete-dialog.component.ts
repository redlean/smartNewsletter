import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlanificationEmails } from './planification-emails.model';
import { PlanificationEmailsPopupService } from './planification-emails-popup.service';
import { PlanificationEmailsService } from './planification-emails.service';

@Component({
    selector: 'jhi-planification-emails-delete-dialog',
    templateUrl: './planification-emails-delete-dialog.component.html'
})
export class PlanificationEmailsDeleteDialogComponent {

    planificationEmails: PlanificationEmails;

    constructor(
        private planificationEmailsService: PlanificationEmailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planificationEmailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'planificationEmailsListModification',
                content: 'Deleted an planificationEmails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-planification-emails-delete-popup',
    template: ''
})
export class PlanificationEmailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planificationEmailsPopupService: PlanificationEmailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.planificationEmailsPopupService
                .open(PlanificationEmailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
