import { Component, HostBinding, OnInit, AfterViewInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';
import { TdMediaService, TdLoadingService, LoadingType, LoadingMode } from '@covalent/core';
import { JhiLanguageHelper, Principal, StateStorageService } from '../../shared';
import { fadeAnimation } from '../../app.animations';

@Component({
    selector: 'eb-main',
    templateUrl: './main.component.html',
    animations: [fadeAnimation],
})
export class MainComponent implements OnInit, AfterViewInit {

    @HostBinding('@routeAnimation') routeAnimation = true;
    @HostBinding('class.td-route-animation') classAnimation = true;

    overlayStarSyntax = false;

    constructor(
        public media: TdMediaService,
        private loadingService: TdLoadingService,
        private jhiLanguageHelper: JhiLanguageHelper,
        private router: Router,
        private $storageService: StateStorageService,
        private principal: Principal
   ) {
        this.createFullScreenLoadingOverlay();
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'global.title';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
                this.toggleConfigFullscreenDemo();
            }
        });
    }

    ngAfterViewInit(): void {
        // broadcast to all listener observables when loading the page
        this.media.broadcast();
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
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
