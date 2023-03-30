import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;

class DistributionC {     
    public static void main(String[] args) throws IOException {

        DistributionCenter();
    }

    private static void mode() throws IOException{
        System.out.println("");
        System.out.println("\t\t\tCOLLECTION SIMULTION ");
        System.out.println("");

        ArrayList<Distribution> distributionList = readDistributionFromFile();

        if(distributionList.size()==0){
            System.out.println("No matching of aids have been done. Please match the aids first in order to add NGO to queue. ");
            DistributionCenter();
            
        }

        else{
            Scanner input = new Scanner(System.in);

            int y;
            System.out.println("\t\t\tQueue");
            System.out.println("\t\t\t----------------------------------------------------");
            System.out.println("               1 = FIFO (first-in-first-out) ");
            System.out.println("               2 = Priority (an NGO with higher manpower will have higher priority) ");
            System.out.println("               0 = Back to DC main page ");
            System.out.println();
            System.out.print("                 Enter Your Selections: ");

            try{
                y= input.nextInt();
                System.out.println();
                if (y==1)
                {  
                    Mode1.mainCaller(); // FIFO
                    
                }
                else if (y==2)
                {
                    PQ.mainCaller(); // Priority Queue 
                    
                }
                else if (y==0)
                {
                    DistributionCenter();
                }
                else{
                    System.out.println(" Only 1 2 or 0 is allowed." );
                    mode();
                }
            }
        
            catch (InputMismatchException ex) {
                System.out.println(" Only 1 2 or 0 is allowed." );
                mode();
            }
            input.close();
        }
        
    }


    public static void DistributionCenter() throws IOException{
        System.out.println();
        System.out.println("\t\t\t Welcome To DISTRIBUTION CENTER");
        System.out.println();

        Scanner input = new Scanner(System.in); 

        int x;
        System.out.println("\t\t\tDC");
        System.out.println("\t\t\t----------------------------------------------------");
        System.out.println();
        System.out.println("               1 = View All AID Donated,its receiver and NGOS. ");
        System.out.println("               2 = Match Aids One-to-One (1 donor to 1 NGO) ");
        System.out.println("               3 = Match aids One-to-Many (1 donor to many NGOs)");
        System.out.println("               4 = Match aids Many-to-One (many receiver to 1 NGO)");
        System.out.println("               5 = Match aids Many-to-Many (many receiver to many NGOs)");
        System.out.println("               6 = Queue");
        System.out.println("               7 = Back to main page ");
        System.out.println();
        System.out.print("                 Enter Your Selections: ");

         
        try{
            x= input.nextInt();
            System.out.println();
            if (x==1)
            {
                main1();  //Print Out Tabular Format of AID Donated and Donor , NGOS
                   
            }
            else if (x==2)
            {
                match1();
            }
            else if (x==3)
            {
                match2();
            }
            else if (x==4)
            {
                match3();
            }
            else if (x==5)
            {
                match4();
            }
            else if (x==6)
            {
               mode();
            }
            else if (x==7)
            {
                MainPage.mainCaller();
    
            }
            else{
                System.out.println("Invalid Choice");
                DistributionCenter();
            }
        }

        catch (InputMismatchException ex) {
            System.out.println(" Only 1 2 3 4 5 6 or 7 is allowed." );
            DistributionCenter();
        }
            input.close();
    }

