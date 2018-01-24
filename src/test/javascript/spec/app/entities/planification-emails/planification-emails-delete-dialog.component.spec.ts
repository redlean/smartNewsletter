/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SmartNewsletterTestModule } from '../../../test.module';
import { Planification_emailsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails-delete-dialog.component';
import { Planification_emailsService } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.service';

describe('Component Tests', () => {

    describe('Planification_emails Management Delete Component', () => {
        let comp: Planification_emailsDeleteDialogComponent;
        let fixture: ComponentFixture<Planification_emailsDeleteDialogComponent>;
        let service: Planification_emailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [Planification_emailsDeleteDialogComponent],
                providers: [
                    Planification_emailsService
                ]
            })
            .overrideTemplate(Planification_emailsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Planification_emailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Planification_emailsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
