import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        StockServer server = new StockServer("init_stocks.txt");
        server.startServer(12345);
    }
}