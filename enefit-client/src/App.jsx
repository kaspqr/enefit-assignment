import { Route, Routes } from 'react-router-dom';

import Layout from './components/Layout';
import PersistLogin from "./features/auth/PersistLogin"
import RequireAuth from './features/auth/RequireAuth';
import Login from './features/auth/Login';
import Consumption from './features/consumption/Consumption';

function App() {
  return (
    <Routes>
      <Route element={<PersistLogin />}>
        <Route path="/" element={<Layout />}>
          <Route element={<RequireAuth />}>
            <Route index element={<Consumption />} />
          </Route>
          <Route path="login" element={<Login />} />
        </Route>
      </Route>
    </Routes>
  )
}

export default App
