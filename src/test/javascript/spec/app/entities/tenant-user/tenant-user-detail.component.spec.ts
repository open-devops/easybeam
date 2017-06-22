import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EasyBeamTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TenantUserDetailComponent } from '../../../../../../main/webapp/app/entities/tenant-user/tenant-user-detail.component';
import { TenantUserService } from '../../../../../../main/webapp/app/entities/tenant-user/tenant-user.service';
import { TenantUser } from '../../../../../../main/webapp/app/entities/tenant-user/tenant-user.model';

describe('Component Tests', () => {

    describe('TenantUser Management Detail Component', () => {
        let comp: TenantUserDetailComponent;
        let fixture: ComponentFixture<TenantUserDetailComponent>;
        let service: TenantUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EasyBeamTestModule],
                declarations: [TenantUserDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TenantUserService,
                    JhiEventManager
                ]
            }).overrideTemplate(TenantUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TenantUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TenantUserService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TenantUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tenantUser).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
