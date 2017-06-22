import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EasyBeamTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TenantGroupDetailComponent } from '../../../../../../main/webapp/app/entities/tenant-group/tenant-group-detail.component';
import { TenantGroupService } from '../../../../../../main/webapp/app/entities/tenant-group/tenant-group.service';
import { TenantGroup } from '../../../../../../main/webapp/app/entities/tenant-group/tenant-group.model';

describe('Component Tests', () => {

    describe('TenantGroup Management Detail Component', () => {
        let comp: TenantGroupDetailComponent;
        let fixture: ComponentFixture<TenantGroupDetailComponent>;
        let service: TenantGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EasyBeamTestModule],
                declarations: [TenantGroupDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TenantGroupService,
                    JhiEventManager
                ]
            }).overrideTemplate(TenantGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TenantGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TenantGroupService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TenantGroup(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tenantGroup).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
