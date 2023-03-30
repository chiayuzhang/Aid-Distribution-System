/**
 * An NGO (non-governmental organization) is an organization that can register and login to an account in order to receives aids from a distribution center.
 */
public class Ngo extends User implements Comparable<Ngo>{

    private int manpower;

    /**
     * Create a NGO with specified name, password for the account and the amount of manpower 
     * 
     * @param name the name of the NGO
     * @param password the password for the account
     * @param manpower the amount of manpowwer
     */
    public Ngo(String name ,String password,int manpower){
        super(name,password);
        this.manpower = manpower;
    }

    /**
     * Returns a int representation of the amount of manpower 
     * @return a int representation of the amount of manpower
     */
    public int  getManpower(){
        return manpower;
    }

    /**
     * Compares this NGO with the specified NGO based on their man power for order.
     * @param other the NGO to be compared.
     * @return a negative integer, zero, or a positive integer as this NGO is less than, equal to, or greater than the specified NGO.
     */
    public int compareTo(Ngo other) {
        return other.manpower - this.manpower;
    }

    /**
     *  Returns a string representation of the name of the NGO
     * @return a string representation of the name of a NGO
     */
    public String toString() {
        return super.name;
    }

    /**
     *  Returns a string representation of the name of the NGO, the password for the account and the amount of manpower seperated by comma in order to save as csv file.
     * @return a string representation of the details of a NGO seperated by comma
     */
    public String toCSVString() {
        return name + "," + password + "," + manpower ;
        
    }
}
