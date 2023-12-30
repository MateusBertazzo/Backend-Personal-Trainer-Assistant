import React from 'react';
import { Route, Switch } from 'react-router-dom';
import UserPage from '../pages/UserPage';
import NotFound from '../pages/NotFound';

function Router() {
  return (
    <Switch>
      <Route exact path="/" component={ UserPage } />
      <Route path="*" component={ NotFound } />
    </Switch>
  );
}

export default Router;