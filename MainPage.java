import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainPage {
    static int count =0;

        
    static void mainCaller()
    {
        count++;
        if(count!=0){
        // Calling the main() method]

            main(null);
        }
    }
    
    public static void main(String args[]){

        int x;
        Scanner input  = new Scanner(System.in);
        System.out.print("****************************************************\n");
        System.out.print("                                                    \n");
        System.out.print("               Aid Distribution System              \n");
        System.out.print("                                                    \n");
        System.out.print("****************************************************\n");
        System.out.println();
        System.out.print("                   1 = Donor \n");
        System.out.print("                   2 = NGO\n");
        System.out.print("                   3 = DC\n");
        System.out.print("                   4 = Exit\n");
        System.out.println();
        System.out.print("                Enter 1 2 3: "); 

        try{
            x=input.nextInt();
            System.out.println();
            if (x==1)
            {
                try
                {
                    TestDonor.main(args);
                }
                catch( IOException ioException)
                {
                    ioException.getMessage();
                }

            }
            else if (x==2)
            {
                try
                {
                    TestNgo.main(args);
                }
                catch( IOException ioException)
                {
                    ioException.getMessage();
                }
            }
            else if (x==3)
            {   
                try
                {
                    DistributionC.main(args);
                }
                catch( IOException ioException)
                {
                    ioException.getMessage();
                }
            }
            else if (x==4)
            {
                System.out.print("      ~ You exited the program ~");

            }

            else{
                System.out.println("Invalid Choice");
                mainCaller();
            }
            input.close();
        }
    
        catch (InputMismatchException ex){
            System.out.println("Only 1 2 3 or 4 is allowed.");
            mainCaller();
        }
    } 


}
