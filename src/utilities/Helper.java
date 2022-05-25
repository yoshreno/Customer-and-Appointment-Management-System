package utilities;

import DAO.DBappointment;
import DAO.DBuser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import java.sql.Timestamp;
import java.time.*;

/** This is a helper class that holds functions/method used throughout the app.*/
public class Helper {

    /** This method converts local time slots to EST time slots.
     * @param startSlot Start time of the time slot
     * @param endSlot End time of the time slot
     * @param date Local date
     * @return List of time slots in EST */
    public static ObservableList<LocalTime> getEasternTimeSlots (LocalTime startSlot, LocalTime endSlot, LocalDate date) {
        ZonedDateTime zdtStart = LocalDateTime.of(date,startSlot).atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime zdtEnd = LocalDateTime.of(date,endSlot).atZone(ZoneId.of("US/Eastern"));
        LocalDateTime estStart = zdtStart.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime estEnd = zdtEnd.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

        ObservableList<LocalTime> timeSlots = FXCollections.observableArrayList();
        while(estStart.isBefore(estEnd.plusSeconds(1))) {
            timeSlots.add(estStart.toLocalTime());
            estStart = estStart.plusMinutes(30);
        }

        return timeSlots;
    }

    /** This method converts local date/time to UTC.
     * @param localDateTime Date/time to convert to UTC
     * @return Timestamp in UTC */
    public static Timestamp convertToUtc (LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldt = utc.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);
        return ts;
    }

    /** This method checks for conflicting appointment.
     * @param appointments List of appointments
     * @param start Start date/time of the appointment being scheduled
     * @param end End date/time of the appointment being scheduled
     * @return True if no conflict */
    public static boolean checkForOverlap (ObservableList<Appointment> appointments, LocalDateTime start, LocalDateTime end) {
        boolean overlap = false;

        for (Appointment appt: appointments) {
            if ((start.isEqual(appt.getStart()) || start.isAfter(appt.getStart())) && start.isBefore(appt.getEnd())) {
                overlap = true;
                break;
            }
            if (end.isAfter(appt.getStart()) && (end.isEqual(appt.getEnd()) || end.isBefore(appt.getEnd()))) {
                overlap = true;
                break;
            }
            if ((start.isEqual(appt.getStart()) || start.isBefore(appt.getStart())) && (end.isEqual(appt.getEnd()) || end.isAfter(appt.getEnd()))) {
                overlap = true;
                break;
            }
        }
        return overlap;
    }

    /** This method checks for an upcoming appointment within 15 min.*/
    public static void checkUpcomingAppointments() {
        boolean upcomingAppt = false;
        for (Appointment appt: DBappointment.getUserAppointments(DBuser.currentUser)) {
            if (appt.getStart().isAfter(LocalDateTime.now()) && appt.getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Appointment Alert");
                warning.setHeaderText("Appointment Alert");
                warning.setContentText("You have an upcoming appointment within 15 minutes. \n" +
                        "\n\tAppointment ID: " + appt.getId() +
                        "\n\tDate: " + appt.getStart().toLocalDate() +
                        "\n\tTime: " + appt.getStart().toLocalTime());
                warning.showAndWait();
                upcomingAppt = true;
                break;
            }
        }
        /**
        if(!upcomingAppt) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Appointment Alert");
            info.setHeaderText("Appointment Alert");
            info.setContentText("No upcoming appointments within 15 minutes.");
            info.showAndWait();
        }
         **/
    }
}
