import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends Subject{
	private String username;
    private String password;
    private Map<String, Integer> portfolio;
	private TransactionHistory transactionHistory;
	private double availableFunds=10000;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.portfolio = new HashMap<>();
        this.transactionHistory = new TransactionHistory();
        addObserver(transactionHistory);
    }


    public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setPortfolio(Map<String, Integer> portfolio) {
		this.portfolio = portfolio;
	}


	public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

	private List<Observer> observers = new ArrayList<>();
	private TradingStrategy tradingStrategy;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(List<String> transactions) {
        for (Observer observer : observers) {
            observer.update(transactions);
        }
    }
	
	public void buyStock(Stock stock, int quantity) {
        
        double totalCost = stock.getPrice() * quantity;
        if (totalCost <= getAvailableFunds()) {
            
            setAvailableFunds(getAvailableFunds() - totalCost);

           
            String symbol = stock.getSymbol();
            int currentQuantity = portfolio.getOrDefault(symbol, 0);
            portfolio.put(symbol, currentQuantity + quantity);

            System.out.println("Stock purchase successful. " + quantity + " shares of " +
                    stock.getName() + " added to your portfolio.");
            List<String> transactions = new ArrayList<>();
            transactions.add("Buy: " + quantity + " shares of " + stock.getSymbol());
            notifyObservers(transactions);
        } else {
            System.out.println("Insufficient funds. Stock purchase failed.");
        }
    }

    
    double getAvailableFunds() {
        
        return	availableFunds;
    }

    void setAvailableFunds(double funds) {
    	 this.availableFunds = funds;

         
         List<String> transactions = new ArrayList<>();
         transactions.add("Available funds updated to: $" + funds);
         notifyObservers(transactions);
       
    }
    public void executeTransaction(String transaction) {
        List<String> transactions = new ArrayList<>();
        transactions.add(transaction);
        notifyObservers(transactions);
    }
    public void displayTransactionHistory() {
        transactionHistory.displayTransactionHistory();
    }
	
    public TransactionHistory getTransactionHistory() {
    	System.out.println("History");
        return transactionHistory;
    }
    public void setTradingStrategy(TradingStrategy tradingStrategy) {
        this.tradingStrategy = tradingStrategy;
    }

    public void executeTradingStrategy() {
        //logic pe care nul voi putea implementa
    }

}
