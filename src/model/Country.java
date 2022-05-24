package model;

/** This is the model class for Country object.*/
public class Country {

    private int countryId;
    private String countryName;

    /** This is the constructor method that sets the attributes for Country object.*/
    public Country (int id, String name) {
        countryId = id;
        countryName = name;
    }

    /** This method overrides the toString() method to return Country object as string name instead of its memory address.
     * @return String of country name */
    @Override
    public String toString() {
        return countryName;
    }

    /** This method gets country ID.
     * @return Country ID */
    public int getCountryId() {
        return countryId;
    }

    /** This method sets country ID.
     * @param countryId Country ID to set */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** This method gets country name.
     * @return Country name */
    public String getCountryName() {
        return countryName;
    }

    /** This method sets country name.
     * @param countryName Country name to set */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
