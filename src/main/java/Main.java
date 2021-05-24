import controllers.LoginCtrl;
import domain.Account;
import domain.Bug;
import domain.validators.AccountValidator;
import domain.validators.BugValidator;
import domain.validators.Validator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.*;
import service.AccountService;
import service.BugService;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Validator<Account> accVal = new AccountValidator();
        Validator<Bug> bugVal = new BugValidator();
        AccountRepository accRepo = new AccountDbRepository();
        EmployeeRepository empRepo = new EmployeeDbRepository();
        BugRepository bugRepo = new BugDbRepository();
        AccountService accServ = new AccountService(accRepo,accVal);
        BugService bugService = new BugService(bugRepo,bugVal,empRepo);

        primaryStage.setTitle("Login");

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        AnchorPane root=loader.load();
        LoginCtrl ctrl=loader.getController();
        ctrl.setService(accServ,bugService,primaryStage);

        Scene myScene = new Scene(root);
        primaryStage.setScene(myScene);
        primaryStage.show();

    }
}
