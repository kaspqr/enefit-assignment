import { useSelector } from "react-redux"

import { jwtDecode } from "jwt-decode"

import { selectCurrentToken } from "../features/auth/auth-slices/authSlice"

const useAuth = () => {
  const token = useSelector(selectCurrentToken)

  if (token) {
    const decoded = jwtDecode(token)

    const {
      username,
      firstName,
      lastName
    } = decoded

    return {
        username,
        firstName,
        lastName
    }
  }

  return { username: '' }
}

export default useAuth
