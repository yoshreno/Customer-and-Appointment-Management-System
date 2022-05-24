package model;

import java.time.LocalDateTime;

/** This is the model class for Customer object.*/
public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String updatedBy;
    private int divisionId;
    private Division division;
    private Country country;

    /** This is the constructor method that sets the attributes for Customer object.*/
    public Customer (int customerId, String customerName, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy, int divisionId, Division division, Country country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    /** This method overrides the toString() method to return Customer object as string name instead of its memory address.
     * @return Concatenated string of ID and customer name */
    @Override
    public String toString() {
        String idAndName = "ID: " + customerId + " - " + customerName;
        return idAndName;
    }

    /** This method sets customer ID.
     * @param id Customer ID to set */
    public void setId (int id) {
        customerId = id;
    }

    /** This method gets customer ID.
     * @return Customer ID */
    public int getId () {
        return customerId;
    }

    /** This method sets customer name.
     * @param name Customer name to set */
    public void setName (String name) {
        customerName = name;
    }

    /** This method gets customer name.
     * @return Customer name */
    public String getName () {
        return customerName;
    }

    /** This method gets customer address.
     * @return Customer address */
    public String getAddress() {
        return address;
    }

    /** This method sets customer address.
     * @param address Customer address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** This method gets customer postal code.
     * @return Customer postal code */
    public String getPostalCode() {
        return postalCode;
    }

    /** This method sets customer postal code.
     * @param postalCode Customer postal code to set */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** This method gets customer created date/time.
     * @return Customer created date/time */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** This method sets customer created date/time.
     * @param createDate Customer created date/time to set */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** This method gets customer created by.
     * @return Customer created by */
    public String getCreatedBy() {
        return createdBy;
    }

    /** This method sets customer created by.
     * @param createdBy Customer created by to set */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** This method gets customer last updated time/date.
     * @return Customer last updated time/date */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** This method sets customer last updated date/time.
     * @param lastUpdate Customer last updated date/time to set */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** This method gets customer updated by.
     * @return Customer updated by */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /** This method sets customer last updated by.
     * @param updatedBy Customer last updated by to set */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** This method gets customer division ID.
     * @return Customer division ID */
    public int getDivisionId() {
        return divisionId;
    }

    /** This method sets customer division ID.
     * @param divisionId Customer division ID to set */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** This method gets customer phone.
     * @return Customer phone */
    public String getPhone() {
        return phone;
    }

    /** This method sets customer phone.
     * @param phone Customer phone to set */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** This method gets customer division.
     * @return Customer division */
    public Division getDivision() {
        return division;
    }

    /** This method sets customer division.
     * @param division Customer division to set */
    public void setDivision(Division division) {
        this.division = division;
    }

    /** This method gets customer country.
     * @return Customer country */
    public Country getCountry() {
        return country;
    }

    /** This method sets customer country.
     * @param country Customer country to set */
    public void setCountry(Country country) {
        this.country = country;
    }
}
