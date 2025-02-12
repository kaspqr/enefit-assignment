import React, { useEffect } from 'react'
import useAuth from '../hooks/useAuth'
import { Col, Row } from 'reactstrap'
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useSendLogoutMutation } from '../features/auth/auth-slices/authApiSlice'

const Header = () => {
  const { firstName } = useAuth()

  const [sendLogout, { isLoading, isError, error }] =
    useSendLogoutMutation()

  useEffect(() => {
    if (isError) console.log(error?.data)
  }, [isError])
  
  if (isLoading) return

  return (
    <Row
      style={{ height: '80px' }}
    >
      <Col>
        <h1 className='pt-3 pl-3'>
          Imaginary Energy
        </h1>
      </Col>
      {firstName !== null &&
        <Col className='text-right'>
          <FontAwesomeIcon
            size='xl'
            color='black'
            className='mt-4 mr-4'
            style={{ cursor: 'pointer' }}
            icon={faRightFromBracket}
            onClick={() => sendLogout()}
          />
        </Col>
      }
    </Row>
  )
}

export default Header
