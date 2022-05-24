package utilities;

import model.User;

import java.time.LocalDateTime;

/** This is an interface for the lambda expression used in Logger printLogInFail().*/
public interface LogInFail {

    String getFailLog(LocalDateTime localDateTime);

}
