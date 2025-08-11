import java.io.Serializable;

public class Client implements clientInt, Serializable {
    private String name;

    Client( String name){
        this.name = name;
    }

    @Override
    public void updatePrice(Stock stock) {

    }

    @Override
    public void updateCount(Stock stock) {

    }

    public String getName(){
        return this.name;
    }
}
