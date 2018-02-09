import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompteConfig } from './compte-config.model';
import { CompteConfigService } from './compte-config.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-compte-config',
    templateUrl: './compte-config.component.html'
})
export class CompteConfigComponent implements OnInit, OnDestroy {
compteConfigs: CompteConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;
    clicked : boolean = false;
    constructor(
        private compteConfigService: CompteConfigService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.compteConfigService.query().subscribe(
            (res: ResponseWrapper) => {
                this.compteConfigs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompteConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompteConfig) {
        return item.id;
    }
    compteIsEmpty() {
        return this.loadAll()
    }
    // compteIsEmpty(){
    //    if ( this.compteConfigs.length == 0 ) {
    //        return true;
    //    }
    //    return false;
    // }
    registerChangeInCompteConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('compteConfigListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
