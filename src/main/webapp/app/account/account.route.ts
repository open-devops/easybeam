import { Routes } from '@angular/router';

import {
    activateRoute,
    passwordRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    settingsRoute
} from './';

export const ACCOUNT_ROUTES = [
   activateRoute,
   passwordRoute,
   passwordResetFinishRoute,
   passwordResetInitRoute,
   settingsRoute
];
