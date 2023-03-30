/**
 * A DC (distribution center) is the place where the matching of aids is performed.
 */
public class Distribution {

    private String donorName;
    private String NGO;
    private String itemName;
    private int quantity;
    private int manPower;
    private String phoneNum;
    private String status;

    /**
     * Create a Distribution with specified donor name, phone number of that donor, aids received/donated, quantity of the aids, NGO name, man power of that NGO and the status of aids.
     * 
     * @param donorname the name of the donor
     * @param NGO the name of the receiver
     * @param itemname the name of item donated /received
     * @param quantity the quantity of the item 
     * @param manPower the manpower of the NGO (Receiver)
     * @param phoneNum the phone number of donor (Donater)
     * @param status the status of items 
     */
    public Distribution(String donorName,String phoneNum,String itemName,int quantity, String NGO, int manPower, String status){
        this.donorName = donorName;
        this.quantity=quantity;
        this.itemName=itemName;
        this.manPower=manPower;
        this.NGO =NGO;
        this.phoneNum=phoneNum;
        this.status =status;
    }

    /**
     * Returns a string representation of the name of the item donated / received
     * @return a string representation of the name of the item donated / received
     */
    public String getItemName(){
        return itemName;
    }

    /**
     * Returns a string representation of the name of the donor
     * @return a string representation of the name of the donor
     */
    public String getDonorName(){
        return donorName;
    }
    /**
     * Returns a string representation of the name of the NGO
     * @return a string representation of the name of the NGO
     */
    public String getNgoName(){
        return NGO;
    }

    /**
     * Returns a int representation of the quantity of the item donated / received
     * @return a int representation of the quantity of the item donated / received
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * Returns a int representation of the manpower of the NGO (Receiver)
     * @return a int representation of the manpower of the NGO (Receiver)
     */
    public int getManpower(){
        return manPower;
    }

    /**
     * Returns a int representation of the phone number of the Donor
     * @return a int representation of the phone number of the Donor
     */
    public String getphoneNum(){
        return phoneNum;
    }

    /**
     * Returns a String representation of the status of the aids
     * @return a String representation of the status of the aids
     */
    public String getStatus(){
        return status;
    }

    /**
     * To change the status of the adis
     * @param status the status of the aids
     */
    public void changeStatus(String status){
        this.status = status;
    }
 
    /**
    *  Returns a string representation of the name of the donor, the phone number of the donor, the name of item received or donated, quantity of the item, name of Ngo, the amount of manpower of NGO and status of the aids
    * @return a string representation of the details of the matching done 
    */
    public String toString() {
        return donorName +" "+ phoneNum +" "+ itemName +" "+ quantity +" "+ NGO +" "+ manPower +" " + status;
    }

    /**
     *  Returns a string representation of the name of the donor, the phone number of the donor, the name of item received or donated, quantity of the item, name of Ngo, the amount of manpower of NGO and status of the aids seperated by comma in order to save as csv file.
    * @return a string representation of the details of the matching done seperated by comma
     */
    public String toCSVString() {
        return donorName + "," + phoneNum + "," + itemName + "," + quantity + "," + NGO +"," + manPower + "," + status;
    }




}
