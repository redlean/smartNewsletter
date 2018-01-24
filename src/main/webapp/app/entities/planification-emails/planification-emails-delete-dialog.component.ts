import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Planification_emails } from './planification-emails.model';
import { Planification_emailsPopupService } from './planification-emails-popup.service';
import { Planification_emailsService } from './planification-emails.service';

@Component({
    selector: 'jhi-planification-emails-delete-dialog',
    templateUrl: './planification-emails-delete-dialog.component.html'
})
export class Planification_emailsDeleteDialogComponent {

    planification_emails: Planification_emails;

    constructor(
        private planification_emailsService: Planification_emailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planification_emailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'planification_emailsListModification',
                content: 'Deleted an planification_emails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-planification-emails-delete-popup',
    template: ''
})
export class Planification_emailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planification_emailsPopupService: Planification_emailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.planification_emailsPopupService
                .open(Planification_emailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
