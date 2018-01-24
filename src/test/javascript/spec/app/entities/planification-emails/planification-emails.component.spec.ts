/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { SmartNewsletterTestModule } from '../../../test.module';
import { Planification_emailsComponent } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.component';
import { Planification_emailsService } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.service';
import { Planification_emails } from '../../../../../../main/webapp/app/entities/planification-emails/planification-emails.model';

describe('Component Tests', () => {

    describe('Planification_emails Management Component', () => {
        let comp: Planification_emailsComponent;
        let fixture: ComponentFixture<Planification_emailsComponent>;
        let service: Planification_emailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [Planification_emailsComponent],
                providers: [
                    Planification_emailsService
                ]
            })
            .overrideTemplate(Planification_emailsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Planification_emailsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Planification_emailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Planification_emails(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.planification_emails[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
