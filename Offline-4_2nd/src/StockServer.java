import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class StockServer {
    private Map<String, ConcreteStocks> allStocks;

    public StockServer(String inpFile) throws FileNotFoundException {
        this.allStocks = new HashMap<>();
        this.initializeStocks(inpFile);
    }

    public void initializeStocks(String inp) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(inp));
        try(reader){
            String Line = "";
            while((Line = reader.readLine()) != null ){
                String arr[] = Line.split(" ");
                String stockName = arr[0];
                double price = Double.parseDouble(arr[2]);
                int count = Integer.parseInt(arr[1]);
                ConcreteStocks currStock = new ConcreteStocks(stockName,count, price);
                allStocks.put(stockName,currStock);
            }
        }
        catch(IOException err){
            System.out.println(err);
        }
    }

    public void startServer(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        try(server){
            System.out.println("Server started succesfully");
            while(true){
                Socket clientSocket = server.accept();
                System.out.println("Got another client!");
                new Thread(()->{
                    try {
                        handleClients(clientSocket);
                    } catch (IOException e) {
                        System.out.println("A client left.");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
        catch(IOException error){
            System.out.println(error);
        }

        server.close();
    }

    public void handleClients(Socket clientSocket) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        while(true){
            Object obj = in.readObject();
            StockReq req = (StockReq) obj;
            ConcreteStocks curr = allStocks.get(req.getStockName());
            if(req.getReq().equalsIgnoreCase("Add")){
                curr.addClient(req.getClient(),out);
//                curr.showSubscribers();
                out.writeObject(req.getClient().getName() + " subscribed to " + req.getStockName());
            }
            else if(req.getReq().equalsIgnoreCase("Remove")){
                curr.removeClient(req.getClient());
                out.writeObject(req.getClient().getName() + " unsubscribed from " + req.getStockName());
            }
            else if( req.getReq().equalsIgnoreCase("UpdatePriceI")){
                curr.setPrice(req.getPrice() + curr.getPrice());
                curr.NotifyAllSubs("Price for " + curr.getName() +" has increased.");
                System.out.println("Successfully notified all subscribers of " + curr.getName());
            }
            else if(req.getReq().equalsIgnoreCase("UpdatePriceD")){
                curr.setPrice(curr.getPrice() - req.getPrice());
                curr.NotifyAllSubs("Price for " + curr.getName() + " has decreased.");
                System.out.println("Successfully notified all subscribers of " + curr.getName());
            }
            else if(req.getReq().equalsIgnoreCase("UpdateCount")){
                curr.setCount(req.getCount());
                curr.NotifyAllSubs("Count of " + curr.getName() + " has changed");
                System.out.println("Successfully notified all subscribers of " + curr.getName());
            }
            else if(req.getReq().equalsIgnoreCase("View")){
                Client client = req.getClient();
                String ans = "";
                for(ConcreteStocks stock : allStocks.values()){
                    if(stock.Contains(client)) { ans += stock.getName(); ans += " "; }
                }
                out.writeObject(ans);
            }
        }
    }

    public void showAllStocks(){
        for (Map.Entry<String, ConcreteStocks> entry : allStocks.entrySet()) {
            String key = entry.getKey();
            ConcreteStocks stock = entry.getValue();
            System.out.println(key);
            stock.showSubscribers();
        }
    }


}
