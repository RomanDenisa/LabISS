package domain.validators;

import domain.Bug;

public class BugValidator implements Validator<Bug> {
    @Override
    public void validate(Bug entity) {
        String errors = "";
        if(entity.getName().equals(""))
            errors +="Invalid name!";
        if(entity.getDescription().equals(""))
            errors +="Invalid description!";
        if(entity.getAdditionalInfo().equals(""))
            errors +="Invalid addiional info!";
        if(errors.length()>0)
            throw new ValidatorException(errors);
    }
}
