package test;

import DAO.DBconnection;
import DAO.DBuser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogInTest {

    @BeforeEach
    void setUp() {
        DBconnection.openConnection();
        DBuser.getAllUsers();
    }

    @AfterEach
    void tearDown() {
        DBconnection.closeConnection();
    }

    @Test
    void correctCredentials_UserName_test() {
        assertTrue(DBuser.verifyCredentials("test", "test"));
    }

    @Test
    void correctCredentials_UserName_admin() {
        assertTrue(DBuser.verifyCredentials("admin", "admin"));
    }

    @Test
    void wrongUserNameAndPassword() {
        assertFalse(DBuser.verifyCredentials("xyz", "xyz"));
    }

    @Test
    void wrongUserName() {
        assertFalse(DBuser.verifyCredentials("xyz", "admin"));
    }

    @Test
    void wrongPassword() {
        assertFalse(DBuser.verifyCredentials("admin", "xyz"));
    }
}