public class ItemReceive extends Item {
    private String ngoName;


    public ItemReceive( String ngoName,String itemName, int quantity){
        super(itemName,quantity);
        this.ngoName = ngoName;
      
    }

    public String getNgoName (){
        return ngoName;
    }

    public String toString() {
        return ngoName + " " + super.quantity + " " + super.itemName ;
    }

    public String toCSVString() {
    return ngoName + "," + super.itemName + "," + super.quantity;
    }
    
}
