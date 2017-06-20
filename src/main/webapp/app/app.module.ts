import './vendor.ts';

import { NgModule } from '@angular/core';
import { Ng2Webstorage } from 'ng2-webstorage';

import { SharedModule, UserRouteAccessService } from './shared';
import { CovalentStepsModule } from './shared/steps/steps.module';

import { AppComponent } from './app.component';
import {
    MainComponent,
    TopbarComponent,
    FooterComponent,
    ErrorComponent
} from './layouts';

import { appRoutes, appRoutingProviders } from './app.route';

import { LayoutModule } from './layouts/layout.module';
import { HomeModule } from './home/home.module';
import { AccountModule } from './account/account.module';
import { AdminModule } from './admin/admin.module';
import { PortalModule } from './portal/portal.module';
import { ProjectManagementModule } from './project-mgr/project-mgr.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

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
