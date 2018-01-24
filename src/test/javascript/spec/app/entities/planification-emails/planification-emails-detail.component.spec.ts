/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { SmartNewsletterTestModule } from '../../../test.module';
import { Planification_emailsDetailComponent } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails-detail.component';
import { Planification_emailsService } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.service';
import { Planification_emails } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.model';

describe('Component Tests', () => {

    describe('Planification_emails Management Detail Component', () => {
        let comp: Planification_emailsDetailComponent;
        let fixture: ComponentFixture<Planification_emailsDetailComponent>;
        let service: Planification_emailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [Planification_emailsDetailComponent],
                providers: [
                    Planification_emailsService
                ]
            })
            .overrideTemplate(Planification_emailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Planification_emailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Planification_emailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Planification_emails(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.planification_emails).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
