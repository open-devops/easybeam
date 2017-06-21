import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import { JhiLanguageService, EventManager, AlertService } from 'ng-jhipster';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from '@ngx-translate/core';
import { Account, Principal, EVENT_TENANT_USER_LIST_MODIFICATION, JhiLanguageHelper } from './../../../shared';

import { TenantUser } from './../tenant-user.model';
import { TenantUserService } from './../tenant-user.service';

@Component({
    selector: 'et-tenant-user-form',
    templateUrl: './form.component.html',
})
export class TenantUserFormComponent implements OnInit, AfterViewInit, OnDestroy  {
    confirmPassword: string;
    doNotMatch = false;
    languages: any[];

    account: Account;
    tenantUser: TenantUser;
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
                private tenantUserService: TenantUserService) {
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
        this.tenantUser = new TenantUser();
        this.editable = this.isEditable(this.router.routerState.snapshot.root);
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
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
                this.tenantUserService.find(params['id']).subscribe((tenantUser: any) => {
                    this.tenantUser = {
                        id: tenantUser.id,
                        login: tenantUser.login,
                        firstName: tenantUser.firstName,
                        lastName: tenantUser.lastName,
                        password: tenantUser.password,
                        email: tenantUser.email,
                        langKey: tenantUser.langKey
                    };
                    this.confirmPassword = tenantUser.password;
                });
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

    save(): void {
        this.isSaving = true;
        if (this.invalidData()) {
            this.isSaving = false;
            return;
        }
        this.prepareSaveData();
        if (this.tenantUser.id !== undefined) {
            this.isNew = false;
            this.subscribeToSaveResponse(this.tenantUserService.update(this.tenantUser));
        } else {
            this.isNew = true;
            this.subscribeToSaveResponse(this.tenantUserService.create(this.tenantUser));
        }
    }

    private invalidData(): boolean {
        // Password Confirmation Compatible Check
        if (this.tenantUser.password !== this.confirmPassword) {
            this.doNotMatch = true;
            return true;
        } else {
            this.doNotMatch = false;
        }

        return false;
    }

    private prepareSaveData(): void {
        // Build up tenant relationship
        if (!this.tenantUser.accountId) {
            this.tenantUser.accountId = this.account.id;
        }
    }

    private subscribeToSaveResponse(result: Observable<TenantUser>) {
        result.subscribe((res: TenantUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TenantUser) {
        this.eventManager.broadcast({
            name: EVENT_TENANT_USER_LIST_MODIFICATION,
            content: this.isNew
                   ? this.createdEventMessage.replace(/{{ name }}/g, result.login)
                   : this.updatedEventMessage.replace(/{{ name }}/g, result.login)
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
        this.translateService.get('easybeam.tenantUser.created').subscribe((message) => {
            this.createdEventMessage = message;
        });
        this.translateService.get('easybeam.tenantUser.updated').subscribe((message) => {
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
