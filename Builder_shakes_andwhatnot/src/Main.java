import Builder.ShakeBuilder;
import Maker.*;
import Shakes.Shake;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static boolean isValidNum(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main(String[] args) {

        String choice;
        List<Shake> allShakes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while(true){
            choice = scanner.nextLine();
            if(choice.equals("o")){
                ShakerProducer maker = new ShakerProducer();
                ShakeBuilder builder = new ShakeBuilder();
                System.out.println("What type of shakes would you want?");
                System.out.println("1.Coffee");
                System.out.println("2.Cholcolate");
                System.out.println("3.Straberry");
                System.out.println("4.Vanilla");
                System.out.println("5.Zero");
                System.out.println("Press any of the numbers.");
                double num;
                String inp1="";
                while(!isValidNum(inp1)){
                    if(!inp1.isEmpty()) System.out.println("Invalid input. Try again.");
                    inp1 = scanner.nextLine();
                }
                num = Double.parseDouble(inp1);
                if(num == 1) maker.CoffeeShakeBuilder(builder);
                else if(num == 2) maker.chocolateShakeBuilder(builder);
                else if(num == 3) maker.strawberryShakeBuilder(builder);
                else if( num == 4 ) maker.vanillaShakeBuilder(builder);
                else maker.zeroShakeBuilder(builder);
                System.out.println("Now would you like some extra ingredients?");
                String inp = scanner.nextLine();
                if(inp.equalsIgnoreCase("YES")){
                    System.out.println("What would you like?");
                    System.out.println("1.Candy Price = 50$");
                    System.out.println("2.Cookies Price = 40$");
                    System.out.println("3.Both Price = 90");
                    int num1 = scanner.nextInt();
                    scanner.nextLine();
                    if(num1 == 1) maker.AddCandy(builder);
                    else if(num1 == 3){
                        maker.AddBoth(builder);
                    }
                    else maker.AddCookies(builder);
                    System.out.println("Would you like Almond milk? It will cost extra 60$.");
                    inp = scanner.nextLine();
                    if(inp.equalsIgnoreCase("YES")){
                        maker.AlmondMilk(builder);
                    }
                }
                System.out.println("Order Added");
                Shake curr = builder.getResult();
                builder.clear();
                allShakes.add(curr);
            }
            else if(choice.equals("e")){
                if(allShakes.size() == 0){
                    System.out.println("At least make one order.");
                }
                break;
            }
        }
        int tot_sum =0,cnt = 1;
        for(Shake x : allShakes){
            System.out.println("Order: " + cnt);
            x.Printdetails();
            tot_sum += x.getBasePrice();
            cnt++;
        }
        System.out.println("Total Price of all the shakes: " + tot_sum);
    }
}