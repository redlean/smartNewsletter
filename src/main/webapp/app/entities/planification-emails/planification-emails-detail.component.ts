import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Planification_emails } from './planification-emails.model';
import { Planification_emailsService } from './planification-emails.service';

@Component({
    selector: 'jhi-planification-emails-detail',
    templateUrl: './planification-emails-detail.component.html'
})
export class Planification_emailsDetailComponent implements OnInit, OnDestroy {

    planification_emails: Planification_emails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private planification_emailsService: Planification_emailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlanification_emails();
    }

    load(id) {
        this.planification_emailsService.find(id).subscribe((planification_emails) => {
            this.planification_emails = planification_emails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlanification_emails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'planification_emailsListModification',
            (response) => this.load(this.planification_emails.id)
        );
    }
}
