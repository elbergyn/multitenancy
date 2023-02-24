package br.tec.dig.app.utils.validations.validator;

import br.tec.dig.app.application.enumerator.EstadoEnum;
import br.tec.dig.app.utils.validations.annotations.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, EstadoEnum> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValidEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(EstadoEnum value, ConstraintValidatorContext context) {
        return value != null && acceptedValues.contains(value.toString());
    }
}
