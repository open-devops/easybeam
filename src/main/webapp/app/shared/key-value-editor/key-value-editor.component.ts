import { Component, Directive, Input, OnInit } from '@angular/core';
import { ContentChild, TemplateRef, ViewContainerRef } from '@angular/core';
import { TemplatePortalDirective } from '@angular/material';

@Directive({
  selector: '[key-value-editor-actions]ng-template',
})
export class KeyValueEditorActionsDirective extends TemplatePortalDirective {
  constructor(templateRef: TemplateRef<any>, viewContainerRef: ViewContainerRef) {
    super(templateRef, viewContainerRef);
  }
}

@Component({
    selector: 'key-value-editor',
    templateUrl: './key-value-editor.component.html',
})
export class KeyValueEditorComponent implements OnInit {

    @ContentChild(KeyValueEditorActionsDirective) keyValueEditorActions: KeyValueEditorActionsDirective;

    /** entries holding the key-value items */
    @Input('entries') entries = [];

    /** min character length */
    @Input('keyMinlength') keyMinlength = '';
    /** max character length */
    @Input('keyMaxlength') keyMaxlength = '';
    /** min character length */
    @Input('valueMinlength') valueMinlength: number;
    /** max character length */
    @Input('valueMaxlength') valueMaxlength: number;
    /** key's validation regrex */
    @Input('keyValidator') keyValidator;
    /** values's validation regrex */
    @Input('valueValidator') valueValidator;
    /** place holder for key */
    @Input('keyPlaceholder') keyPlaceholder: string;
    /** place holder for value */
    @Input('valuePlaceholder') valuePlaceholder: string;

    /** indicates whether show the editor header */
    @Input('showHeader') showHeader = true;
    /** indicates whether can add new row */
    @Input('cannotAdd') cannotAdd = false;
    /** indicates whether can delete rows */
    @Input('cannotDelete') cannotDelete = false;
    /** indicates whether the rows are readonly */
    @Input('isReadonly') isReadonly = false;
    /** indicates whether readonly will only apply to existing keys */
    @Input('isReadonlyKeys') isReadonlyKeys = false;
    /** indicates whether empty keys are allowed */
    @Input('allowEmptyKeys') allowEmptyKeys = true;

    /** placeholder for new entry */
    placeholder: any;

    /** default timeout delay setting */
    private timeoutDelay = 25;

    /** unique id & name for key value items */
    counter = 1000;
    unique: number;

    constructor() {
        this.placeholder = this.newEntry();
        this.unique = this.counter++;
    }

    ngOnInit() {
        if (!this.entries.length) {
            this.addEntry(this.entries);
        }
    }

    newEntry(): any {
        return {name: '', value: ''};
    };

    addEntry(entries: any[], entry?: any) {
        if (entries) {
            entries.push(entry || this.newEntry());
        }
    };

    onAddRow(index: number): void {
        this.entries.splice(index + 1, 0, this.newEntry());
    }

    deleteEntry(start: number, deleteCount: number) {
        this.entries.splice(start, deleteCount);
        // if the link is used, add a new empty entry to ensure the inputs do not all disappear
        if (!this.entries.length || this.noEnabledDeleteButton()) {
            this.addEntry(this.entries);
        }
    }

    uniqueForKey(index: number): string {
        return 'key-value-editor-key-' + this.unique.toString() + '-' + index.toString();
    }

    uniqueForValue(index: number): string {
        return 'key-value-editor-value-' + this.unique.toString() + '-' + index.toString();
    }

    private noEnabledDeleteButton(): boolean {
        return this.entries.filter((entry) => !entry.cannotDelete).length === 0;
    }
}
