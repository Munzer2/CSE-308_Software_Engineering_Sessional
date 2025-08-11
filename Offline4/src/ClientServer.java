import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientServer implements Serializable {
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    ClientServer(String address,int port) throws IOException {
        socket = new Socket(address,port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        new Thread(this::listenForMessages).start();
    }


    public void listenForMessages() {
        while(true){
            try {
                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof String) {
                        System.out.println((String) obj);
                    }
                }
            } catch (EOFException eofException) {
                System.out.println("Server closed the connection.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
    }

    public void SendReq(StockReq req) throws IOException, ClassNotFoundException {
        out.writeObject(req);
//        return in.readObject();
    }



    public void Subscribe(String stockName, Client client) throws IOException, ClassNotFoundException {
        StockReq req = new StockReq(stockName,"Add",client,0,0);
        SendReq(req);
    }


    public void Unsubsribe(String stockName, Client client) throws IOException, ClassNotFoundException {
        StockReq req = new StockReq(stockName,"Remove",client,0,0);
        SendReq(req);
    }

    public void IncreasePrice(String stockName,Client client,double ammount) throws IOException, ClassNotFoundException {
        StockReq req = new StockReq(stockName,"UpdatePriceI",client,ammount,0);
        SendReq(req);
    }

    public void DecreasePrice(String stockName,Client client,double ammount) throws IOException, ClassNotFoundException {
        StockReq req = new StockReq(stockName,"UpdatePriceD",client,ammount,0);
        SendReq(req);
    }

    public void UpdateCount(String stockName,Client client,int count) throws IOException, ClassNotFoundException {
        StockReq req = new StockReq(stockName,"UpdateCount",client,0,count);
        SendReq(req);
    }

    public void ViewAllSubscribedStocks(Client client) throws IOException, ClassNotFoundException {
        StockReq req= new StockReq(null, "View",client,0,0);
        SendReq(req);
    }


    public void closeAll() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

}