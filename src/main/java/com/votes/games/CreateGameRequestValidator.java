package com.votes.games;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class CreateGameRequestValidator implements ConstraintValidator<ValidCreateGameRequest, CreateGameRequest> {

    public boolean isValid(CreateGameRequest request, ConstraintValidatorContext context) {
        // List with players names has to contain unique names only. Names are case sensitive.
        if (request.getPlayersNames() == null) {
            return false;
        }
        Set<String> set = new HashSet<>(request.getPlayersNames());
        return set.size() == request.getPlayersNames().size();
    }

}
