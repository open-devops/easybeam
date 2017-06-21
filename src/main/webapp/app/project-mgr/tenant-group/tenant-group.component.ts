import { Component, OnInit, AfterViewInit } from '@angular/core';
import { MdSnackBar } from '@angular/material';
import { Subscription } from 'rxjs/Rx';

import { EventManager, JhiLanguageService, AlertService } from 'ng-jhipster';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from '@ngx-translate/core';

import { TdDataTableSortingOrder, TdDataTableService, ITdDataTableSortChangeEvent, IPageChangeEvent } from '@covalent/core';
import { TdDialogService, TdMediaService, TdLoadingService, LoadingType, LoadingMode } from '@covalent/core';
import { EVENT_TENANT_GROUP_LIST_MODIFICATION, Principal, ResponseWrapper } from '../../shared';

import { TenantGroup } from './tenant-group.model';
import { TenantGroupService } from './tenant-group.service';

@Component({
  selector: 'eb-tenant-group',
  templateUrl: './tenant-group.component.html',
})
export class TenantGroupComponent implements OnInit, AfterViewInit {
    columnsDef: any[] = [
        { name: 'name',  messageKey: 'easybeam.tenantGroup.name', sortable: true},
        { name: 'description', messageKey: 'easybeam.tenantGroup.description' },
        { name: 'id', messageKey: 'entity.action.title' }
    ];
    columns: any[];
    currentAccount: any;
    data: any[];
    eventSubscriber: Subscription;
    filteredData: any[];
    filteredTotal: number;
    searchTerm = '';
    fromRow = 1;
    currentPage = 1;
    pageSize = 5;
    sortBy = 'name';
    sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

    /** i18n messages for delete confirmation  */
    deleteConfirmTitle = '';
    deleteConfirmMessage = '';
    deleteConfirmCancel = '';
    deleteConfirmAccept = '';
    deletedEventMessage = '';

    constructor(public media: TdMediaService,
                public snackBar: MdSnackBar,
                private alertService: AlertService,
                private principal: Principal,
                private eventManager: EventManager,
                private languageService: JhiLanguageService,
                private translateService: TranslateService,
                private loadingService: TdLoadingService,
                private dialogService: TdDialogService,
                private dataTableService: TdDataTableService,
                private tenantGroupService: TenantGroupService) {
        this.languageService.addLocation('all');
    }

    ngOnInit(): void {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.updateTableColumns();
        this.updateDeleteConfirmMessage();
        this.subscribeLangEvent();
        this.registerChangeInTenantGroups();
    }

    ngAfterViewInit(): void {
        // broadcast to all listener observables when loading the page
        this.media.broadcast();

        this.loadAll();
    }

    loadAll() {
        this.loadingService.register('tenant-groups.load');
        this.tenantGroupService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    sort(sortEvent: ITdDataTableSortChangeEvent): void {
        this.sortBy = sortEvent.name;
        this.sortOrder = sortEvent.order;
        this.filter();
    }

    search(searchTerm: string): void {
        this.searchTerm = searchTerm;
        this.filter();
    }

    page(pagingEvent: IPageChangeEvent): void {
        this.fromRow = pagingEvent.fromRow;
        this.currentPage = pagingEvent.page;
        this.pageSize = pagingEvent.pageSize;
        this.filter();
    }

    filter(): void {
        let newData: any[] = this.data;
        newData = this.dataTableService.filterData(newData, this.searchTerm, true);
        this.filteredTotal = newData.length;
        newData = this.dataTableService.sortData(newData, this.sortBy, this.sortOrder);
        newData = this.dataTableService.pageData(newData, this.fromRow, this.currentPage * this.pageSize);
        this.filteredData = newData;
    }

    trackId(index: number, item: TenantGroup) {
        return item.id;
    }

    registerChangeInTenantGroups() {
        this.eventSubscriber = this.eventManager.subscribe(EVENT_TENANT_GROUP_LIST_MODIFICATION, (response) => {
            this.loadAll();
            this.snackBar.open(response.content, null, {
                duration: 2000,
            });
        });
    }

    confirmDelete(id: number, name: string): void {
        this.dialogService.openConfirm({
          message: this.deleteConfirmMessage.replace(/{{ name }}/g, name),
          title: this.deleteConfirmTitle,
          cancelButton: this.deleteConfirmCancel,
          acceptButton: this.deleteConfirmAccept,
        }).afterClosed().subscribe((accept: boolean) => {
          if (accept) {
            this.deleteTenantGroup(id, name);
          } else {
            // DO SOMETHING ELSE
          }
        });
    }

    private deleteTenantGroup(id: number, name: string) {
        this.tenantGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: EVENT_TENANT_GROUP_LIST_MODIFICATION,
                content: this.deletedEventMessage.replace(/{{ name }}/g, name),
            });
        });
    }

    private onSuccess(data, headers) {
        this.data = data;
        this.filteredData = this.data;
        this.filteredTotal = this.data.length;
        this.filter();
        setTimeout(() => {
            this.loadingService.resolve('tenant-groups.load');
        }, 500);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private updateTableColumns() {
        this.columns = [];
        for (let columnDef of this.columnsDef) {
            this.translateService.get(columnDef.messageKey).subscribe((message) => {
                this.columns.push({
                    name: columnDef.name,
                    label: message,
                    sortable: !!columnDef.sortable
                });
            });
        }
    }

    private updateDeleteConfirmMessage() {
        this.translateService.get('entity.delete.title').subscribe((message) => {
            this.deleteConfirmTitle = message;
        });
        this.translateService.get('easybeam.tenantGroup.delete.question').subscribe((message) => {
            this.deleteConfirmMessage = message;
        });
        this.translateService.get('entity.delete.accept').subscribe((message) => {
            this.deleteConfirmAccept = message;
        });
        this.translateService.get('entity.delete.cancel').subscribe((message) => {
            this.deleteConfirmCancel = message;
        });
        this.translateService.get('easybeam.tenantGroup.deleted').subscribe((message) => {
            this.deletedEventMessage = message;
        });
    }

    private subscribeLangEvent() {
        this.translateService.onTranslationChange.subscribe((event: TranslationChangeEvent) => {
            this.updateTableColumns();
            this.updateDeleteConfirmMessage();
        });

        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.updateTableColumns();
            this.updateDeleteConfirmMessage();
        });
    }
}
