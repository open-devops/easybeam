import { Type } from '@angular/core';
import { NgModule, ModuleWithProviders } from '@angular/core';

import { CommonModule } from '@angular/common';
import { MdButtonModule, MdIconModule, MdRippleModule, PortalModule, ScrollDispatchModule } from '@angular/material';

import { CovalentCommonModule } from '@covalent/core';

import { NgxDnDModule } from '@swimlane/ngx-dnd';

// Steps
import { TdStepsComponent } from './steps.component';
import { TdStepHeaderComponent } from './step-header/step-header.component';
import { TdStepBodyComponent } from './step-body/step-body.component';
import { TdStepComponent, TdStepLabelDirective, TdStepActionsDirective,
         TdStepSummaryDirective, TdStepHeaderActionsDirective } from './step.component';

const TD_STEPS: Type<any>[] = [
  TdStepsComponent,
  TdStepComponent,
  TdStepHeaderComponent,
  TdStepBodyComponent,
  TdStepHeaderActionsDirective,
  TdStepLabelDirective,
  TdStepActionsDirective,
  TdStepSummaryDirective,
];

export { TdStepComponent, StepState  } from './step.component';
export { TdStepsComponent, IStepChangeEvent, StepMode } from './steps.component';

@NgModule({
  imports: [
    CommonModule,
    MdButtonModule,
    MdIconModule,
    MdRippleModule,
    PortalModule,
    ScrollDispatchModule,
    CovalentCommonModule,
    NgxDnDModule
  ],
  declarations: [
    TD_STEPS,
  ],
  exports: [
    TD_STEPS,
  ],
})
export class CovalentStepsModule {

}
