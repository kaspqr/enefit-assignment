import { useLocation, Navigate, Outlet } from "react-router-dom"

import useAuth from "../../hooks/useAuth"

const RequireAuth = () => {
  const { username } = useAuth()
  const location = useLocation()

  return !username.length ? (
    <Navigate to="/login" state={{ from: location }} replace />
  ) : (
    <Outlet />    
  )
}

export default RequireAuth
