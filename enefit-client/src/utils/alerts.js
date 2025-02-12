import Swal from "sweetalert2"

export const errorAlert = async (text) =>
  Swal.fire({
    icon: 'error',
    title: "Error",
    text,
  })
