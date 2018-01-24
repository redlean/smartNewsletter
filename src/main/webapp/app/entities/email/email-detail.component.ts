import {Component, OnInit, OnDestroy, Pipe, PipeTransform} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Email } from './email.model';
import { EmailService } from './email.service';
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({ name: 'safeHtml'})
export class SafeHtmlPipe implements PipeTransform  {
    constructor(private sanitized: DomSanitizer) {}
    transform(value) {
        return this.sanitized.bypassSecurityTrustHtml(value);
        // return this.sanitizer.bypassSecurityTrustStyle(value);
        // return this.sanitizer.bypassSecurityTrustHtml(value);
        // return this.sanitizer.bypassSecurityTrustXxx(value);
    }
}
@Component({
    selector: 'jhi-email-detail',
    templateUrl: './email-detail.component.html'
})
export class EmailDetailComponent implements OnInit, OnDestroy {

    email: Email;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emailService: EmailService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmails();
    }

    load(id) {
        this.emailService.find(id).subscribe((email) => {
            this.email = email;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emailListModification',
            (response) => this.load(this.email.id)
        );
    }
}
