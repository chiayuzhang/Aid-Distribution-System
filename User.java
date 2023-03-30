/**
 * An user is an individual or organization with a name and password.
 */
public class User {

    protected String name;
    protected String password;
   
    /**
     * Create a user with specified name and password for the account
     * 
     * @param name the name of the user
     * @param password the password for the account of the user
     */
    public User(String name, String password){
        this.name=name;
        this.password=password;
    
    }

    /**
     * Returns a string representation of the name of the user.
     * @return a string representation of the name of the user.
     */
    public String getName(){
        return name;
    }

    /**
     * Returns a string representation of the passsword of the account of the user.
     * @return a string representation of the passsword of the account of the user.
     */
    public String getPassword(){
        return password;
    }


}
