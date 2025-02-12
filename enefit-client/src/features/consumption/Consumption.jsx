import React, { useEffect, useState } from 'react'
import { Card, CardBody, CardHeader, Col, Row } from 'reactstrap'
import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer } from 'recharts'

import { getBarChartData, getY, getYears, stringAsSelectOption } from './utils'
import { MandatorySelectField } from '../../components/MandatorySelectField'
import useAuth from '../../hooks/useAuth'
import { useGetConsumptionsQuery } from './consumption-slices/consumptionsApiSlice'

const Consumption = () => {
  const { firstName, lastName } = useAuth()

  const [year, setYear] = useState("2025")
  const [meteringPoints, setMeteringPoints] = useState([])

  const {
    data: consumptions,
    isLoading,
    isSuccess,
    isError,
    error,
  } = useGetConsumptionsQuery(year, {
    pollingInterval: 600000,
    refetchOnFocus: false,
    refetchOnMountOrArgChange: true,
  })

  useEffect(() => {
    if (isSuccess) setMeteringPoints(consumptions.metering_points)
  }, [consumptions, isSuccess])

  useEffect(() => {
    if (isError) console.log(error?.data)
  }, [isError])

  if (isLoading) return

  return (
    <Card>
      <CardHeader>
        <Row>
          <Col>
            <h2>
              Electricity Consumption of {firstName} {lastName}
            </h2>
          </Col>
        </Row>
      </CardHeader>
      <CardBody>
        <Row className='mb-4'>
          <Col>
            <MandatorySelectField
              label="Year"
              id="year"
              options={getYears().map(option => stringAsSelectOption(option))}
              value={stringAsSelectOption(year)}
              onChange={(newValue) => {
                if (newValue) setYear(newValue.value)
              }}
            />
          </Col>
        </Row>
        {meteringPoints?.map(point => (
          <Row key={point.id}>
            <Col>
              <Card>
                <CardHeader>
                  <Row>
                    <Col>
                      <h3>{point.address}</h3>
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      <h4>{year} total</h4>
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      {point.totalConsumption.toFixed(2)}MW
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      {point.totalCost.toFixed(2)}€
                    </Col>
                  </Row>
                </CardHeader>
                <CardBody>
                  <Row>
                    <Col>
                      <h3>Consumption</h3>
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      <ResponsiveContainer width="100%" height={300}>
                        <BarChart
                          data={getBarChartData(point.consumptions, "consumption")}
                          margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                        >
                          <CartesianGrid />
                          <XAxis dataKey="month" />
                          <YAxis domain={getY(point.consumptions)} />
                          <Tooltip />
                          <Bar dataKey="consumption" fill="#ceed00" />
                        </BarChart>
                      </ResponsiveContainer>
                    </Col>
                  </Row>
                  <Row className='mt-5'>
                    <Col>
                      <h3>Cost</h3>
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      <ResponsiveContainer width="100%" height={300}>
                        <BarChart
                          data={getBarChartData(point.prices, "cost")}
                          margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                        >
                          <CartesianGrid />
                          <XAxis dataKey="month" />
                          <YAxis domain={getY(point.prices)} />
                          <Tooltip />
                          <Bar dataKey="cost" fill="#0055ff" />
                        </BarChart>
                      </ResponsiveContainer>
                    </Col>
                  </Row>
                </CardBody>
              </Card>
            </Col>
          </Row>
        ))}
      </CardBody>
    </Card>
  )
}

export default Consumption
