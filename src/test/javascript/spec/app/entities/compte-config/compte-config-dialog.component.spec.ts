/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SmartNewsletterTestModule } from '../../../test.module';
import { CompteConfigDialogComponent } from '../../../../../../main/webapp/app/entities/compte-config/compte-config-dialog.component';
import { CompteConfigService } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.service';
import { CompteConfig } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.model';

describe('Component Tests', () => {

    describe('CompteConfig Management Dialog Component', () => {
        let comp: CompteConfigDialogComponent;
        let fixture: ComponentFixture<CompteConfigDialogComponent>;
        let service: CompteConfigService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [CompteConfigDialogComponent],
                providers: [
                    CompteConfigService
                ]
            })
            .overrideTemplate(CompteConfigDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteConfigDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteConfigService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompteConfig(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.compteConfig = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'compteConfigListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompteConfig();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.compteConfig = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'compteConfigListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
