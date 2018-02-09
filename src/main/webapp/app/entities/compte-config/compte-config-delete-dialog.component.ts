import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompteConfig } from './compte-config.model';
import { CompteConfigPopupService } from './compte-config-popup.service';
import { CompteConfigService } from './compte-config.service';

@Component({
    selector: 'jhi-compte-config-delete-dialog',
    templateUrl: './compte-config-delete-dialog.component.html'
})
export class CompteConfigDeleteDialogComponent {

    compteConfig: CompteConfig;

    constructor(
        private compteConfigService: CompteConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compteConfigService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'compteConfigListModification',
                content: 'Deleted an compteConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-compte-config-delete-popup',
    template: ''
})
export class CompteConfigDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private compteConfigPopupService: CompteConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.compteConfigPopupService
                .open(CompteConfigDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
