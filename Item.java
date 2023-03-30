public class Item {
    protected String itemName;
    protected int quantity;


    public Item(String itemName, int quantity){
        this.itemName=itemName;
        this.quantity=quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;  
    }
    
    public String getItemName(){
        return itemName;
    }

    public int getQuantity(){
        return quantity;
    }


    
}
