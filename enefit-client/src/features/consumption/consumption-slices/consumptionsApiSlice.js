import { createSelector, createEntityAdapter } from "@reduxjs/toolkit"

import { apiSlice } from "../../../app/api/apiSlice"

const consumptionsAdapter = createEntityAdapter({})

const initialState = consumptionsAdapter.getInitialState()

export const consumptionsApiSlice = apiSlice.injectEndpoints({
    endpoints: builder => ({
        getConsumptions: builder.query({
            query: (year) => ({
                url: `/consumption?year=${year}`,
                validateStatus: (response, result) => {
                    return response.status === 200 && !result.isError
                },
            }),
            providesTags: (result, error, arg) => {
                if (result?.metering_points) {
                    return [
                        { type: 'Consumption', id: 'LIST' },
                        ...result.metering_points.map(id => ({ type: 'Consumption', id }))
                    ]
                } else return [{ type: 'Consumption', id: 'LIST' }]
            }
        }),
    }),
})

export const { useGetConsumptionsQuery } = consumptionsApiSlice

export const selectConsumptionsResult = consumptionsApiSlice.endpoints.getConsumptions.select()

const selectConsumptionsData = createSelector(
  selectConsumptionsResult,
    consumptionsResult => consumptionsResult.data
)

export const {
    selectAll: selectAllConsumptions,
    selectById: selectConsumptionById,
    selectIds: selectConsumptionsIds
} = consumptionsAdapter.getSelectors(state => selectConsumptionsData(state) ?? initialState)
