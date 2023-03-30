import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class TestDonor {  
       
    public static void main(String[] args) throws IOException {
        donorMainPage();
    }
    
    private static void donorMainPage()throws IOException {
        Scanner input = new Scanner(System.in); 

        int x;
        System.out.println("\t\t\tDonor");
        System.out.println("\t\t\t-----");
        System.out.println();
        System.out.println("                 1 = Register Account ");
        System.out.println("                 2 = Login Account ");
        System.out.println("                 3 = Back to Main Page ");
        System.out.println();
        System.out.print("                  Enter 1 2 3 : ");

        try{
            x= input.nextInt();
            System.out.println();
            if (x==1)
            {
                registerDonor();
            }
            else if (x==2)
            {
                loginDonor();
            }
            else if (x==3)
            {
                MainPage.mainCaller();
    
            }
            else{
                System.out.println("Only 1 2 3 or 4 is allowed.");
                System.out.println();
                donorMainPage();
            }
        }

        catch (InputMismatchException ex){
            System.out.println("Only 1 2 3 or 4 is allowed.");
            System.out.println();
            donorMainPage();

        }

        System.out.println();



        input.close();
    }

    private static ArrayList<Donor> readDonorFromFile() throws IOException {
        ArrayList<Donor> donors = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("donorlist.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            donors.add (new Donor(items[0], items[1],items[2]));
        }
        return donors;
    }
    private static void saveDonorToFile(ArrayList<Donor> donors) 
	    throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < donors.size(); i++)
            sb.append (donors.get(i).toCSVString() + "\n");
        Files.write(Paths.get("donorlist.csv"), sb.toString().getBytes());
    }


    private static void loginDonor() throws IOException {
        String username=null;

        System.out.println ("          *--------------------------*");
        System.out.println ("          *                          *");
        System.out.println ("          *      Login as Donor      *");
        System.out.println ("          *                          *");
        System.out.println ("          *--------------------------*");
        System.out.println ();

        ArrayList<Donor> donors = readDonorFromFile();  

        Scanner input  = new Scanner(System.in);

        boolean isStop = false;

        while(isStop == false)
        {
            System.out.print("Enter your name : ");
            username =input.nextLine();
            if(username.equals("0")){
                isStop=true;
                donorMainPage();
            }
            System.out.print("Enter your password: ");
            String pw =input.nextLine();

            for(int i=0; i<donors.size(); ++i)
            {
                if( username.equals(donors.get(i).getName()) && pw.equals(donors.get(i).getPassword()))
                {
                    System.out.println ("~~ Login Successfully as a Donor ~~");

                    isStop = true;
                }
            }

            if(isStop == false)
            {
                System.out.println ("Error: Wrong username or wrong password. Try again or enter 0 to exit.");
            }

        }

        donorFunct(username);
    
        input.close();

    }

    private static void registerDonor() throws IOException {

        ArrayList<Donor> donors = readDonorFromFile(); 
        
        System.out.println ("          *--------------------------*");
        System.out.println ("          *                          *");
        System.out.println ("          *     Register as Donor    *");
        System.out.println ("          *                          *");
        System.out.println ("          *--------------------------*");
        System.out.println ();



        Scanner input = new Scanner(System.in); 
        System.out.print ("Enter your name as login id  : ");
        String name = input.nextLine();
        System.out.print ("Enter password you will like to set : ");
        String pw = input.nextLine();


        for(int i=0; i<donors.size(); ++i){

            while(donors.get(i).getName().equals(name)){
                System.out.print ("Same Name is already taken, please enter your full name/ nick name instead.\n");
                System.out.print ("Enter your name as login id  : ");
                name = input.nextLine();
                System.out.print ("Enter password you will like to set : ");
                pw = input.nextLine();
                i=0;
            }
        }

        System.out.print ("Enter your phone number : ");
        String phoneNum = input.nextLine(); 
        donors.add (new Donor(name,pw,phoneNum));

        System.out.println ("~~  Donor Account Created  ~~");

        System.out.println ("Details saved to file...");
        saveDonorToFile (donors);
        donorMainPage();
        input.close();

    }
    
    private static void donorFunct(String username) throws IOException {

        Scanner input  = new Scanner(System.in);

        System.out.println ();
        System.out.println ();
        System.out.println ("          *--------------------------*");
        System.out.println ("          *                          *");
        System.out.println ("          *          Function        *");
        System.out.println ("          *                          *");
        System.out.println ("          *--------------------------*");
        System.out.println ();

        System.out.println("          1 = Donate Aids ");
        System.out.println("          2 = View List of Aids Donated  ");
        System.out.println("          3 = View List of NGOs receiving the aids ");
        System.out.println("          4 = Logout ");

        System.out.println();
        System.out.print("                  Enter 1 2 3 4: ");


        try{

            int x= input.nextInt();
            System.out.println();


            if (x==1)
            {
                donateAids(username);
            }
            else if (x==2)
            {
                viewAidsDonate(username);
            }
            else if (x==3)
            {
                viewNGORecieveAids(username);
            }
            else if (x==4)
            {
                donorMainPage();
            }
            else{
                System.out.println("Invalid Choice");
                donorFunct(username);
            }
        }
        catch (InputMismatchException ex){
            System.out.println("Only 1 2 3 or 4 is allowed.");
            donorFunct(username);
        }


        input.close();
    }

    private static void donateAids(String donorName) throws IOException {

        Scanner input  = new Scanner(System.in);
        ArrayList<ItemDonate> itemDonates = readAidListFromFile();
        System.out.println ("          *--------------------------*");
        System.out.println ("          *                          *");
        System.out.println ("          *        Donate Aids       *");
        System.out.println ("          *                          *");
        System.out.println ("          *--------------------------*");
        System.out.println ();

        System.out.print("Please enter the name of the item :");
        String itemdonate = input.nextLine();
        System.out.print("Please enter the quantity that you would like to donate for "+ itemdonate +" : ");
        try{
            int quantity = input.nextInt();
            input.nextLine(); 
        
            boolean same =false;
            for (int j=0; j<itemDonates.size(); j++){

                if (itemdonate.equals(itemDonates.get(j).getItemName()) && donorName.equals(itemDonates.get(j).getDonorName())){
                    same=true;
                    Integer newQuantity=itemDonates.get(j).getQuantity()+quantity;
                    itemDonates.set(j,new ItemDonate(itemDonates.get(j).getDonorName(),itemDonates.get(j).getItemName(),newQuantity));
                    saveAidListToFile(itemDonates);
                    System.out.println ("Donation details saved to file...");
                    donorFunct(donorName);
                    break;
                }

            }

            if(same==false){ 
                itemDonates.add (new ItemDonate(donorName,itemdonate,quantity));
                System.out.println ("Donation details saved to file...");
                saveAidListToFile(itemDonates);
                donorFunct(donorName);
            }
        }

        catch(InputMismatchException ex){
            System.out.println("Quantity must be an integer.");
            donateAids(donorName);
        }

        input.close();
    }

    private static ArrayList<ItemDonate> readAidListFromFile() throws IOException {
        ArrayList<ItemDonate> itemDonates = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("aidList.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");

            int quantity = Integer.parseInt(items[2]);

            itemDonates.add (new ItemDonate(items[0], items[1],quantity));
        }
        return itemDonates;
    }

    private static void saveAidListToFile(ArrayList<ItemDonate> itemDonates) 
	    throws IOException
    {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itemDonates.size(); i++)
            sb.append (itemDonates.get(i).toCSVString() + "\n");
        Files.write(Paths.get("aidList.csv"), sb.toString().getBytes());
    }

    private static void viewAidsDonate(String donorName) throws IOException {

        ArrayList<ItemDonate> itemDonates = readAidListFromFile();
        System.out.println ("          *---------------------------*");
        System.out.println ("          *                           *");
        System.out.println ("          * View List of Aids Donated *");
        System.out.println ("          *                           *");
        System.out.println ("          *---------------------------*");
        System.out.println ();
        
        boolean isStop = false;

        while(isStop == false)
        {
            for(int i = 0; i < itemDonates.size(); i++){
                
                if(itemDonates.get(i).getDonorName().equals(donorName)){
                    System.out.println(" Aid       Quantity");
                    System.out.println("-------------------");
                    isStop=true;
                    break;
                }
            }
            if(isStop == false){
                System.out.println("You had not donate anything yet.");
                break;
            }
        }
        
        for(int i = 0; i < itemDonates.size(); i++){
            
            if(itemDonates.get(i).getDonorName().equals(donorName)){
                System.out.print(String.format( "%-7s",itemDonates.get(i).getItemName()));
                System.out.println(String.format("%10d",itemDonates.get(i).getQuantity()));
               
            }
        }
        donorFunct(donorName);

    }

    private static void viewNGORecieveAids(String donorName) throws IOException {

        ArrayList<Distribution> distributions = readDistributionsFromFile();

        System.out.println ("          *--------------------------------------*");
        System.out.println ("          *                                      *");
        System.out.println ("          * View List of NGOs receiving the aids *");
        System.out.println ("          *                                      *");
        System.out.println ("          *--------------------------------------*");
        System.out.println ();

        boolean found =false;

        for (int i = 0; i < distributions.size(); i++){
            if(donorName.equals(distributions.get(i).getDonorName())){
                found=true;
              
            }
        }
        
        if(found==false){
            System.out.println ("Matching of the aids you donated is yet to be done or you haven't donate anything");
        }
        else if (found==true){
            System.out.println("Item      Quantity   NGO     Status");
        }

        for (int i = 0; i < distributions.size(); i++){

            if(donorName.equals(distributions.get(i).getDonorName())){
                System.out.println(String.format("%-7s",distributions.get(i).getItemName())
                +String.format("%8d",distributions.get(i).getQuantity())
                +String.format("%8s",distributions.get(i).getNgoName())
                +String.format("%14s",distributions.get(i).getStatus()));
            }

        }

        donorFunct(donorName);
    }

    private static ArrayList<Distribution> readDistributionsFromFile() throws IOException {
        ArrayList<Distribution> distributions = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("DistributionFile.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");

            int quantity = Integer.parseInt(items[3]);
            int manPower = Integer.parseInt(items[5]);

            distributions.add (new Distribution(items[0],items[1],items[2],quantity,items[4],manPower,items[6]));
        }
        return distributions;
    } 
}
