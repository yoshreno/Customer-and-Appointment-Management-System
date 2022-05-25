package model;

/** This is the model class for Contact object.*/
public class Contact extends Person {

    private String email;

    /** This is the constructor method that sets the attributes for Contact object.*/
    public Contact(int id, String name, String email) {
        super(id, name);
        this.email = email;
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
