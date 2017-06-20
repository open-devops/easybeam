import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import { JhiLanguageService, EventManager, AlertService } from 'ng-jhipster';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from 'ng2-translate/ng2-translate';
import { Account, Principal, EVENT_TENANT_GROUP_LIST_MODIFICATION, JhiLanguageHelper } from './../../../shared';

import { TenantGroup } from './../tenant-group.model';
import { TenantGroupService } from './../tenant-group.service';

@Component({
    selector: 'eb-tenant-group-form',
    templateUrl: './form.component.html',
})
export class TenantGroupFormComponent implements OnInit, AfterViewInit, OnDestroy  {
    account: Account;
    tenantGroup: TenantGroup;
    isSaving: boolean;
    isNew: boolean;
    editable: boolean;

    routeSub: any;

    /** i18n messages for save event  */
    createdEventMessage = '';
    updatedEventMessage = '';

    constructor(private principal: Principal,
                private languageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private translateService: TranslateService,
                private route: ActivatedRoute,
                private router: Router,
                private alertService: AlertService,
                private eventManager: EventManager,
                private tenantGroupService: TenantGroupService) {
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
        this.tenantGroup = new TenantGroup();
        this.editable = this.isEditable(this.router.routerState.snapshot.root);
        this.principal.identity().then((account) => {
            this.account = account;
        });
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
                this.tenantGroupService.find(params['id']).subscribe((tenantGroup: any) => {
                    this.tenantGroup = {
                        id: tenantGroup.id,
                        name: tenantGroup.name,
                        description: tenantGroup.description,
                        accountId: tenantGroup.accountId,
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
        this.prepareSaveData();
        if (this.tenantGroup.id !== undefined) {
            this.isNew = false;
            this.subscribeToSaveResponse(this.tenantGroupService.update(this.tenantGroup));
        } else {
            this.isNew = true;
            this.subscribeToSaveResponse(this.tenantGroupService.create(this.tenantGroup));
        }
    }

    private prepareSaveData(): void {
        // Build up tenant relationship
        if (!this.tenantGroup.accountId) {
            this.tenantGroup.accountId = this.account.id;
        }
    }

    private subscribeToSaveResponse(result: Observable<TenantGroup>) {
        result.subscribe((res: TenantGroup) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TenantGroup) {
        this.eventManager.broadcast({
            name: EVENT_TENANT_GROUP_LIST_MODIFICATION,
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
        this.translateService.get('easybeam.tenantGroup.created').subscribe((message) => {
            this.createdEventMessage = message;
        });
        this.translateService.get('easybeam.tenantGroup.updated').subscribe((message) => {
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
