import { HomeComponent, registerRoute } from './';

export const HOME_ROUTE = [{
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  }
}, registerRoute];
