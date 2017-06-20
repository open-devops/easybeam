import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from './../../shared';

import {
    ProjectService,
    ProjectComponent,
    ProjectFormComponent,
    ProjectResolvePagingParams
} from './';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        ProjectComponent,
        ProjectFormComponent
    ],
    providers: [
        ProjectService,
        ProjectResolvePagingParams
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProjectModule {
}
