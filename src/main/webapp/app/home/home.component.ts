import { Component, OnInit } from '@angular/core';
import { EventManager } from 'ng-jhipster';

import { Account, Principal } from '../shared';

@Component({
    selector: 'eb-home',
    templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
    account: Account;

    constructor(
        private principal: Principal,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
