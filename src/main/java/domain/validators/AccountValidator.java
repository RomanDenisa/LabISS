package domain.validators;

import domain.Account;

public class AccountValidator implements Validator<Account>{
    @Override
    public void validate(Account entity) {
        String errors = "";
        if(entity.getId().equals(""))
            errors +="Invalid username!";
        if(entity.getPassword().equals(""))
            errors +="Invalid password!";
        if(errors.length()>0)
            throw new ValidatorException(errors);
    }
}
