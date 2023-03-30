import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// FIFO Queue 
public class Mode1 {

    static int count =0;

        
    static void mainCaller() throws IOException
    {
        count++;
        if(count!=0){
        // Calling the main() method]

            main(null);
        }
    }
    public static void main(String[] args) throws IOException {
        FIFO();

    }

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

    private static ArrayList<ItemReceive> readItemFromFile() throws IOException {
        ArrayList<ItemReceive> itemRequire = new ArrayList<>();

        // read students.csv into a list of lines.
        List<String> lines = Files.readAllLines(Paths.get("receiveAidList.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            // items[0] is id, items[1] is name
            int num = Integer.parseInt(items[2]); // convert String to int
            //int manPower = Integer.parseInt(items[3]);
            itemRequire.add (new ItemReceive(items[0],items[1],num));
        }
        return itemRequire;
    }

    private static ArrayList<Ngo> readNGOFromFile() throws IOException {
        ArrayList<Ngo> ngoList = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("ngolist.csv"));
        for (int i = 0; i < lines.size(); i++) {

            // split a line by comma
            String[] items = lines.get(i).split(",");
            int manpower = Integer.parseInt(items[2]);
           
            ngoList.add (new Ngo(items[0], items[1],manpower));

        }
        return ngoList;
    }

    private static void saveDistributionToFile(ArrayList<Distribution> distributions) throws IOException {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < distributions.size(); i++)
            sb.append (distributions.get(i).toCSVString() + "\n");
        Files.write(Paths.get("DistributionFile.csv"), sb.toString().getBytes());
    }

    // Assignment 2 
    // FIFO

    private static void FIFO() throws IOException {
        LinkedList<String> status = new LinkedList<>();

        ArrayList<Distribution> list = readDistributionFromFile();
        ArrayList<ItemReceive> item = readItemFromFile();
        ArrayList<Ngo> ngo = readNGOFromFile();

        Scanner v = new Scanner(System.in); 

        boolean correct = false;
        boolean name = false;
        boolean ngoList = false;
        boolean itemList = false;
        boolean disList = false;
    

        try {
            while (!correct){
                System.out.println("List of NGO ");
                System.out.println(ngo.toString());
                System.out.println();
    
                System.out.println("FIFO queue: " + status);
                System.out.println("Option");
                System.out.println("1 - Enqueue an NGO");
                System.out.println("2 - Dequeue an NGO"); 
                System.out.println("0 - Exit");

                System.out.print("Command > ");
                int option = v.nextInt();

                if (option == 1){  // to insert the ngo name
                    String ngoName = v.next();

                    // find is ngo exist in ngo list
                    for (int j = 0; j < ngo.size(); j++) {
                        if (ngo.get(j).getName().equals(ngoName))
                        {
                            ngoList = true;
                            break;
                        }
                    }

                
                    if (ngoList == true){
                        for (int i=0; i<item.size(); i++){
                            if(item.get(i).getNgoName().equals(ngoName)){
                                itemList = true;
                                break;}
                        }

                        if(itemList == false)
                            System.out.println("Error : The Ngo Name is not added into queue as the required aids that this NGO require is yet to be fulfill");
                    }

                    else{
                        System.out.println("Error : The Ngo name does not exist in Ngo file");
                    }

                    // The ngo name must exist in ngo file 
                    // find is the ngo list exist in distribute list
                    if (itemList == true){
                        
                        for (int i=0; i<list.size(); i++){
                            if(list.get(i).getNgoName().equals(ngoName)){
                                disList = true;
                                break;
                            }
                        }
    
                        if(disList == false)
                            System.out.println("Error : The Ngo Name not exist in the Distribution file.");
                    }


                    // the name is exist in ngo file but not in distribute file
                    if(disList == false && itemList == true)
                        System.out.println("Error : The Ngo haven't recieve any aid yet.");
                    

                    // add the ngo name into queue
                    if(status.size() != 0) {
                        for(int i=0; i<status.size(); i++){
                            if(status.get(i).equals(ngoName)){
                                System.out.println("Error : The Ngo Name is already exist in the queue.");
                                name = true;
                                break;
                            }
                        }
                    }
                    
                    if(disList == true && itemList == true && name == false ){
                        status.add(ngoName);
                    }

                    // reset all the condition
                    name = false;
                    disList = false;
                    ngoList = false;
                    itemList = false;
                }
                
                else if (option == 2){

                    // check the queue is not empty
                    if (!status.isEmpty()){
                        System.out.println(status.get(0));
                        System.out.println("DC RECORDS");
                        
                        System.out.println("Donor       Phone        Aids     Quantity     Ngo        Manpower      Status");
                        for (int i=0; i<list.size(); i++){
                            if (status.get(0).equals(list.get(i).getNgoName())) {
                                list.get(i).changeStatus("Collected");
                                saveDistributionToFile(list);
                                System.out.println(String.format("%-10s",list.get(i).getDonorName()) + 
                                String.format("%3s",list.get(i).getphoneNum())  +
                                String.format("%12s",list.get(i).getItemName()) +
                                String.format("%7s",list.get(i).getQuantity())  +
                                String.format("%12s",list.get(i).getNgoName())  +
                                String.format("%12s",list.get(i).getManpower()) +
                                String.format("%17s",list.get(i).getStatus()) );
                            }
                            else{
                                saveDistributionToFile(list);
                                System.out.println(String.format("%-10s",list.get(i).getDonorName()) + 
                                String.format("%3s",list.get(i).getphoneNum())  +
                                String.format("%12s",list.get(i).getItemName()) +
                                String.format("%7s",list.get(i).getQuantity())  +
                                String.format("%12s",list.get(i).getNgoName())  +
                                String.format("%12s",list.get(i).getManpower()) +
                                String.format("%17s",list.get(i).getStatus()) );
                            }
                        }
                        // after display the table remove the ngo name in the list
                        status.remove(0);
                    }
                    else {
                        System.out.println("Error : The queue is empty.");
                    }
                    
                }
                
                else if (option == 0 )  // use to exit the function
                {
                    correct = true;
                    DistributionC.DistributionCenter();
                }
                
                else{
                    System.out.println("Error : Enter invalid number.");
                    System.out.println("Please enter the correct number (1, 2 or 0). ");
                }
                
                System.out.println();
            }
        } catch (InputMismatchException e){
            System.out.println("Error : Incorrect Input.");
            System.out.println("Please enter the correct number (1, 2 or 0). ");
            System.out.println();  
            FIFO();
        }
        v.close();
    }

}
