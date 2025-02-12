import { expect, test } from 'vitest'

import { getY, getYears, stringAsSelectOption } from './utils'

test('Get years from 2025 to 2021', () => { // will only work in 2025
  expect(getYears()).toEqual(["2025", "2024", "2023", "2022", "2021"])
})

test('Get a select option object with the value and label of the param', () => {
  expect(stringAsSelectOption('2023')).toEqual({ value: '2023', label: '2023' })
})

test('Return an array of 2 numbers: smallest and biggest in an array of numbers', () => {
  expect(getY([1.1, 0.5, 5.2, 3, 7.7, 4.3])).toEqual([0.5, 7.7])
})
