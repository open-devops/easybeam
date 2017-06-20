import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from '../shared';

import {
    Activate,
    Password,
    PasswordResetInit,
    PasswordResetFinish,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent
} from './';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        ActivateComponent,
        PasswordComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SettingsComponent
    ],
    providers: [
        Activate,
        Password,
        PasswordResetInit,
        PasswordResetFinish
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountModule {}