    //Selection 1 to view 
    private static void main1()throws IOException {
        String aidlist = "aidList.csv";
        BufferedReader reader = null;
        String line = "";
        System.out.println("DONOR DETAIL");
        System.out.println("Donor:         Aids:      Quantity: ");

        try{
            reader = new BufferedReader(new FileReader(aidlist));
            while((line = reader.readLine()) !=null){
                String[] row = line.split(",");
                for(String index : row){
                    System.out.printf("%-15s", index);
                }
                System.out.println();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                reader.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        String ngo = "receiveAidList.csv";
        BufferedReader reader2 = null;
        String line2 = "";
        System.out.println();
        System.out.println("NGO DETAIL");
        System.out.println("NGO:       Aids:      Quantity: ");

        try{
            reader2 = new BufferedReader(new FileReader(ngo));
            while((line2 = reader2.readLine()) !=null){
                String[] row = line2.split(",");
                for(String index : row){
                    System.out.printf("%-13s", index);
                }
                System.out.println();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                reader2.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("After Distribution ");
        printDistributionFromFile();
        DistributionCenter();
    }

    //MATCHING RELATION FUNCTION//
    //ONE TO ONE
    private static void match1() throws IOException {
      
        ArrayList<Distribution> distributionList = readDistributionFromFile();

        //Read DONOR
        ArrayList<ItemDonate> donorAs = readDonorAidFromFile();
        //Read NGO
        ArrayList<ItemReceive> receiver= readreceiverFromFile();
        //Read USER
        ArrayList<Ngo> users = readNGOFromFile();
        //Read Donor phoneNum
        ArrayList<Donor> donor = readDonorFromFile();

        System.out.println("-------One To One Relation-------");
        System.out.println("Donor:   Phone:     Aids:   Quantity:  NGO:   Manpower:   Status:");

        boolean found =false;
        for(int i =0; i <donorAs.size(); i++){
            String phoneNum =null;
            for (int y =0; y <donor.size(); y++){
                if (donorAs.get(i).getDonorName().equals(donor.get(y).getName()))
                    phoneNum =donor.get(y).getphoneNum();}
            for (int j = 0; j <receiver.size(); j++){
                int manpower = 0;
                for(int a=0; a <users.size(); a++){
                    if(receiver.get(j).getNgoName().equals(users.get(a).getName()))
                        manpower = users.get(a).getManpower();}

                if(donorAs.get(i).getItemName().equals(receiver.get(j).getItemName()) && (donorAs.get(i).getQuantity()==receiver.get(j).getQuantity())){
                    System.out.printf(String.format("%-6s",donorAs.get(i).getDonorName())
                    +String.format("%10s",phoneNum)
                    + String.format("%9s",donorAs.get(i).getItemName()) 
                    + String.format("%8d",receiver.get(j).getQuantity())
                    +String.format("%10s",receiver.get(j).getNgoName())
                    +String.format("%8d",manpower)
                    +String.format("%15s","Reserved"));

                    System.out.println();
                   

                    for(int f=0;f<distributionList.size();f++){
                       
                        if(donorAs.get(i).getDonorName().equals(distributionList.get(f).getDonorName()) && 
                            phoneNum.equals(distributionList.get(f).getphoneNum()) &&
                            donorAs.get(i).getItemName().equals(distributionList.get(f).getItemName()) &&
                            receiver.get(j).getQuantity()==(distributionList.get(f).getQuantity()) &&
                            receiver.get(j).getNgoName().equals(distributionList.get(f).getNgoName()) &&
                            manpower==distributionList.get(f).getManpower()){
                                found=true;
                                break;

                        }
                    }

                    if (found == false){
                        donorAs.get(i).setQuantity(0);
                        distributionList.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), receiver.get(j).getQuantity(), receiver.get(j).getNgoName(), manpower,"Reserved"));
                        saveDistributionToFile(distributionList);
                    } 
                }
            }
        }
        
        DistributionCenter();
    }
    
    //ONE TO MANY
    private static void match2() throws IOException{

        ArrayList<Distribution> distributionList = readDistributionFromFile();

        //Read DONOR
        ArrayList<ItemDonate> donorAs = readDonorAidFromFile();
        //Read NGO
        ArrayList<ItemReceive> receiver= readreceiverFromFile();
        //Read USER
        ArrayList<Ngo> users = readNGOFromFile();
        //Read Donor phoneNum
        ArrayList<Donor> donor = readDonorFromFile();

        System.out.println("-------One To Many Relation-------");
        System.out.println("Donor:   Phone:     Aids:   Quantity:   NGO:   Manpower:   Status:");
        boolean found =false;
        for(int i =0; i <donorAs.size(); i++){
            int numOfReceiver=0;              //Calculate how much nGo need that item
            int sumreceiverneed =0;
            String phoneNum =null;
            for (int y =0; y <donor.size(); y++)
            {if (donorAs.get(i).getDonorName().equals(donor.get(y).getName()))
                    phoneNum =donor.get(y).getphoneNum();}

            for (int k = 0; k <receiver.size(); k++){
                if(donorAs.get(i).getItemName().equals(receiver.get(k).getItemName()) 
                && donorAs.get(i).getQuantity()!=(receiver.get(k).getQuantity())){
                    numOfReceiver++;
                    sumreceiverneed=sumreceiverneed+(receiver.get(k).getQuantity());
                }
            }
            for (int j = 0; j <receiver.size(); j++){
                int manpower = 0;
                for(int a=0; a <users.size(); a++){
                    if(receiver.get(j).getNgoName().equals(users.get(a).getName())){
                        manpower = users.get(a).getManpower();
                    }
                }
                if (donorAs.get(i).getItemName().equals(receiver.get(j).getItemName())
                && (donorAs.get(i).getQuantity() > receiver.get(j).getQuantity()) 
                && numOfReceiver>=2 && sumreceiverneed==donorAs.get(i).getQuantity())
                {  
                    System.out.printf(String.format("%-6s",donorAs.get(i).getDonorName())
                    + String.format("%10s",phoneNum) 
                    + String.format("%9s",donorAs.get(i).getItemName()) 
                    + String.format("%8d",receiver.get(j).getQuantity())
                    +String.format("%10s",receiver.get(j).getNgoName())
                    +String.format("%8d",manpower)
                    +String.format("%15s","Reserved"));
                    System.out.println();
                   

                    for(int f=0;f<distributionList.size();f++){
                        if(donorAs.get(i).getDonorName().equals(distributionList.get(f).getDonorName()) && 
                            phoneNum.equals(distributionList.get(f).getphoneNum()) &&
                            donorAs.get(i).getItemName().equals(distributionList.get(f).getItemName()) &&
                            receiver.get(j).getQuantity()==(distributionList.get(f).getQuantity()) &&
                            receiver.get(j).getNgoName().equals(distributionList.get(f).getNgoName()) &&
                            manpower==distributionList.get(f).getManpower()){
                                found=true;
                                break;
                        }
                    }
                    

                    if (found == false){
                        //Write the result into  DistributionFile.csv.
                        distributionList.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), receiver.get(j).getQuantity(), receiver.get(j).getNgoName(), manpower, "Reserved"));
                        saveDistributionToFile(distributionList);
                    }
                }
            }
        }
 
        DistributionCenter();
    }

    //MANY TO ONE
    private static void match3() throws IOException{
        ArrayList<Distribution> distributionList = readDistributionFromFile();

        //Read DONOR
        ArrayList<ItemDonate> donorAs = readDonorAidFromFile();
        //Read NGO
        ArrayList<ItemReceive> receiver= readreceiverFromFile();
        //Read USER
        ArrayList<Ngo> users = readNGOFromFile();
        //Read Donor phoneNum
        ArrayList<Donor> donor = readDonorFromFile();

        System.out.println("-------Many to One Relation-------");
        System.out.println("Donor:   Phone:     Aids:   Quantity:  NGO:   Manpower:   Status:");
        boolean found =false;
        for(int j = 0; j <receiver.size(); j++){  
            int numOfDonor=0;            
            int sumDonorDonate  =0;
            for (int i =0; i <donorAs.size(); i++){
                if(donorAs.get(i).getItemName().equals(receiver.get(j).getItemName())
                && donorAs.get(i).getQuantity()!=(receiver.get(j).getQuantity())){
                    numOfDonor++;
                    sumDonorDonate =sumDonorDonate +(donorAs.get(i).getQuantity());
                }
            } 

            for (int i = 0; i <donorAs.size(); i++){
                String phoneNum =null;
                for (int y =0; y <donor.size(); y++)
                {if (donorAs.get(i).getDonorName().equals(donor.get(y).getName()))
                    phoneNum =donor.get(y).getphoneNum();}
                int manpower = 0;
                for(int a=0; a <users.size(); a++){
                    if(receiver.get(j).getNgoName().equals(users.get(a).getName()))
                        manpower = users.get(a).getManpower();
                    }
                if (receiver.get(j).getItemName().equals(donorAs.get(i).getItemName()) 
                && (donorAs.get(i).getQuantity() < receiver.get(j).getQuantity() 
                && numOfDonor>=2)&& sumDonorDonate ==receiver.get(j).getQuantity())
                {  
            
                    System.out.printf(String.format("%-7s",donorAs.get(i).getDonorName())
                    + String.format("%10s",phoneNum)
                    + String.format("%9s",donorAs.get(i).getItemName())
                    + String.format("%7d",donorAs.get(i).getQuantity())
                    +String.format("%9s",receiver.get(j).getNgoName())
                    +String.format("%8d",manpower)
                    +String.format("%15s","Reserved"));
                    System.out.println();

                   

                    for(int f=0;f<distributionList.size();f++){
                        if(donorAs.get(i).getDonorName().equals(distributionList.get(f).getDonorName()) && 
                            phoneNum.equals(distributionList.get(f).getphoneNum()) &&
                            donorAs.get(i).getItemName().equals(distributionList.get(f).getItemName()) &&
                            donorAs.get(i).getQuantity()==(distributionList.get(f).getQuantity()) &&
                            receiver.get(j).getNgoName().equals(distributionList.get(f).getNgoName()) &&
                            manpower==distributionList.get(f).getManpower()){
                                found=true;
                                break;

                        }
                    }

                    if (found == false){
                        //Write the result into  DistributionFile.csv.
                        distributionList.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), donorAs.get(i).getQuantity(), receiver.get(j).getNgoName(), manpower,"Reserved"));
                        saveDistributionToFile(distributionList);
                    }
                }
            }
        }
        DistributionCenter();
        
    }

    //MANY TO MANY
    private static void match4() throws IOException{
        ArrayList<Distribution> distributionList = readDistributionFromFile();
        ArrayList<Distribution> inOtoM = new ArrayList<>();
        ArrayList<Distribution> inMtoO = new ArrayList<>();
        ArrayList<Distribution> inOtoO = new ArrayList<>();

        //Read DONOR
        ArrayList<ItemDonate> donorAs = readDonorAidFromFile();
        //Read NGO
        ArrayList<ItemReceive> receiver= readreceiverFromFile();
        //Read USER
        ArrayList<Ngo> users = readNGOFromFile();
        //Read Donor phoneNum
        ArrayList<Donor> donor = readDonorFromFile();

        System.out.println("-------Many to Many Relation-------");
        System.out.println("Donor:   Phone:      Aids:   Quantity:   NGO:   Manpower:   Status:");
      
        boolean found =false;


        // one to one
        for(int i =0; i <donorAs.size(); i++){
            String phoneNum =null;
            for (int y =0; y <donor.size(); y++){
                if (donorAs.get(i).getDonorName().equals(donor.get(y).getName()))
                    phoneNum =donor.get(y).getphoneNum();}
            for (int j = 0; j <receiver.size(); j++){
                int manpower = 0;
                for(int a=0; a <users.size(); a++){
                    if(receiver.get(j).getNgoName().equals(users.get(a).getName()))
                        manpower = users.get(a).getManpower();}

                if(donorAs.get(i).getItemName().equals(receiver.get(j).getItemName()) && (donorAs.get(i).getQuantity()==receiver.get(j).getQuantity())){
                    inOtoO.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), receiver.get(j).getQuantity(), receiver.get(j).getNgoName(), manpower,"Reserved"));

                }
            }
        }




        // one to many 
        for(int i =0; i <donorAs.size(); i++){
            int numOfReceiver=0;              
            int sumreceiverneed =0;
            String phoneNum =null;
            for (int y =0; y <donor.size(); y++)
            {if (donorAs.get(i).getDonorName().equals(donor.get(y).getName()))
                    phoneNum =donor.get(y).getphoneNum();}

            for (int k = 0; k <receiver.size(); k++){
                if(donorAs.get(i).getItemName().equals(receiver.get(k).getItemName()) 
                && donorAs.get(i).getQuantity()!=(receiver.get(k).getQuantity())){
                    numOfReceiver++;
                    sumreceiverneed=sumreceiverneed+(receiver.get(k).getQuantity());
                }
            }
            for (int j = 0; j <receiver.size(); j++){
                int manpower = 0;
                for(int a=0; a <users.size(); a++){
                    if(receiver.get(j).getNgoName().equals(users.get(a).getName())){
                        manpower = users.get(a).getManpower();
                    }
                }
                if (donorAs.get(i).getItemName().equals(receiver.get(j).getItemName())
                && (donorAs.get(i).getQuantity() > receiver.get(j).getQuantity()) 
                && numOfReceiver>=2 && sumreceiverneed==donorAs.get(i).getQuantity())
                {  
     
                    inOtoM.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), receiver.get(j).getQuantity(), receiver.get(j).getNgoName(), manpower, "Reserved"));
                }
            }
        }


        // many to one 

        for(int j = 0; j <receiver.size(); j++){  
            int numOfDonor=0;           
            int sumDonorDonate  =0;
            for (int i =0; i <donorAs.size(); i++){
                if(donorAs.get(i).getItemName().equals(receiver.get(j).getItemName())
                && donorAs.get(i).getQuantity()!=(receiver.get(j).getQuantity())){
                    numOfDonor++;
                    sumDonorDonate =sumDonorDonate +(donorAs.get(i).getQuantity());
                }
            } 

            for (int i = 0; i <donorAs.size(); i++){
                String phoneNum =null;
                for (int y =0; y <donor.size(); y++)
                {if (donorAs.get(i).getDonorName().equals(donor.get(y).getName()))
                    phoneNum =donor.get(y).getphoneNum();}
                int manpower = 0;
                for(int a=0; a <users.size(); a++){
                    if(receiver.get(j).getNgoName().equals(users.get(a).getName()))
                        manpower = users.get(a).getManpower();
                    }
                if (receiver.get(j).getItemName().equals(donorAs.get(i).getItemName()) 
                && (donorAs.get(i).getQuantity() < receiver.get(j).getQuantity() 
                && numOfDonor>=2)&& sumDonorDonate ==receiver.get(j).getQuantity())
                {  
                    inMtoO.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), donorAs.get(i).getQuantity(), receiver.get(j).getNgoName(), manpower,"Reserved"));
                }
            }
        }

        // many to many
        for (int i=0;i<donorAs.size();i++){

            int manpower = 0; 
            String phoneNum =null;
  

            for(int j=0;j<receiver.size();j++){


                if(donorAs.get(i).getItemName().equals(receiver.get(j).getItemName()) && donorAs.get(i).getQuantity()==receiver.get(j).getQuantity()){
                    donorAs.get(i).setQuantity(0);
                }

                for(int a=0; a <users.size(); a++){
                    if(receiver.get(j).getNgoName().equals(users.get(a).getName())){
                        manpower = users.get(a).getManpower();
                    }
                }
                for (int y =0; y <donor.size(); y++){
                    if (donorAs.get(i).getDonorName().equals(donor.get(y).getName()))
                    phoneNum =donor.get(y).getphoneNum();
                }

                if(donorAs.get(i).getItemName().equals(receiver.get(j).getItemName()) && donorAs.get(i).getQuantity()!=receiver.get(j).getQuantity() && donorAs.get(i).getQuantity()!=0){   

                    if(donorAs.get(i).getQuantity()>receiver.get(j).getQuantity() && receiver.get(j).getQuantity()!= 0 ) { // donation > need


                        donorAs.get(i).setQuantity(donorAs.get(i).getQuantity()-receiver.get(j).getQuantity());

                        for(int h=0;h<inOtoM.size();h++){
                                
                            if((donorAs.get(i).getDonorName().equals(inOtoM.get(h).getDonorName()) && 
                            phoneNum.equals(inOtoM.get(h).getphoneNum()) &&
                            donorAs.get(i).getItemName().equals(inOtoM.get(h).getItemName()) &&
                            receiver.get(j).getQuantity()==(inOtoM.get(h).getQuantity()) &&
                            receiver.get(j).getNgoName().equals(inOtoM.get(h).getNgoName()) &&
                            manpower==inOtoM.get(h).getManpower())){
                                
                                found=true;
                                break;
                            }
                        
                            found=false;
                            for(int f=0;f<distributionList.size();f++){

                                if((donorAs.get(i).getDonorName().equals(distributionList.get(f).getDonorName()) && 
                                    phoneNum.equals(distributionList.get(f).getphoneNum()) &&
                                    donorAs.get(i).getItemName().equals(distributionList.get(f).getItemName()) &&
                                    receiver.get(j).getNgoName().equals(distributionList.get(f).getNgoName()) &&
                                    manpower==distributionList.get(f).getManpower()) && 
                                    (receiver.get(j).getQuantity()==(distributionList.get(f).getQuantity()) || receiver.get(j).getQuantity()==0)){
                                    
                                        if(receiver.get(j).getQuantity()!=0){

                                            System.out.println(String.format("%-6s",donorAs.get(i).getDonorName())
                                            + String.format("%10s",phoneNum) 
                                            + String.format("%9s",donorAs.get(i).getItemName()) 
                                            + String.format("%8d",receiver.get(j).getQuantity())
                                            +String.format("%10s",receiver.get(j).getNgoName())
                                            +String.format("%8d",manpower)
                                            +String.format("%15s","Reserved"));
                                        }
                                        found=true;
                                        receiver.get(j).setQuantity(0);
                                        break;
        
                                }
                            }
                        }
        
    
                        if (found == false){                            
                            System.out.println(String.format("%-6s",donorAs.get(i).getDonorName())
                            + String.format("%10s",phoneNum) 
                            + String.format("%9s",donorAs.get(i).getItemName()) 
                            + String.format("%8d",receiver.get(j).getQuantity())
                            +String.format("%10s",receiver.get(j).getNgoName())
                            +String.format("%8d",manpower)
                            +String.format("%15s","Reserved"));
                        
                            // Write the result into  DistributionFile.csv.
                            distributionList.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), receiver.get(j).getQuantity(), receiver.get(j).getNgoName(), manpower, "Reserved"));
                          
                            saveDistributionToFile(distributionList);
                        }

                        receiver.get(j).setQuantity(0);

                    }

                    else if(donorAs.get(i).getQuantity()<receiver.get(j).getQuantity() && donorAs.get(i).getQuantity()!= 0 ){  // donation < need
                        
                        receiver.get(j).setQuantity(receiver.get(j).getQuantity()-donorAs.get(i).getQuantity());

                        for(int h=0;h<inMtoO.size();h++){

                            if((donorAs.get(i).getDonorName().equals(inMtoO.get(h).getDonorName()) && 
                            phoneNum.equals(inMtoO.get(h).getphoneNum()) &&
                            donorAs.get(i).getItemName().equals(inMtoO.get(h).getItemName()) &&
                            donorAs.get(i).getQuantity()==(inMtoO.get(h).getQuantity()) &&
                            receiver.get(j).getNgoName().equals(inMtoO.get(h).getNgoName()) &&
                            manpower==inMtoO.get(h).getManpower())){
                                found=true;
                                break;

                            }

                            found=false;

                            for(int f=0;f<distributionList.size();f++){
                                if(donorAs.get(i).getDonorName().equals(distributionList.get(f).getDonorName()) && 
                                    phoneNum.equals(distributionList.get(f).getphoneNum()) &&
                                    donorAs.get(i).getItemName().equals(distributionList.get(f).getItemName()) &&
                                    receiver.get(j).getNgoName().equals(distributionList.get(f).getNgoName()) &&
                                    manpower==distributionList.get(f).getManpower() &&
                                    (donorAs.get(i).getQuantity()==(distributionList.get(f).getQuantity()) || donorAs.get(i).getQuantity()==0)){
                                        if(donorAs.get(i).getQuantity()!=0){

                                            System.out.println(String.format("%-6s",donorAs.get(i).getDonorName())
                                            + String.format("%10s",phoneNum) 
                                            + String.format("%9s",donorAs.get(i).getItemName()) 
                                            + String.format("%8d",donorAs.get(i).getQuantity())
                                            +String.format("%10s",receiver.get(j).getNgoName())
                                            +String.format("%8d",manpower)
                                            +String.format("%15s","Reserved"));
                                        }
                                        found=true;
                                        donorAs.get(i).setQuantity(0);
                                        break;
        
                                }
                                
                            }
                        }


                        if (found == false ){
                            // Write the result into  DistributionFile.csv.
                            System.out.println(String.format("%-6s",donorAs.get(i).getDonorName())
                                            + String.format("%10s",phoneNum) 
                                            + String.format("%9s",donorAs.get(i).getItemName()) 
                                            + String.format("%8d",donorAs.get(i).getQuantity())
                                            +String.format("%10s",receiver.get(j).getNgoName())
                                            +String.format("%8d",manpower)
                                            +String.format("%15s","Reserved"));

                            distributionList.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), donorAs.get(i).getQuantity(), receiver.get(j).getNgoName(), manpower, "Reserved"));
                           
                            saveDistributionToFile(distributionList);
                        }
                    

                        donorAs.get(i).setQuantity(0);
                        
            
                    
                    }
                }
            
            }

            // to print and write available item ( item not match to any ngo )

            for(int f=0;f<distributionList.size();f++){
                if(donorAs.get(i).getDonorName().equals(distributionList.get(f).getDonorName()) && 
                    phoneNum.equals(distributionList.get(f).getphoneNum()) &&
                    donorAs.get(i).getItemName().equals(distributionList.get(f).getItemName()) &&
                    donorAs.get(i).getQuantity()==(distributionList.get(f).getQuantity()) &&
                    distributionList.get(f).getNgoName().equals("-") &&
                    manpower==distributionList.get(f).getManpower() ){
                        found=true;
                        break;

                }
            }

            if(donorAs.get(i).getQuantity()!=0 ){
                System.out.println(String.format("%-6s",donorAs.get(i).getDonorName())
                                            + String.format("%10s",phoneNum) 
                                            + String.format("%9s",donorAs.get(i).getItemName()) 
                                            + String.format("%8d",donorAs.get(i).getQuantity())
                                            +String.format("%10s","-")
                                            +String.format("%8d",0)
                                            +String.format("%15s","Available"));

                if(found==false){
                    
                    distributionList.add(new Distribution(donorAs.get(i).getDonorName(), phoneNum, donorAs.get(i).getItemName(), donorAs.get(i).getQuantity(), "-",0, "Available"));
                    saveDistributionToFile(distributionList);
                }
            }


        }
        DistributionCenter();
    }
    /////END OF MATCHING RELATION/////


    /////READ FILES FUNCTION/////
    //Read Ngo file
    private static ArrayList<Ngo> readNGOFromFile() throws IOException {
        ArrayList<Ngo> users = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("ngolist.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            int manpower = Integer.parseInt(items[2]);

            users.add (new Ngo(items[0], items[1],manpower));
        }
        return users;
    }

    //Read DonorAID
    private static ArrayList<ItemDonate> readDonorAidFromFile() throws IOException {
        ArrayList<ItemDonate> donorAs = new ArrayList<>();

        // read aidist.csv into a list of lines.
        List<String> lines = Files.readAllLines(Paths.get("aidList.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            int quan = Integer.parseInt(items[2]); // convert String to int
            donorAs.add(new ItemDonate(items[0], items[1],quan));
        }
        return donorAs;
    }

    //Read Receiver File
    private static ArrayList<ItemReceive> readreceiverFromFile() throws IOException {
        ArrayList<ItemReceive> receiver = new ArrayList<>();

        // read aidist.csv into a list of lines.
        List<String> lines = Files.readAllLines(Paths.get("receiveAidList.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            int quantity = Integer.parseInt(items[2]); // convert String to int
            receiver.add(new ItemReceive(items[0], items[1],quantity));
        }
        return receiver;
    }

    // Read Donor phoneNum
    private static ArrayList<Donor> readDonorFromFile() throws IOException {
        ArrayList<Donor> donor = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("donorlist.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            donor.add (new Donor(items[0],items[1],items[2]));
        }
        return donor;
    }
    //END OF READ FILES FUNCTION

    private static ArrayList<Distribution> readDistributionFromFile() throws IOException {
        ArrayList<Distribution> distributionList = new ArrayList<>();

        // read aidist.csv into a list of lines.
        List<String> lines = Files.readAllLines(Paths.get("DistributionFile.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            int quantity = Integer.parseInt(items[3]); // convert String to int
            int manpower = Integer.parseInt(items[5]);

            distributionList.add(new Distribution(items[0],items[1],items[2],quantity,items[4],manpower,items[6]));
        }
        return distributionList;
    }

    private static void printDistributionFromFile() throws IOException {
        ArrayList<Distribution> distributionList = readDistributionFromFile();

        System.out.println("Donor:   Phone:      Aids:   Quantity:  NGO:   Manpower:   Status:");
        for(int i=0; i<distributionList.size();i++){
            System.out.println(String.format("%-8s",distributionList.get(i).getDonorName())
                    + String.format("%8s",distributionList.get(i).getphoneNum())
                    + String.format("%8s",distributionList.get(i).getItemName()) 
                    + String.format("%8d",distributionList.get(i).getQuantity())
                    +String.format("%9s",distributionList.get(i).getNgoName())
                    +String.format("%9d",distributionList.get(i).getManpower())
                    + String.format("%15s",distributionList.get(i).getStatus()));
        }
    }

    private static void saveDistributionToFile(ArrayList<Distribution> distributions) throws IOException {

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < distributions.size(); i++)
                sb.append (distributions.get(i).toCSVString() + "\n");
            Files.write(Paths.get("DistributionFile.csv"), sb.toString().getBytes());
        }
}