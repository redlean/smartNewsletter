import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompteConfig } from './compte-config.model';
import { CompteConfigPopupService } from './compte-config-popup.service';
import { CompteConfigService } from './compte-config.service';

@Component({
    selector: 'jhi-compte-config-dialog',
    templateUrl: './compte-config-dialog.component.html'
})
export class CompteConfigDialogComponent implements OnInit {

    compteConfig: CompteConfig;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private compteConfigService: CompteConfigService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.compteConfig.id !== undefined) {
            this.subscribeToSaveResponse(
                this.compteConfigService.update(this.compteConfig));
        } else {
            this.subscribeToSaveResponse(
                this.compteConfigService.create(this.compteConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompteConfig>) {
        result.subscribe((res: CompteConfig) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompteConfig) {
        this.eventManager.broadcast({ name: 'compteConfigListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-compte-config-popup',
    template: ''
})
export class CompteConfigPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private compteConfigPopupService: CompteConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.compteConfigPopupService
                    .open(CompteConfigDialogComponent as Component, params['id']);
            } else {
                this.compteConfigPopupService
                    .open(CompteConfigDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
