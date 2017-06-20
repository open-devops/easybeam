import { Component, OnInit, Renderer, ElementRef } from '@angular/core';
import { MdDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { EventManager } from 'ng-jhipster';

import { LoginService } from './login.service';
import { StateStorageService } from '../auth/state-storage.service';

@Component({
    selector: 'jhi-login-modal',
    templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements OnInit {
    processing = false;

    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;

    constructor(
        private eventManager: EventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private router: Router,
        public activeModal: MdDialogRef<JhiLoginModalComponent>
    ) {
        this.credentials = {};
    }

    ngOnInit() {
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
        this.activeModal.close('cancel');
    }

    login() {
        this.processing = true;
        this.loginService.login({
            username: this.username,
            password: this.password,
            rememberMe: this.rememberMe
        }).then(() => {
            this.processing = false;
            this.authenticationError = false;
            this.activeModal.close('login success');
            if (this.router.url === '/register' || this.router.url === '/login' ||
                this.router.url === '/finishReset' || this.router.url === '/requestReset' ||
                this.router.url === '/home' || (/activate/.test(this.router.url))) {
                this.router.navigate(['/portal']);
            }

            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });

            // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
            // // since login is succesful, go to stored previousState and clear previousState
            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.router.navigate([redirect]);
            }
        }).catch(() => {
            this.authenticationError = true;
            this.processing = false;
        });
    }
}
