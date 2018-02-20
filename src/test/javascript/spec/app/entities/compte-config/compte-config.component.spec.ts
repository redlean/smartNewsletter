/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { SmartNewsletterTestModule } from '../../../test.module';
import { CompteConfigComponent } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.component';
import { CompteConfigService } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.service';
import { CompteConfig } from '../../../../../../main/webapp/app/entities/compte-config/compte-config.model';

describe('Component Tests', () => {

    describe('CompteConfig Management Component', () => {
        let comp: CompteConfigComponent;
        let fixture: ComponentFixture<CompteConfigComponent>;
        let service: CompteConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmartNewsletterTestModule],
                declarations: [CompteConfigComponent],
                providers: [
                    CompteConfigService
                ]
            })
            .overrideTemplate(CompteConfigComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteConfigComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteConfigService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompteConfig(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.compteConfigs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
