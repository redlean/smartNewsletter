import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PlanificationEmails } from './planification-emails.model';
import { PlanificationEmailsService } from './planification-emails.service';

@Injectable()
export class PlanificationEmailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private planificationEmailsService: PlanificationEmailsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.planificationEmailsService.find(id).subscribe((planificationEmails) => {
                    this.ngbModalRef = this.planificationEmailsModalRef(component, planificationEmails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.planificationEmailsModalRef(component, new PlanificationEmails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    planificationEmailsModalRef(component: Component, planificationEmails: PlanificationEmails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.planificationEmails = planificationEmails;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
