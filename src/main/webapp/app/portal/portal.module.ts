import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from '../shared';
import { TdLoadingService } from '@covalent/core';

import {
    PortalTopbarComponent,
    DashboardComponent,
    PortalComponent
} from './';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        PortalTopbarComponent,
        DashboardComponent,
        PortalComponent
    ],
    providers: [
        TdLoadingService
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class PortalModule {
}
