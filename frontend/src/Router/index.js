import { Route, Switch } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import NotFound from '../pages/NotFound';

function Router() {
  return (
    <Switch>
      <Route exact path="/" component={ LoginPage } />
      <Route exact path="*" component={ NotFound } />
    </Switch>
  );
}

export default Router;