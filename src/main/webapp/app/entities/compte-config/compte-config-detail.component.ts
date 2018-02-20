import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CompteConfig } from './compte-config.model';
import { CompteConfigService } from './compte-config.service';

@Component({
    selector: 'jhi-compte-config-detail',
    templateUrl: './compte-config-detail.component.html'
})
export class CompteConfigDetailComponent implements OnInit, OnDestroy {

    compteConfig: CompteConfig;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private compteConfigService: CompteConfigService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompteConfigs();
    }

    load(id) {
        this.compteConfigService.find(id).subscribe((compteConfig) => {
            this.compteConfig = compteConfig;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompteConfigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'compteConfigListModification',
            (response) => this.load(this.compteConfig.id)
        );
    }
}
