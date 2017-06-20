import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MdButtonModule, MdCardModule, MdIconModule, MdInputModule } from '@angular/material';
import { CovalentCommonModule } from '@covalent/core';

import { NgxDnDModule } from '@swimlane/ngx-dnd';
import { KeyValueEditorComponent } from '.';

@NgModule({
    imports: [
        FormsModule,
        CommonModule,
        MdButtonModule,
        MdCardModule,
        MdIconModule,
        MdInputModule,
        CovalentCommonModule,
        NgxDnDModule
    ],
    declarations: [
        KeyValueEditorComponent
    ],
    exports: [
        KeyValueEditorComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KeyValueEditorModule {

}
