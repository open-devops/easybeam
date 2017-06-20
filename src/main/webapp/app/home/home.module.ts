import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from '../shared';

import { HomeComponent, RegisterComponent, Register } from './';

@NgModule({
    imports: [
        SharedModule,
    ],
    declarations: [
        HomeComponent,
        RegisterComponent
    ],
    entryComponents: [
    ],
    providers: [
        Register
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HomeModule {}
