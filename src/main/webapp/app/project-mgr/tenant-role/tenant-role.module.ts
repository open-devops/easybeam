import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from './../../shared';

import {
    TenantRoleService,
    TenantRoleComponent,
    TenantRoleFormComponent,
    TenantRoleResolvePagingParams
} from './';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        TenantRoleComponent,
        TenantRoleFormComponent
    ],
    providers: [
        TenantRoleService,
        TenantRoleResolvePagingParams
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class TenantRoleModule {
}
