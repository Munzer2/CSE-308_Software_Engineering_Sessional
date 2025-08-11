import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientServer c1 = new ClientServer("localhost",12345);
        Client curr = null;
        String Line = "";
        boolean login = false;
        boolean admin = false;
        Scanner scanner = new Scanner(System.in);
        while(true){
            Line = scanner.nextLine();
            if( Line.equalsIgnoreCase("Exit")){
                break ;
            }
            else{
                String arr[] = Line.split(" ");
                if(login){
                    if(admin){
                        if(arr[0].equalsIgnoreCase("I")){
                            c1.IncreasePrice(arr[1],curr,Double.parseDouble(arr[2]));
//                            System.out.println(ans);
                        }
                        else if(arr[0].equalsIgnoreCase("D"))
                        {
                            c1.DecreasePrice(arr[1],curr,Double.parseDouble(arr[2]));
//                            System.out.println(ans);
                        }
                        else if(arr[0].equalsIgnoreCase("C")){
                            c1.UpdateCount(arr[1],curr,Integer.parseInt(arr[2]));
//                            System.out.println(ans);
                        }
                        else {
                            System.out.println("Invalid command.");
                        }
                    }
                    else {
                        if(arr[0].equalsIgnoreCase("S")){
                            c1.Subscribe(arr[1],curr);
//                            System.out.println(retmssg);
                        }
                        else if(arr[0].equalsIgnoreCase("U")){
                            c1.Unsubsribe(arr[1],curr);
//                            System.out.println(retmssg);
                        }
                        else if(arr[0].equalsIgnoreCase("V")){
                            c1.ViewAllSubscribedStocks(curr);
//                            String arr1[] = res.split(" ");
//                            if(arr1.length != 0){
//                                System.out.println("The subscribed stocks are: ");
//                                for(String el : arr1){
//                                    System.out.println(el);
//                                }
//                            }
                        }
                        else {
                            System.out.println("Invalid command. Try again.");
                        }
                    }
                }
                else {
                    if(arr[0].equalsIgnoreCase("Login")){
                        login = true;
                        if(arr[1].equalsIgnoreCase("Admin")){
                            curr = new Client("Admin");
                            admin = true;
                        }
                        else{
                            curr = new Client(arr[1]);
                        }
                    }
                    else System.out.println("Login first!");
                }

            }


        }

        c1.closeAll();
    }

}