package utilities;

import DAO.DBuser;
import java.io.*;
import java.time.LocalDateTime;

/** This class handles all logging functions.*/
public class Logger {

    /** This method adds an entry to the log for successful log in. Lambda expression is used in this method to create the log in success entry.*/
    public static void printLogInSuccess() throws IOException {
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter file = new PrintWriter(fileWriter);

        LogInSuccess log = (localDateTime, user) -> localDateTime + " - Successfully logged in as " + user;

        file.println(log.getSuccessLog(LocalDateTime.now(), DBuser.currentUser));
        file.close();
    }

    /** This method adds an entry to the log for unsuccessful log in. Lambda expression is used in this method to create the log in fail entry.*/
    public static void printLogInFail() throws IOException {
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter file = new PrintWriter(fileWriter);

        LogInFail log = (localDateTime) -> localDateTime + " - Log in failed!";

        file.println(log.getFailLog(LocalDateTime.now()));
        file.close();
    }
}
