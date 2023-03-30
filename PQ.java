import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PQ {

    static int count =0;

        
    static void mainCaller() throws IOException
    {
        count++;
        if(count!=0){
        // Calling the main() method]

            main(null);
        }
    }
    public static ArrayList<Distribution> readDistributionFromFile() throws IOException {
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


    public static void main(String [] args)  throws IOException{
        ArrayList<Distribution> distributionList = readDistributionFromFile();
        PriorityQueue<Ngo> queue = new PriorityQueue<>();
        ArrayList<Ngo> ngoList = readNGOFromFile();
        Scanner input = new Scanner(System.in);

        String choice;
        int ans=0;

        try{
            do{
                System.out.println();
                System.out.println("List of NGO ");
                System.out.println(ngoList.toString());
                System.out.println("Current queue: "+queue);
                System.out.println();
    
                System.out.print("1 - Enqueue an NGO\n"
                +"2 - Dequeue an NGO\n"
                +"0 - Back to DC main page \n");
    
                ans=input.nextInt();
                boolean found = false;
                boolean inNgoList = false;
                boolean inDistributionFile = false;

                if(ans==1){
                    System.out.print("Enter NGO to be added into the queue: ");
                    choice = input.next();


                    for (Ngo item : queue) {

                        if (item.toString().equals(choice)){
                            System.out.println();
                            System.out.print( choice +" already in queue.\n");
                            found=true;
                            break;
                        }

                    }

                    for( int i=0;i<distributionList.size();i++){

                        // NGO not in distribution file
                        
                        for( int j=0;j<distributionList.size();j++){

                            if(choice.equals(distributionList.get(i).getNgoName())){
                                
                                inDistributionFile= true;
                                
                                break;

                            }
                        }

                        for( int j=0;j<ngoList.size();j++){
                            if(ngoList.get(j).getName().equals(choice)){

                                inNgoList=true;
                                break;
                            }
                        }

                        for (Ngo item : ngoList){

                            if ( item.getName().equals(choice)  && found==false && inNgoList==true && !queue.contains(item) && distributionList.get(i).getNgoName().equals(choice)){
                                queue.add(item);
                                break;
                            }

                        }

                    
                    }
                    
                    if(inNgoList==false){
                        System.out.println();
                        System.out.println("Ngo does not exist.");
                    }

                    if(inDistributionFile==false && inNgoList==true){
                        System.out.println();
                        System.out.println(choice+" is not added into queue as the required aids that this NGO require is yet to be fulfill");
                    }

                    System.out.println("Current queue : "+queue);
                    System.out.println();
                    

                }

                else if(ans==2){

                    if (!queue.isEmpty()){

                        System.out.println (queue.peek());

                        System.out.println();

                        System.out.println("Donor       Phone        Aids     Quantity     Ngo        Manpower      Status");
                        for( int i=0;i<distributionList.size();i++){
                            if(queue.peek().toString().equals(distributionList.get(i).getNgoName())){
                                distributionList.get(i).changeStatus("Collected");
                                distributionList.set(i, distributionList.get(i));
                                
                                saveDistributionToFile(distributionList);
                            }
                
                            System.out.println(String.format("%-10s",distributionList.get(i).getDonorName()) + 
                                String.format("%3s",distributionList.get(i).getphoneNum())  +
                                String.format("%12s",distributionList.get(i).getItemName()) +
                                String.format("%7s",distributionList.get(i).getQuantity())  +
                                String.format("%12s",distributionList.get(i).getNgoName())  +
                                String.format("%12s",distributionList.get(i).getManpower()) +
                                String.format("%17s",distributionList.get(i).getStatus()) );
                        }

                        queue.poll();

                        System.out.println();

                    }
                    else{
                        System.out.println ("The queue is currently empty.");
                       
                    }


                }

                else if( ans==0){
                    DistributionC.DistributionCenter();
                }

                else {

                    System.out.println("Only 1 or 2 is allowed.");
                    System.out.println();

                }
                
            }while(ans!=0);
            
        }
        catch (InputMismatchException ex ){
                
            System.out.println("Only 1 or 2 is allowed.");
            System.out.println();

            mainCaller();
        }

        input.close();
    }

   

}




