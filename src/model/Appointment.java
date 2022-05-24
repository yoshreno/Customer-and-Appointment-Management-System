package model;

import java.time.LocalDateTime;

/** This is the model class for Appointment object.*/
public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String updatedBy;
    private Customer customer;
    private User user;
    private Contact contact;

    /** This is the constructor method that sets the attributes for Appointment object.*/
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                       LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy, Customer customer, User user,
                       Contact contact) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.customer = customer;
        this.user = user;
        this.contact = contact;
    }

    /** This method gets appointment ID.
     * @return Appointment ID */
    public int getId() {
        return id;
    }

    /** This method sets appointment ID.
     * @param id Appointment ID to set */
    public void setId(int id) {
        this.id = id;
    }

    /** This method gets appointment title.
     * @return Appointment title */
    public String getTitle() {
        return title;
    }

    /** This method sets appointment title.
     * @param title Appointment title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** This method gets appointment description.
     * @return Appointment description */
    public String getDescription() {
        return description;
    }

    /** This method sets appointment description.
     * @param description Appointment description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** This method gets appointment location.
     * @return Appointment location */
    public String getLocation() {
        return location;
    }

    /** This method sets appointment location.
     * @param location Appointment location to set */
    public void setLocation(String location) {
        this.location = location;
    }

    /** This method gets appointment type.
     * @return Appointment type */
    public String getType() {
        return type;
    }

    /** This method sets appointment type.
     * @param type Appointment type to set */
    public void setType(String type) {
        this.type = type;
    }

    /** This method gets appointment start time.
     * @return Appointment start time */
    public LocalDateTime getStart() {
        return start;
    }

    /** This method sets appointment start time.
     * @param start Appointment start time to set */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** This method gets appointment end time.
     * @return Appointment end time */
    public LocalDateTime getEnd() {
        return end;
    }

    /** This method sets appointment end time.
     * @param end Appointment end time to set */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** This method gets appointment created time.
     * @return Appointment created time */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** This method sets appointment create time.
     * @param createDate Appointment create time to set */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** This method gets appointment created by.
     * @return Appointment created by */
    public String getCreatedBy() {
        return createdBy;
    }

    /** This method sets appointment created by.
     * @param createdBy Appointment created by to set */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** This method gets appointment last updated time.
     * @return Appointment last updated time */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** This method sets appointment last update time.
     * @param lastUpdate Appointment last update time to set */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** This method gets appointment updated by.
     * @return Appointment updated by */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /** This method sets appointment updated by.
     * @param updatedBy Appointment updated by to set */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** This method gets appointment customer.
     * @return Appointment customer */
    public Customer getCustomer() {
        return customer;
    }

    /** This method sets appointment customer.
     * @param customer Appointment customer to set */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** This method gets appointment user.
     * @return Appointment user */
    public User getUser() {
        return user;
    }

    /** This method sets appointment user.
     * @param user Appointment user to set */
    public void setUser(User user) {
        this.user = user;
    }

    /** This method gets appointment contact.
     * @return Appointment contact */
    public Contact getContact() {
        return contact;
    }

    /** This method sets appointment contact.
     * @param contact Appointment contact to set */
    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
