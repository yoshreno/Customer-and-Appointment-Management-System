package model;

/** This is the model class for Contact object.*/
public class Contact {

    private int id;
    private String contactName;
    private String email;

    /** This is the constructor method that sets the attributes for Contact object.*/
    public Contact(int id, String contactName, String email) {
        this.id = id;
        this.contactName = contactName;
        this.email = email;
    }

    /** This method overrides the toString() method to return contact object as string name instead of its memory address.
     * @return Concatenated string of ID and contact name */
    @Override
    public String toString() {
        String idAndName = "ID: " + id + " - " + contactName;
        return idAndName;
    }

    /** This method gets contact ID.
     * @return Contact ID */
    public int getId() {
        return id;
    }

    /** This method sets contact ID.
     * @param id Contact ID to set */
    public void setId(int id) {
        this.id = id;
    }

    /** This method gets contact name.
     * @return Contact name */
    public String getContactName() {
        return contactName;
    }

    /** This method sets contact name.
     * @param contactName Contact name to set */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** This method gets contact email.
     * @return Contact email */
    public String getEmail() {
        return email;
    }

    /** This method sets contact email.
     * @param email Contact email to set */
    public void setEmail(String email) {
        this.email = email;
    }
}
