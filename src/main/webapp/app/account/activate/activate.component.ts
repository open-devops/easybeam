import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Activate } from './activate.service';
import { LoginModalService } from '../../shared';

@Component({
    selector: 'jhi-activate',
    templateUrl: './activate.component.html'
})
export class ActivateComponent implements OnInit {
    error: string;
    success: string;

    constructor(
        private activate: Activate,
        private loginModalService: LoginModalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.route.queryParams.subscribe((params) => {
            this.activate.get(params['key']).subscribe(() => {
                this.error = null;
                this.success = 'OK';
            }, () => {
                this.success = null;
                this.error = 'ERROR';
            });
        });
    }

    login() {
        this.loginModalService.open();
    }
}