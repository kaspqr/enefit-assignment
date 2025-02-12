import React from 'react'
import { Outlet } from 'react-router-dom'
import { Col, Container, Row } from 'reactstrap'

import Header from './Header.jsx'

const Layout = () => {
  return (
    <>
      <Header />
      <div
        style={{ minHeight: '200px', backgroundColor: '#00ceed' }}
      >
        <Container className='py-5'>
          <Outlet />
          <Row className="pt-3">
            <Col className="text-center">Home Assignment</Col>
          </Row>
          <Row>
            <Col className="text-center">Developed by Kaspar Vellamäe</Col>
          </Row>
        </Container>
      </div>
    </>
  )
}

export default Layout
