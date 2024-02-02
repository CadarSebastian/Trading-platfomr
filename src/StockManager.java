import java.util.HashMap;
import java.util.Map;



public class StockManager {
    private static StockManager instance;
    private Map<String, Stock> stocks;

    private StockManager() {
        stocks = new HashMap<>();
        initializeStocks();  
    }

    public static StockManager getInstance() {
        if (instance == null) {
            instance = new StockManager();
        }
        return instance;
    }

    
    public void addStock(String symbol, String name, double price, int quantity) {
        Stock stock = new Stock(symbol, name, price, quantity);
        stocks.put(symbol, stock);
    }

    
    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

   
    public void displayStocks() {
        System.out.println("Stocks available:");
        for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }
    }
    public void displayUserStocks(User user) {
        System.out.println("Stocks in your portfolio:");
        Map<String, Integer> userPortfolio = user.getPortfolio();

        for (Map.Entry<String, Integer> entry : userPortfolio.entrySet()) {
            String stockSymbol = entry.getKey();
            int quantity = entry.getValue();

            Stock stock = stocks.get(stockSymbol);
            if (stock != null && quantity > 0) {
                System.out.println(stock + " (Quantity: " + quantity + ")");
            }
        }}
    
    private void initializeStocks() {
    	addStock("AAPL", "Apple Inc.", 150.0, 50);
        addStock("GOOGL", "Alphabet Inc.", 2500.0, 20);
        addStock("AMZN", "Amazon.com Inc.", 3300.0, 30);
        addStock("MSFT", "Microsoft Corporation", 300.0, 40);
        addStock("TSLA", "Tesla Inc.", 800.0, 15);
        addStock("FB", "Meta Platforms Inc.", 300.0, 25);
        addStock("NVDA", "NVIDIA Corporation", 600.0, 20);
    }

    public Stock searchStock(String input) {
        
        Stock stock = stocks.get(input);
        if (stock != null) {
            return stock;
        }

        
        for (Stock s : stocks.values()) {
            if (s.getName().equalsIgnoreCase(input)) {
                return s;
            }
        }

       
        return null;
    }

}
    
    
