package service;

import domain.Account;
import domain.validators.Validator;
import repository.AccountRepository;

public class AccountService {

    private AccountRepository accRepo;
    private Validator<Account> accVal;

    public AccountService(AccountRepository accRepo,Validator<Account> accVal) {
        this.accRepo = accRepo;
        this.accVal = accVal;
    }

    public Account findAccount(String email, String password){
        Account acc = new Account();
        acc.setId(email);
        acc.setPassword(password);
        accVal.validate(acc);
        Account accFound = accRepo.findOne(email);
        if(accFound == null){
            throw new ServiceException("No account with this email found!");
        }
        else{
            if(!accFound.getPassword().equals(password)){
                throw new ServiceException("Password doesn't match the email!");
            }
            return accFound;
        }
    }
}
