import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Router from './Router';

function App() {
  return (
    <div>
      <Router />
      <ToastContainer />
    </div>
  );
}

export default App;
