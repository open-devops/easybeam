import { Injectable } from '@angular/core';
import { MdDialog, MdDialogRef, MdDialogConfig } from '@angular/material';

import { JhiLoginModalComponent } from './login.component';

@Injectable()
export class LoginModalService {
    private isOpen = false;

    config: MdDialogConfig = {
        disableClose: true,
        width: '500px'
    };

    constructor( private modalService: MdDialog ) {}

    open(): MdDialogRef<JhiLoginModalComponent> {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        const modalRef = this.modalService.open(JhiLoginModalComponent, this.config);
        modalRef.afterClosed().subscribe((result) => {
            this.isOpen = false;
        }, (reason) => {
            this.isOpen = false;
        });
        return modalRef;
    }
}
