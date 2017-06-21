// Angular Core Modules
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
// Angular Material Design Modules
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
    MdCoreModule,
    MdButtonModule,
    MdCardModule,
    MdCheckboxModule,
    MdChipsModule,
    MdDialogModule,
    MdInputModule,
    MdIconModule,
    MdListModule,
    MdMenuModule,
    MdSelectModule,
    MdSnackBarModule,
    MdSidenavModule,
    MdSlideToggleModule,
    MdToolbarModule,
    MdTooltipModule,
} from '@angular/material';
// Covalent Modules
import {
    CovalentCommonModule,
    CovalentChipsModule,
    CovalentDataTableModule,
    CovalentDialogsModule,
    CovalentExpansionPanelModule,
    CovalentLayoutModule,
    CovalentLoadingModule,
    CovalentMediaModule,
    CovalentMenuModule,
    CovalentMessageModule,
    CovalentNotificationsModule,
    CovalentPagingModule,
    CovalentSearchModule,
} from '@covalent/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
// Jhipster Moduels
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';

@NgModule({
    imports: [
        NgbModule.forRoot(),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            i18nEnabled: true,
            defaultI18nLang: 'en'
        }),
        InfiniteScrollModule,
        CookieModule.forRoot()
    ],
    exports: [
        /** Angular Modules */
        CommonModule,
        BrowserAnimationsModule,
        BrowserModule,
        FormsModule,
        HttpModule,
        RouterModule,
        /** Jhipster Modules */
        NgbModule,
        NgJhipsterModule,
        InfiniteScrollModule,
        /** Material Modules */
        MdCoreModule,
        MdButtonModule,
        MdCardModule,
        MdCheckboxModule,
        MdChipsModule,
        MdDialogModule,
        MdIconModule,
        MdInputModule,
        MdListModule,
        MdMenuModule,
        MdSelectModule,
        MdSnackBarModule,
        MdToolbarModule,
        MdTooltipModule,
        /** Covalent Modules */
        CovalentCommonModule,
        CovalentChipsModule,
        CovalentDataTableModule,
        CovalentDialogsModule,
        CovalentExpansionPanelModule,
        CovalentLayoutModule,
        CovalentLoadingModule,
        CovalentMediaModule,
        CovalentMenuModule,
        CovalentMessageModule,
        CovalentNotificationsModule,
        CovalentPagingModule,
        CovalentSearchModule,
        NgxChartsModule
    ]
})
export class SharedLibsModule {}
