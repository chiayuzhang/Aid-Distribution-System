import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TestNgo {     
    public static void main(String[] args) throws IOException {

        mainPage();
    }
    private static ArrayList<Ngo> readNGOFromFile() throws IOException {
        ArrayList<Ngo> users = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("ngolist.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            int manPower = Integer.parseInt(items[2]);

            users.add (new Ngo(items[0], items[1],manPower));
        }
        return users;
    }

    private static void saveNGOToFile(ArrayList<Ngo> users) 
	    throws IOException
    {
        // read students.csv into a list of lines.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < users.size(); i++)
            sb.append (users.get(i).toCSVString() + "\n");
        Files.write(Paths.get("ngolist.csv"), sb.toString().getBytes());
    }

    private static void mainPage() throws IOException{

        try {
            Scanner v = new Scanner(System.in);

            System.out.println("|------------------------------------|");
            System.out.println("|              NGO                   |");
            System.out.println("|------------------------------------|");
            System.out.println("| 1. Register Account                |");
            System.out.println("| 2. Login Account                   |");
            System.out.println("| 3. Back to main page               |");
            System.out.println("|                                    |");
            System.out.println("| Enter 1 2 or 3 to choose it.       |");
            System.out.println("|------------------------------------|");
            int x = v.nextInt();

            
            if (x == 1){
                registerNGO();
            }
            else if( x == 2){
                loginNGO();
            }
            else if( x == 3){
                MainPage.mainCaller();
            }
            else{
                System.out.println("Error : Enter wrong number.");
                mainPage();
            }
            v.close();
        } catch (InputMismatchException e){ 
            System.out.println("Error : Incorrect Input");
            System.out.println();
            mainPage();
        }
    }

    private static void registerNGO() throws IOException{

        try{
            ArrayList<Ngo> user = readNGOFromFile();
            Scanner v = new Scanner(System.in);
            String username = null;
            String password = null;
            int manpower =0;

            System.out.println();
            System.out.println("|------------------------------------|");
            System.out.println("|          Regsiter Account          |");
            System.out.println("|------------------------------------|");

            boolean correct = false;
            //boolean isCorrect = false;

            while (correct == false){
                System.out.print("     Enter organization name: ");
                username = v.nextLine();
                System.out.print("     Enter a password: ");
                password = v.nextLine();
                System.out.print("     Enter the manpower you have: ");
                manpower = v.nextInt();
                v.nextLine();
                System.out.println();

                boolean isCorrect = false;
                for (int i=0; i<user.size(); i++){
                    if (username.equals(user.get(i).getName())){
                        System.out.println("Error : The name had been use by other, Please enter other name.");
                        System.out.println();
                        isCorrect =true;
                    }
                }
                
                if (isCorrect == false){
                    user.add(new Ngo(username,password,manpower));
                    saveNGOToFile(user);
                    correct = true;
                }

            }

            System.out.println(" ~~Account create succesfull~~ ");
            System.out.println();
            System.out.println();
            mainPage();
            v.close();
        }
        catch (InputMismatchException e){
            System.out.println("Error : Incorrect Input");
            System.out.println();
            mainPage();
        }
    }

    private static void loginNGO() throws IOException{

        try{
            String username = null;
            Scanner v = new Scanner(System.in);

            System.out.println();
            System.out.println("|------------------------------------|");
            System.out.println("|          Login    Account          |");
            System.out.println("|------------------------------------|");
            System.out.println();

            ArrayList<Ngo> users = readNGOFromFile(); // old 

            boolean isUser = false;

            while(isUser == false){
                System.out.print("  Enter username: ");
                username = v.nextLine();
                System.out.print("  Enter password: ");
                String password = v.nextLine();

                for (int i=0; i<users.size(); ++i){
                    if( username.equals(users.get(i).getName()) && password.equals(users.get(i).getPassword()) ){ 
                        System.out.println("****Login succesfull****");
                        isUser = true;
                        System.out.println(username);
                        NGOfunc(username);
                    }
                }
                if (isUser == false){
                    System.out.println();
                    System.out.println("****Error : Enter wrong username or password****");
                }
            }
            v.close();
        } 
        catch (InputMismatchException e){
            System.out.println("Error : Incorrect Input");
            System.out.println();
            mainPage();
        }
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

    private static void saveItemToFile(ArrayList<ItemReceive> itemRequire) 
	    throws IOException
    {
        // open the csv file
        File file = new File("receiveAidList.csv");
        PrintWriter writer = new PrintWriter(file);
        // rewrite it / clear it 
        writer.print("");
        writer.close();


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itemRequire.size(); i++)
            sb.append (itemRequire.get(i).toCSVString() + "\n");
        Files.write(Paths.get("receiveAidList.csv"), sb.toString().getBytes());
    }


    private static void NGOpage(String name) throws IOException{

        try{
            Scanner v = new Scanner(System.in);
            ArrayList<ItemReceive> itemRequire = readItemFromFile(); // all item inside csv file , change the quantity inside list
            
            String item=null;
            int quan=0;

            System.out.println("|------------------------------------|");
            System.out.println("|             NGO Page               |");
            System.out.println("|------------------------------------|");
            System.out.println();

            System.out.println("How many item you need?");
            System.out.println("Enter the number of item you need: ");
            int num = v.nextInt();
            v.nextLine();

            for (int i=1; i<=num; i++ ){
                System.out.println("Enter the name of the " + i + " item you need? ");
                item = v.nextLine();
                System.out.println("How many " + item + " do you need?");
                quan = v.nextInt();
                v.nextLine();
                    
                System.out.println();

                boolean correct = false;
                for (int j=0; j<itemRequire.size(); j++){
                    if (item.equals(itemRequire.get(j).getItemName()) && name.equals(itemRequire.get(j).getNgoName())){
                        int quantity = itemRequire.get(j).getQuantity();
                        itemRequire.get(j).setQuantity(quantity + quan); 
                        saveItemToFile(itemRequire);
                        correct = true;
                    }
                }

                if (!correct){
                    itemRequire.add(new ItemReceive(name,item,quan));
                    saveItemToFile(itemRequire);
                }
            }
            
            System.out.println();
            System.out.println("The items you require is store into the file.");

            NGOfunc(name);
            v.close();
        } 
        catch (InputMismatchException e){
            System.out.println("Error : Incorrect Input");
            System.out.println();
            NGOpage(name);
        }
    }

    private static void NGOfunc(String name) throws IOException{

        try{
            Scanner v = new Scanner(System.in);


            System.out.println();
            System.out.println("|------------------------------------|");
            System.out.println("|             NGO Page               |");
            System.out.println("|------------------------------------|");
            System.out.println("        1. Require Aids                                   ");
            System.out.println("        2. View list for Aids Receive and Donor of the aids    ");
            System.out.println("        3. Logout");
            System.out.println();
            System.out.println("Enter 1 2 or 3 to choose the function. ");
            int num = v.nextInt();

            if (num == 1){
                NGOpage(name);
               
            }
            else if (num ==2){
                viewList(name);

            }
            else if(num==3){
                mainPage();
            }
            else{
                System.out.println("Error :Enter wrong number.");
                NGOfunc(name);
            }
            v.close();
        } 
        catch (InputMismatchException e){
           System.out.println("Error : Incorrect Input");
           System.out.println();
           NGOfunc(name);
        }
    }

    private static ArrayList<Distribution> readListFromFile() throws IOException {
        ArrayList<Distribution> lists = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("DistributionFile.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            int quan = Integer.parseInt(items[3]);
            int manpower = Integer.parseInt(items[5]);

            lists.add (new Distribution(items[0],items[1],items[2],quan,items[4],manpower,items[6]));
        }
        return lists;
    }
    

    private static void viewList(String name) throws IOException{

        ArrayList<Distribution> lists = readListFromFile();
        boolean inDistributioinFile = false;

        for (int i=0; i<lists.size(); i++){
            if(name.equals(lists.get(i).getNgoName()))
            inDistributioinFile = true;
        }


        if (inDistributioinFile == false){
            System.out.println();
            System.out.println("Error : You haven't receive any aids yet.");
            System.out.println();
        }
        else{
            System.out.println(" ~~ List for the Aid Receive ~~ ");
            System.out.println("------------------------------");
            System.out.println("ITEM      |      QUANTITY      |     Donor Name    |   Status");

            for (int i=0; i<lists.size();i++){
                if (name.equals(lists.get(i).getNgoName())){
                    System.out.println(String.format("%-10s",lists.get(i).getItemName()) 
                    + String.format("%11s",lists.get(i).getQuantity()) 
                    + String.format("%22s",lists.get(i).getDonorName())
                    + String.format("%20s",lists.get(i).getStatus()));
                }
            }   
            System.out.println();
        } 
        NGOfunc(name);
    }
}

    


