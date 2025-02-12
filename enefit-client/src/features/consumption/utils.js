export const stringAsSelectOption = (label) => {
  if (label === "") return { value: label, label: "Select..." }
  return { value: label, label: label }
}

export const getYears = () => {
  const years = []
  const currentYear = new Date().getFullYear()

  for (let year = currentYear; year >= 2021; year--) {
    years.push(year.toString())
  }

  return years
}

export const getBarChartData = (values, title) => {
  const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]

  return values.map((value, index) => ({
    month: months[index],
    [`${title}`]: value.toFixed(2),
  }))
}

export const getY = (values) => {
  return [Number(Math.min(...values).toFixed(2)), Number(Math.max(...values).toFixed(2))]
}
