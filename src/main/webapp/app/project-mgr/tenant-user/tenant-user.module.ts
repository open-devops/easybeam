import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from './../../shared';

import {
    TenantUserService,
    TenantUserComponent,
    TenantUserFormComponent,
    TenantUserResolvePagingParams
} from './';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        TenantUserComponent,
        TenantUserFormComponent
    ],
    providers: [
        TenantUserService,
        TenantUserResolvePagingParams
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class TenantUserModule {
}
