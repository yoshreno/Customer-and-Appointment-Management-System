package model;

/** This is the model class for Division object.*/
public class Division {

    private int divisionId;
    private String divisionName;
    private int countryId;

    /** This is the constructor method that sets the attributes for Division object.*/
    public Division(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /** This method overrides the toString() method to return Division object as string name instead of its memory address.
     * @return String of division name */
    @Override
    public String toString() {
        return divisionName;
    }

    /** This method gets division ID.
     * @return Division ID */
    public int getDivisionId() {
        return divisionId;
    }

    /** This method sets division ID.
     * @param divisionId Division ID to set */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** This method gets division name.
     * @return Division name */
    public String getDivisionName() {
        return divisionName;
    }

    /** This method sets division name.
     * @param divisionName Division name to set */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
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
}
