- Implement HTML input attributes as listed [here](https://www.w3schools.com/html/html_form_attributes.asp)

`readonly, disabled, size, maxlength, min, max, multiple, pattern, placeholder, required, step, autofocus, height, width, list, autocomplete`

Currently we have `FormMember.size, maxLength, disabled, optional, required, multiChoice, multiple`

- Consider implementing `Map<String, String> FormInputContext.getAttributes`

Currently we have only `FormInputContext#isOptional(S formDataSource, F dataSourceField)`




