import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from './../../shared';

import {
    TenantGroupService,
    TenantGroupComponent,
    TenantGroupFormComponent,
    TenantGroupResolvePagingParams
} from './';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        TenantGroupComponent,
        TenantGroupFormComponent
    ],
    providers: [
        TenantGroupService,
        TenantGroupResolvePagingParams
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class TenantGroupModule {
}
