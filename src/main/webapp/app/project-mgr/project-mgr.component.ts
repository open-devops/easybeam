import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from '@ngx-translate/core';
import { TdMediaService, TdLoadingService } from '@covalent/core';

@Component({
    selector: 'eb-project-mgr',
    templateUrl: 'project-mgr.component.html',
})
export class ProjectManagementComponent implements OnInit, AfterViewInit {

    toolbarTitle: string;

    constructor(public media: TdMediaService,
                private router: Router,
                private jhiLanguageService: JhiLanguageService,
                private translateService: TranslateService) {
        this.jhiLanguageService.addLocation('all');
    }

    ngOnInit() {
        this.subscribePageEvent();
        this.subscribeLangEvent();
    }

    ngAfterViewInit(): void {
      // broadcast to all listener observables when loading the page
      this.media.broadcast();
    }

    private subscribePageEvent(): void {
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.updateToolbarTitle();
            }
        });
    }

    private subscribeLangEvent() {
        this.translateService.onTranslationChange.subscribe((event: TranslationChangeEvent) => {
            this.updateToolbarTitle();
        });

        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.updateToolbarTitle();
        });
    }

    private updateToolbarTitle() {
        let toolbarTitleKey = this.getToolbarTitle(this.router.routerState.snapshot.root);
        this.translateService.get(toolbarTitleKey).subscribe((message) => {
            this.toolbarTitle = message;
        });
    }

    private getToolbarTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['toolbarTitle']) ? routeSnapshot.data['toolbarTitle'] : 'easybeam.project.home.toolbar';
        if (routeSnapshot.firstChild) {
            title = this.getToolbarTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }
}
