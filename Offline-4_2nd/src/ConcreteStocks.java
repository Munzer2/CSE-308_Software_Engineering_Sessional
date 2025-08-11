import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteStocks implements Stock, Serializable {

    private Map<Client, ObjectOutputStream> subscribers;
    private String name;
    private int count;

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int val){ this.count = val; }

    public void setPrice(double val) { this.price = val ; }

    public double getPrice() {
        return price;
    }

    private double price;

    ConcreteStocks(String name,int count, double price){
        this.subscribers = new HashMap<>();
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public void addClient(Client client,ObjectOutputStream out) {
        for(Client curr: subscribers.keySet()){
            if( client.getName().equalsIgnoreCase(curr.getName())){
                return;
            }
        }
        this.subscribers.put(client,out);
    }

    public void removeClient(Client client){
        for(Client curr: subscribers.keySet()){
            if( curr.getName().equalsIgnoreCase(client.getName())){
                this.subscribers.remove(curr);
                return;
            }
        }
    }

    public void showSubscribers() {
        System.out.println("Subscribers to " + name + ":");
        for (Client subscriber : subscribers.keySet()) {
            System.out.println(subscriber.getName());
        }
    }

    @Override
    public void NotifyAllSubs(String message) throws IOException {
        for(ObjectOutputStream out : subscribers.values()){
            System.out.println("Here");
            out.writeObject(message);
        }
    }


    public boolean Contains(Client client){
        for(Client curr : subscribers.keySet()){
            if(curr.getName().equalsIgnoreCase(client.getName())){
                return true;
            }
        }
        return false;
    }
}
