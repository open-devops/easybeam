import { NgModule } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { WindowRef } from './tracker/window.service';
import {
    SharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    KeysPipe,
    SplitPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    PasswordStrengthBarComponent
} from './';

import {
    TdLoadingService,
    TdDialogService,
    TdDigitsPipe
} from '@covalent/core';

@NgModule({
    imports: [
        SharedLibsModule
    ],
    declarations: [
        KeysPipe,
        SplitPipe,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        PasswordStrengthBarComponent,
    ],
    providers: [
        JhiLanguageHelper,
        WindowRef,
        Title,
        /** Covalent Service */
        TdLoadingService,
        TdDialogService,
        TdDigitsPipe
    ],
    exports: [
        SharedLibsModule,
        FindLanguageFromKeyPipe,
        KeysPipe,
        SplitPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        PasswordStrengthBarComponent,
    ]
})
export class SharedCommonModule {}
