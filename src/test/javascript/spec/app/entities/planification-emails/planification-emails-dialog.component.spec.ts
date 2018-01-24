/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SmartNewsletterTestModule } from '../../../test.module';
import { Planification_emailsDialogComponent } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails-dialog.component';
import { Planification_emailsService } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.service';
import { Planification_emails } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.model';
import { EmailService } from '../../../../../../main/webapp/app/entities/email';

describe('Component Tests', () => {

    describe('Planification_emails Management Dialog Component', () => {
        let comp: Planification_emailsDialogComponent;
        let fixture: ComponentFixture<Planification_emailsDialogComponent>;
        let service: Planification_emailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [Planification_emailsDialogComponent],
                providers: [
                    EmailService,
                    Planification_emailsService
                ]
            })
            .overrideTemplate(Planification_emailsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Planification_emailsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Planification_emailsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Planification_emails(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.planification_emails = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'planification_emailsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Planification_emails();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.planification_emails = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'planification_emailsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
