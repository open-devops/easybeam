import { NgModule, Sanitizer } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TranslateService } from 'ng2-translate';
import { AlertService } from 'ng-jhipster';
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

export function alertServiceProvider(sanitizer: Sanitizer, translateService: TranslateService) {
    // set below to true to make alerts look like toast
    const isToast = false;
    return new AlertService(sanitizer, isToast, translateService);
}

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
        {
            provide: AlertService,
            useFactory: alertServiceProvider,
            deps: [Sanitizer, TranslateService]
        },
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
