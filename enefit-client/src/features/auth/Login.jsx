import { useState } from "react"
import { useDispatch } from "react-redux"
import { useNavigate } from "react-router-dom"
import { Row, Button, Form, Col, Card, CardHeader, CardBody } from "reactstrap"

import usePersist from "../../hooks/usePersist"
import useAuth from "../../hooks/useAuth"

import { setCredentials } from "./auth-slices/authSlice"
import { useLoginMutation } from "./auth-slices/authApiSlice"

import { InputField } from "../../components/InputField"

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faToggleOff, faToggleOn } from "@fortawesome/free-solid-svg-icons"
import { errorAlert } from "../../utils/alerts"

const Login = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const auth = useAuth()
  const [persist, setPersist] = usePersist()

  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const [login, { isLoading }] = useLoginMutation()

  const handleSubmit = async (e) => {
    e.preventDefault()

    try {
      const { accessToken } = await login({ username: username?.toLowerCase(), password }).unwrap()
      dispatch(setCredentials({ accessToken }))
      setUsername("")
      setPassword("")
      navigate("/")
    } catch (err) {
      console.log(err)
      if (!err.status) {
        errorAlert('No Server Response')
      } else if (err.status === 400) {
        errorAlert('Missing Username or Password')
      } else if (err.status === 403) {
        errorAlert('Forbidden')
      } else if (err.status === 401) {
        errorAlert('Unauthorized')
      } else {
        errorAlert('Something went wrong')
      }
    }
  }

  if (isLoading) return

  if (auth?.username?.length) {
    setTimeout(() => {
      navigate("/")
    }, 50)
  }

  return (
    <Card>
      <CardHeader><h2>Login</h2></CardHeader>
      <CardBody>
        <Form className="mb-4" onSubmit={(e) => handleSubmit(e)}>
          <InputField 
            id="username"
            label="Username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <InputField 
            id="password"
            label="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Row>
            <Col>
              <label className="mt-2" htmlFor="persist">
                <span className="mr-2">Stay Logged In</span>
                <FontAwesomeIcon
                  name="persist"
                  onClick={() => setPersist((prev) => !prev)}
                  size="lg"
                  icon={persist ? faToggleOn : faToggleOff}
                  color={persist ? "rgb(23, 152, 207)" : "grey"}
                />
              </label>
            </Col>
          </Row>
          <Row>
            <Col>
              <Button className="mt-4" style={{ backgroundColor: '#08c792', color: 'white' }} type="submit">
                Login
              </Button>
            </Col>
          </Row>
        </Form>
      </CardBody>
    </Card>
  )
}

export default Login
