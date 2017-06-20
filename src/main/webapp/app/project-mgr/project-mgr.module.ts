import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from '../shared';

import { ProjectModule } from './project/project.module';
import { TenantUserModule } from './tenant-user/tenant-user.module';
import { TenantRoleModule } from './tenant-role/tenant-role.module';
import { TenantGroupModule } from './tenant-group/tenant-group.module';
import { ProjectManagementComponent } from '.';

@NgModule({
    imports: [
        SharedModule,
        ProjectModule,
        TenantUserModule,
        TenantRoleModule,
        TenantGroupModule
    ],
    declarations: [
        ProjectManagementComponent
    ],
    providers: [
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProjectManagementModule {
}
