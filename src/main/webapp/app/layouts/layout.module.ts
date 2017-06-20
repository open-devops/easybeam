import '../vendor.ts';

import { NgModule } from '@angular/core';

import {
  CovalentLayoutModule,
  CovalentLoadingModule,
  TdMediaService,
  TdLoadingService
} from '@covalent/core';

@NgModule({
  imports: [
    CovalentLayoutModule,
    CovalentLoadingModule
  ],
  providers: [
    TdMediaService,
    TdLoadingService
  ]
})
export class LayoutModule {}
