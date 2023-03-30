public class ItemDonate extends Item {

    private String donorName;

    public ItemDonate( String donorName,String itemName, int quantity){
       super(itemName, quantity);
        this.donorName=donorName;
    }

    public String getDonorName(){
        return donorName;
    }

    public String toString() {
        return donorName +" "+ super.itemName +" "+ super.quantity;
    }
    public String toCSVString() {
       return donorName + "," + super.itemName + "," + super.quantity ;
    }

    
}
