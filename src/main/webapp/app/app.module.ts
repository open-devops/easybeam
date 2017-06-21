import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { SharedModule, UserRouteAccessService } from './shared';
import { CovalentStepsModule } from './shared/steps/steps.module';

import { AppComponent } from './app.component';

import { appRoutes, appRoutingProviders } from './app.route';

import { LayoutModule } from './layouts/layout.module';
import { HomeModule } from './home/home.module';
import { AdminModule } from './admin/admin.module';
import { AccountModule } from './account/account.module';
import { PortalModule } from './portal/portal.module';
import { ProjectManagementModule } from './project-mgr/project-mgr.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import {
    MainComponent,
    TopbarComponent,
    FooterComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        SharedModule,
        CovalentStepsModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        appRoutes,
        LayoutModule,
        HomeModule,
        AccountModule,
        AdminModule,
        PortalModule,
        ProjectManagementModule,
    ],
    declarations: [
        AppComponent,
        MainComponent,
        TopbarComponent,
        FooterComponent,
        ErrorComponent
    ],
    providers: [
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        appRoutingProviders
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule {
}
