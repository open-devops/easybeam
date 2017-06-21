import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EasybeamTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TenantRoleDetailComponent } from '../../../../../../main/webapp/app/entities/tenant-role/tenant-role-detail.component';
import { TenantRoleService } from '../../../../../../main/webapp/app/entities/tenant-role/tenant-role.service';
import { TenantRole } from '../../../../../../main/webapp/app/entities/tenant-role/tenant-role.model';

describe('Component Tests', () => {

    describe('TenantRole Management Detail Component', () => {
        let comp: TenantRoleDetailComponent;
        let fixture: ComponentFixture<TenantRoleDetailComponent>;
        let service: TenantRoleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EasybeamTestModule],
                declarations: [TenantRoleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TenantRoleService,
                    JhiEventManager
                ]
            }).overrideTemplate(TenantRoleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TenantRoleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TenantRoleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TenantRole(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tenantRole).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
