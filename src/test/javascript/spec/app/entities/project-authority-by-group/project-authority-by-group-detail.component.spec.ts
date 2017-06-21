import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EasybeamTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProjectAuthorityByGroupDetailComponent } from '../../../../../../main/webapp/app/entities/project-authority-by-group/project-authority-by-group-detail.component';
import { ProjectAuthorityByGroupService } from '../../../../../../main/webapp/app/entities/project-authority-by-group/project-authority-by-group.service';
import { ProjectAuthorityByGroup } from '../../../../../../main/webapp/app/entities/project-authority-by-group/project-authority-by-group.model';

describe('Component Tests', () => {

    describe('ProjectAuthorityByGroup Management Detail Component', () => {
        let comp: ProjectAuthorityByGroupDetailComponent;
        let fixture: ComponentFixture<ProjectAuthorityByGroupDetailComponent>;
        let service: ProjectAuthorityByGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EasybeamTestModule],
                declarations: [ProjectAuthorityByGroupDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProjectAuthorityByGroupService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProjectAuthorityByGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectAuthorityByGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectAuthorityByGroupService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProjectAuthorityByGroup(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.projectAuthorityByGroup).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
