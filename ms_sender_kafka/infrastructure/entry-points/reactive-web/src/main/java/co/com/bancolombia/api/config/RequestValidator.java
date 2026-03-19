package co.com.bancolombia.api.config;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> void validateBody(T object) {
        Set<ConstraintViolation<T>> constraint = validator.validate(object);
        if (!constraint.isEmpty()) {
            throw new RuntimeException("Invalid parameters: " + getMessage(constraint));
        }
    }

    public <T> void validateObjectHeaders(T object) {
        Set<ConstraintViolation<T>> constraints = validator.validate(object);
        if (!constraints.isEmpty()) {
            throw new RuntimeException("Invalid headers: " + getMessage(constraints));
        }
    }

    private <T> String getMessage(Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream().map(c -> String.join(" ",
                c.getPropertyPath().toString(), c.getMessage())).collect(Collectors.joining(", "));
    }

}
