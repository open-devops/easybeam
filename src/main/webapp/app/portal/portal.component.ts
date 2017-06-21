import { Component, HostBinding, OnInit, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from '@ngx-translate/core';
import { TdMediaService, TdLoadingService } from '@covalent/core';
import { EventManager, JhiLanguageService, AlertService } from 'ng-jhipster';
import { EVENT_PROJECT_SWITCHED, EVENT_PROJECT_LIST_MODIFICATION } from './../shared';
import { Principal, ResponseWrapper, LoginService } from '../shared';
import { Project, ProjectService } from '../project-mgr';
import { fadeAnimation } from '../app.animations';

@Component({
    selector: 'eb-portal',
    templateUrl: './portal.component.html',
    styleUrls: ['./portal.scss'],
    viewProviders: [ ProjectService ],
    animations: [fadeAnimation],
})
export class PortalComponent implements OnInit, AfterViewInit {

    @HostBinding('@routeAnimation') routeAnimation = true;
    @HostBinding('class.td-route-animation') classAnimation = true;

    toolbarTitle: string;

    routes: Object[] = [{
        titleKey: 'global.sidebar.dashboard.main',
        title: 'Project Dashboard',
        route: '/portal/',
        icon: 'dashboard',
      }, {
        titleKey: 'global.sidebar.project-mgr.main',
        title: 'Project Management',
        route: '/portal/project-mgr/projects',
        icon: 'library_add',
      }, {
        titleKey: 'global.sidebar.require-mgr.main',
        title: 'Pipeline Management',
        route: '/portal/require-mgr/requires',
        icon: 'collections_bookmark',
      }, {
        titleKey: 'global.sidebar.library-mgr.main',
        title: 'Job Runner Management',
        route: '/portal/test-design/test-scenarios',
        icon: 'photo_library',
      }
    ];

    account: any;
    projects: Object[];
    currentProject: Project;

    eventSubscriber: Subscription;

    /** i18n messages for project switch  */
    projectSwitchedMessage = '';

    constructor(public media: TdMediaService,
                private alertService: AlertService,
                private loginService: LoginService,
                private router: Router,
                private principal: Principal,
                private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private loadingService: TdLoadingService,
                private translateService: TranslateService,
                private projectService: ProjectService) {
    }

    ngOnInit() {
        this.jhiLanguageService.addLocation('all');

        this.principal.identity().then((account) => {
            this.account = account;
        });

        this.updateSidebarTitle();
        this.updateResourceMessage();
        this.subscribeLangEvent();
    }

    ngAfterViewInit(): void {
        // broadcast to all listener observables when loading the page
        this.media.broadcast();

        this.loadProjecs();
        this.registerChangeInProjects();
    }

    isDashboardPage(): boolean {
        return this.router.url === '/portal';
    }

    setCurrentProject(currentProject: Project) {
        // When the current project has not been set or another project has been selected
        if (!this.currentProject || this.currentProject.id !== currentProject.id) {
            this.currentProject = currentProject;
            this.projectService.setCurrentProject(currentProject);
            this.eventManager.broadcast({
                name: EVENT_PROJECT_SWITCHED,
                content: this.projectSwitchedMessage.replace(/{{ name }}/g, currentProject.name),
            });
        }
    }

    logout() {
        this.loginService.logout();
        this.router.navigate(['']);
    }

    private subscribeLangEvent() {
        this.translateService.onTranslationChange.subscribe((event: TranslationChangeEvent) => {
            this.updateSidebarTitle();
            this.updateResourceMessage();
        });

        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.updateSidebarTitle();
            this.updateResourceMessage();
        });
    }

    private updateSidebarTitle() {
        this.routes.forEach((part, index, theArray) => {
            this.translateService.get(part['titleKey']).subscribe((message) => {
                theArray[index]['title'] = message;
            });
        });
    }

    private updateResourceMessage() {
        this.translateService.get('easybeam.project.switched').subscribe((message) => {
            this.projectSwitchedMessage = message;
        });
    }

    private loadProjecs() {
        this.loadingService.register('projects.load');
        this.projectService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onSuccess(data, headers) {
        this.projects = data;
        this.setCurrentProject(this.projectService.getCurrentProject() ?
                               this.projectService.getCurrentProject() :
                               this.projects[0]);
        setTimeout(() => {
            this.loadingService.resolve('projects.load');
        }, 500);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private registerChangeInProjects() {
        this.eventSubscriber = this.eventManager.subscribe(EVENT_PROJECT_LIST_MODIFICATION, (response) => this.loadProjecs());
    }
}
