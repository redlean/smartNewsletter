/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SmartNewsletterTestModule } from '../../../test.module';
import { CompteConfigDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/compte-config/compte-config-delete-dialog.component';
import { CompteConfigService } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.service';

describe('Component Tests', () => {

    describe('CompteConfig Management Delete Component', () => {
        let comp: CompteConfigDeleteDialogComponent;
        let fixture: ComponentFixture<CompteConfigDeleteDialogComponent>;
        let service: CompteConfigService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [CompteConfigDeleteDialogComponent],
                providers: [
                    CompteConfigService
                ]
            })
            .overrideTemplate(CompteConfigDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteConfigDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteConfigService);
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
