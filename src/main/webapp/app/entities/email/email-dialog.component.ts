import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { CKEditorModule } from 'ng2-ckeditor';
import { Email } from './email.model';
import { EmailPopupService } from './email-popup.service';
import { EmailService } from './email.service';
import {createViewChild} from "@angular/compiler/src/core";
// this.ckeConfig = {
//     height: 400,
//     language: "en",
//     allowedContent: true,
//     toolbar: [
//         { name: "clipboard", items: ["Cut", "Copy", "Paste", "PasteText", "PasteFromWord", "-", "Undo", "Redo"] },
//         { name: "links", items: ["Link", "Unlink", "Anchor"] },
//         { name: "insert", items: ["Image", "Table", "HorizontalRule", "SpecialChar", "Iframe", "imageExplorer"] }
//     ]
// };
@Component({
    selector: 'jhi-email-dialog',
    templateUrl: './email-dialog.component.html'
})
export class EmailDialogComponent implements OnInit {

    email: Email;
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private emailService: EmailService,
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
        if (this.email.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emailService.update(this.email));
        } else {
            this.subscribeToSaveResponse(
                this.emailService.create(this.email));
        }
    }

    private subscribeToSaveResponse(result: Observable<Email>) {
        result.subscribe((res: Email) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Email) {
        this.eventManager.broadcast({ name: 'emailListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}


@Component({
    selector: 'jhi-email-popup',
    template: ''
})
export class EmailPopupComponent implements OnInit, OnDestroy {
  //  public uploader: fileUploader = new FileUploader()
    routeSub: any;
    constructor(
        private route: ActivatedRoute,
        private emailPopupService: EmailPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.emailPopupService
                    .open(EmailDialogComponent as Component, params['id']);
            } else {
                this.emailPopupService
                    .open(EmailDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
    // openImageExplorer($event: any) {
    //     this.showFiles = true; // open pop-up window
    // }
    // onCloseImage() {
    //     this.showFiles = false; // close the pop-up window
    // }
    //
    // onAddImage() {
    //     try
    //     {
    //         let link = this.ckeditor.instance.document.createElement("img");
    //         link.setAttribute("alt", "Image");
    //         link.setAttribute("src", "./Images/test.png");
    //
    //         this.ckeditor.instance.insertElement(link);
    //     }
    //     catch(error)
    //     {
    //         console.log((<Error>error).message);
    //     }
    //
    //     this.showFiles = false; // close the pop-up window
    // }


}
