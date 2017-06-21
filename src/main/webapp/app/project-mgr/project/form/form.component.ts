import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import { JhiLanguageService, EventManager, AlertService } from 'ng-jhipster';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from '@ngx-translate/core';
import { EVENT_PROJECT_LIST_MODIFICATION } from './../../../shared';

import { Project } from './../project.model';
import { ProjectService } from './../project.service';

@Component({
    selector: 'eb-project-form',
    templateUrl: './form.component.html',
})
export class ProjectFormComponent implements OnInit, AfterViewInit, OnDestroy  {
    project: Project;
    isSaving: boolean;
    isNew: boolean;
    editable: boolean;

    routeSub: any;

    /** i18n messages for save event  */
    createdEventMessage = '';
    updatedEventMessage = '';

    constructor(private languageService: JhiLanguageService,
                private translateService: TranslateService,
                private route: ActivatedRoute,
                private router: Router,
                private alertService: AlertService,
                private eventManager: EventManager,
                private projectService: ProjectService) {
        this.languageService.addLocation('all');
    }

    goBack(): void {
        window.history.back();
    }

    ngOnInit(): void {
        this.initialize();
        this.updateEventMessage();
        this.subscribeLangEvent();
    }

    private initialize() {
        this.isSaving = false;
        this.project = new Project();
        this.editable = this.isEditable(this.router.routerState.snapshot.root);
    }

    private isEditable(routeSnapshot: ActivatedRouteSnapshot) {
        let editable: boolean = (routeSnapshot.data && routeSnapshot.data['editable']) ? routeSnapshot.data['editable'] : false;
        if (routeSnapshot.firstChild) {
            editable = this.isEditable(routeSnapshot.firstChild) || editable;
        }
        return editable;
    }

    ngAfterViewInit(): void {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projectService.find(params['id']).subscribe((project: any) => {
                    this.project = {
                        id: project.id,
                        name: project.name,
                        description: project.description,
                        isActive: project.isActive,
                        isPublic: project.isPublic
                    };
                });
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

    save(): void {
        this.isSaving = true;
        if (this.project.id !== undefined) {
            this.isNew = false;
            this.subscribeToSaveResponse(this.projectService.update(this.project));
        } else {
            this.isNew = true;
            this.subscribeToSaveResponse(this.projectService.create(this.project));
        }
    }

    private subscribeToSaveResponse(result: Observable<Project>) {
        result.subscribe((res: Project) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Project) {
        this.eventManager.broadcast({
            name: EVENT_PROJECT_LIST_MODIFICATION,
            content: this.isNew
                   ? this.createdEventMessage.replace(/{{ name }}/g, result.name)
                   : this.updatedEventMessage.replace(/{{ name }}/g, result.name)
        });
        this.isSaving = false;
        this.goBack();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private updateEventMessage() {
        this.translateService.get('easybeam.project.created').subscribe((message) => {
            this.createdEventMessage = message;
        });
        this.translateService.get('easybeam.project.updated').subscribe((message) => {
            this.updatedEventMessage = message;
        });
    }

    private subscribeLangEvent() {
        this.translateService.onTranslationChange.subscribe((event: TranslationChangeEvent) => {
            this.updateEventMessage();
        });

        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.updateEventMessage();
        });
    }
}
