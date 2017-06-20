import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TdLoadingService, LoadingType, LoadingMode } from '@covalent/core';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from '../../shared';

@Component({
    selector: 'eb-portal-topbar',
    templateUrl: './topbar.component.html'
})
export class PortalTopbarComponent implements OnInit {

    languages: any[];

    constructor(
        private loadingService: TdLoadingService,
        private loginService: LoginService,
        private languageHelper: JhiLanguageHelper,
        private languageService: JhiLanguageService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private router: Router
    ) {
        this.languageService.addLocation('home');
        this.createFullScreenLoadingOverlay();
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    changeLanguage(languageKey: string) {
        this.toggleConfigFullscreenDemo();
        this.languageService.changeLanguage(languageKey);
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.loginModalService.open();
    }

    logout() {
        this.loginService.logout();
        this.router.navigate(['']);
    }

    private createFullScreenLoadingOverlay(): void {
        this.loadingService.create({
            name: 'fullScreenLoadingOverlay',
            mode: LoadingMode.Indeterminate,
            type: LoadingType.Linear,
            color: 'accent',
        });
    }

    private toggleConfigFullscreenDemo(): void {
        this.loadingService.register('fullScreenLoadingOverlay');
        setTimeout(() => {
            this.loadingService.resolve('fullScreenLoadingOverlay');
        }, 500);
    }

}
