import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from '@ngx-translate/core';
import { Account, Principal, EVENT_TENANT_ROLE_LIST_MODIFICATION, JhiLanguageHelper } from './../../../shared';

import { TenantRole } from './../tenant-role.model';
import { TenantRoleService } from './../tenant-role.service';

@Component({
    selector: 'eb-tenant-role-form',
    templateUrl: './form.component.html',
})
export class TenantRoleFormComponent implements OnInit, AfterViewInit, OnDestroy  {
    account: Account;
    tenantRole: TenantRole;
    isSaving: boolean;
    isNew: boolean;
    editable: boolean;

    routeSub: any;

    /** i18n messages for save event  */
    createdEventMessage = '';
    updatedEventMessage = '';

    constructor(private principal: Principal,
                private languageHelper: JhiLanguageHelper,
                private translateService: TranslateService,
                private route: ActivatedRoute,
                private router: Router,
                private alertService: JhiAlertService,
                private eventManager: JhiEventManager,
                private tenantRoleService: TenantRoleService) {
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
        this.tenantRole = new TenantRole();
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
                this.tenantRoleService.find(params['id']).subscribe((tenantRole: any) => {
                    this.tenantRole = {
                        id: tenantRole.id,
                        name: tenantRole.name,
                        description: tenantRole.description
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
        if (this.tenantRole.id !== undefined) {
            this.isNew = false;
            this.subscribeToSaveResponse(this.tenantRoleService.update(this.tenantRole));
        } else {
            this.isNew = true;
            this.subscribeToSaveResponse(this.tenantRoleService.create(this.tenantRole));
        }
    }

    private prepareSaveData(): void {
        // Build up tenant relationship
        if (!this.tenantRole.accountId) {
            this.tenantRole.accountId = this.account.id;
        }
    }

    private subscribeToSaveResponse(result: Observable<TenantRole>) {
        result.subscribe((res: TenantRole) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TenantRole) {
        this.eventManager.broadcast({
            name: EVENT_TENANT_ROLE_LIST_MODIFICATION,
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
        this.translateService.get('easybeam.tenantRole.created').subscribe((message) => {
            this.createdEventMessage = message;
        });
        this.translateService.get('easybeam.tenantRole.updated').subscribe((message) => {
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
