/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { SmartNewsletterTestModule } from '../../../test.module';
import { CompteConfigDetailComponent } from '../../../../../../main/webapp/app/entities/compte-config/compte-config-detail.component';
import { CompteConfigService } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.service';
import { CompteConfig } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.model';

describe('Component Tests', () => {

    describe('CompteConfig Management Detail Component', () => {
        let comp: CompteConfigDetailComponent;
        let fixture: ComponentFixture<CompteConfigDetailComponent>;
        let service: CompteConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [CompteConfigDetailComponent],
                providers: [
                    CompteConfigService
                ]
            })
            .overrideTemplate(CompteConfigDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteConfigDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteConfigService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompteConfig(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.compteConfig).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
