/**
 * A donor is an individual or organization who can register and login to an account in order to donates aid to a distribution center (DC)
 */

public class Donor extends User{
    
    private String phoneNum;

    /**
     * Create a donor with specified name, password for the account and phone number
     * 
     * @param name the name of the donor
     * @param password the password for the account
     * @param phoneNum the phone number of the donor
     */
    public Donor(String name,String password, String phoneNum) {
        super(name,password);
        this.phoneNum=phoneNum;
        this.phoneNum = phoneNum;

    }
    /**
     * Returns a string representation of the phone number of the donor.
     * @return a string representation of the phone number of the donor 
     */
    public String getphoneNum() {
        return phoneNum;
    }
    
    /**
     * Returns a string representation of the name of the donor, the password for the account and the phone number of the donor.
     * @return a string representation of the details of a donor
     */
    public String toString() {
        return super.name +" "+ super.password +" "+ phoneNum;
    }

    /**
     * Returns a string representation of the name of the donor, the password for the account and the phone number of the donor seperated by comma in order to save as csv file.
     * @return a string representation of the details of a donor seperated by comma
     */
    public String toCSVString() {
       return super.name + "," + super.password + "," + phoneNum ;
    }

}