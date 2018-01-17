import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlanificationEmails } from './planification-emails.model';
import { PlanificationEmailsService } from './planification-emails.service';

@Component({
    selector: 'jhi-planification-emails-detail',
    templateUrl: './planification-emails-detail.component.html'
})
export class PlanificationEmailsDetailComponent implements OnInit, OnDestroy {

    planificationEmails: PlanificationEmails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private planificationEmailsService: PlanificationEmailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlanificationEmails();
    }

    load(id) {
        this.planificationEmailsService.find(id).subscribe((planificationEmails) => {
            this.planificationEmails = planificationEmails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlanificationEmails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'planificationEmailsListModification',
            (response) => this.load(this.planificationEmails.id)
        );
    }
}
