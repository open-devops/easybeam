import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EasyBeamTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TenantPolicyDetailComponent } from '../../../../../../main/webapp/app/entities/tenant-policy/tenant-policy-detail.component';
import { TenantPolicyService } from '../../../../../../main/webapp/app/entities/tenant-policy/tenant-policy.service';
import { TenantPolicy } from '../../../../../../main/webapp/app/entities/tenant-policy/tenant-policy.model';

describe('Component Tests', () => {

    describe('TenantPolicy Management Detail Component', () => {
        let comp: TenantPolicyDetailComponent;
        let fixture: ComponentFixture<TenantPolicyDetailComponent>;
        let service: TenantPolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EasyBeamTestModule],
                declarations: [TenantPolicyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TenantPolicyService,
                    JhiEventManager
                ]
            }).overrideTemplate(TenantPolicyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TenantPolicyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TenantPolicyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TenantPolicy(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tenantPolicy).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
