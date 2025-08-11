import java.io.Serializable;

public class StockReq implements Serializable {
    private String stockName;
    String req;
    private Client client;
    private int Count;
    private double price;

    StockReq(String sname, String req, Client client,double price, int count){
        this.stockName = sname;
        this.req = req;
        this.client = client;
        this.Count = count;
        this.price = price;
    }
    public String getStockName(){
        return this.stockName;
    }

    public String getReq(){
        return this.req;
    }

    public Client getClient() {
        return this.client;
    }

    public double getPrice() { return this.price; }

    public int getCount() { return this.Count; }
}
