import { Routes } from '@angular/router';

import {
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    trackerRoute,
    userMgmtRoute,
    userDialogRoute
} from './';

export const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    trackerRoute,
    ...userMgmtRoute,
    metricsRoute,
    ...userDialogRoute
];
