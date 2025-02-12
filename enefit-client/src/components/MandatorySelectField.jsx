import Select from "react-select";
import makeAnimated from "react-select/animated";
import { FormGroup } from "reactstrap";

export const MandatorySelectField = ({ id, label, ...props }) => {
  return (
    <FormGroup>
      <label className="form-control-label" htmlFor={id}>
        {label}
      </label>
      <Select {...props} options={props.options} components={makeAnimated()} />
    </FormGroup>
  )
}
