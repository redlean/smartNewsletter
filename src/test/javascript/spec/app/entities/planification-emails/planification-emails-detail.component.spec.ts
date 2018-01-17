/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SmartNewsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlanificationEmailsDetailComponent } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails-detail.component';
import { PlanificationEmailsService } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.service';
import { PlanificationEmails } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.model';

describe('Component Tests', () => {

    describe('PlanificationEmails Management Detail Component', () => {
        let comp: PlanificationEmailsDetailComponent;
        let fixture: ComponentFixture<PlanificationEmailsDetailComponent>;
        let service: PlanificationEmailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsTestModule],
                declarations: [PlanificationEmailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlanificationEmailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlanificationEmailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlanificationEmailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanificationEmailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlanificationEmails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.planificationEmails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
