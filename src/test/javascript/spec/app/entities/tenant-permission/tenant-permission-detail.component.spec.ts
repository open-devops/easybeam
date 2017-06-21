import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EasybeamTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TenantPermissionDetailComponent } from '../../../../../../main/webapp/app/entities/tenant-permission/tenant-permission-detail.component';
import { TenantPermissionService } from '../../../../../../main/webapp/app/entities/tenant-permission/tenant-permission.service';
import { TenantPermission } from '../../../../../../main/webapp/app/entities/tenant-permission/tenant-permission.model';

describe('Component Tests', () => {

    describe('TenantPermission Management Detail Component', () => {
        let comp: TenantPermissionDetailComponent;
        let fixture: ComponentFixture<TenantPermissionDetailComponent>;
        let service: TenantPermissionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EasybeamTestModule],
                declarations: [TenantPermissionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TenantPermissionService,
                    JhiEventManager
                ]
            }).overrideTemplate(TenantPermissionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TenantPermissionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TenantPermissionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TenantPermission(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tenantPermission).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
