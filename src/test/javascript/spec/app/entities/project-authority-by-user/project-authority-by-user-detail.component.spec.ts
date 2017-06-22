import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EasyBeamTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProjectAuthorityByUserDetailComponent } from '../../../../../../main/webapp/app/entities/project-authority-by-user/project-authority-by-user-detail.component';
import { ProjectAuthorityByUserService } from '../../../../../../main/webapp/app/entities/project-authority-by-user/project-authority-by-user.service';
import { ProjectAuthorityByUser } from '../../../../../../main/webapp/app/entities/project-authority-by-user/project-authority-by-user.model';

describe('Component Tests', () => {

    describe('ProjectAuthorityByUser Management Detail Component', () => {
        let comp: ProjectAuthorityByUserDetailComponent;
        let fixture: ComponentFixture<ProjectAuthorityByUserDetailComponent>;
        let service: ProjectAuthorityByUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EasyBeamTestModule],
                declarations: [ProjectAuthorityByUserDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProjectAuthorityByUserService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProjectAuthorityByUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectAuthorityByUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectAuthorityByUserService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProjectAuthorityByUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.projectAuthorityByUser).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
