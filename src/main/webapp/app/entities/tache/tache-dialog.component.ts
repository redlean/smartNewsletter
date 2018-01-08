import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tache } from './tache.model';
import { TachePopupService } from './tache-popup.service';
import { TacheService } from './tache.service';

@Component({
    selector: 'jhi-tache-dialog',
    templateUrl: './tache-dialog.component.html'
})
export class TacheDialogComponent implements OnInit {

    tache: Tache;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tacheService: TacheService,
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
        if (this.tache.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tacheService.update(this.tache));
        } else {
            this.subscribeToSaveResponse(
                this.tacheService.create(this.tache));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tache>) {
        result.subscribe((res: Tache) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Tache) {
        this.eventManager.broadcast({ name: 'tacheListModification', content: 'OK'});
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
    selector: 'jhi-tache-popup',
    template: ''
})
export class TachePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tachePopupService: TachePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tachePopupService
                    .open(TacheDialogComponent as Component, params['id']);
            } else {
                this.tachePopupService
                    .open(TacheDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
