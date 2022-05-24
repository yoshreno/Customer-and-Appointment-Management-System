package utilities;

import model.User;
import java.time.LocalDateTime;

/** This is an interface for the lambda expression used in Logger printLogInSuccess().*/
public interface LogInSuccess {

    String getSuccessLog(LocalDateTime localDateTime, User user);

}
