import java.util.Map;
import java.util.Scanner;

public class Main {

	 public static void main(String[] args) {
	        UserManager userManager = UserManager.getInstance();
	        StockManager stockManager = StockManager.getInstance();
	        Scanner scanner = new Scanner(System.in);
	        StockPurchaseContext stockPurchaseContext = new StockPurchaseContext();
	        stockPurchaseContext.setCommand(new SearchCommand(stockManager, scanner));

        int choice;
        do {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleRegistration(userManager, scanner);
                    break;
                case 2:
                    handleLogin(userManager, scanner);
                    break;
                case 3:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 3);

        scanner.close();
    }

    private static void handleRegistration(UserManager userManager, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        userManager.registerUser(username, password);
    }

    private static void handleLogin(UserManager userManager, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User loggedInUser = userManager.loginUser(username, password);
        if (loggedInUser != null) {
            System.out.println("User " + username + " logged in successfully.");
            handleTradingStrategy(loggedInUser, scanner);
            displayLoggedInMenu(loggedInUser, scanner);
        }
    }
    private static void handleTradingStrategy(User user, Scanner scanner) {
        System.out.println("Choose a trading strategy:");
        System.out.println("1. DayTrading Strategy");
        System.out.println("2. LongTerm Invesment Strategy");
        System.out.print("Enter your choice: ");

        int strategyChoice = scanner.nextInt();
        switch (strategyChoice) {
            case 1:
                user.setTradingStrategy(new DayTradingStrategy());
                break;
            case 2:
                user.setTradingStrategy(new LongTermInvestingStrategy());
                break;
            default:
                System.out.println("Invalid choice. Using default strategy.");
               
        }
    }

    private static void displayPortfolio(User user) {
        System.out.println("Portfolio for user " + user.getUsername() + ":");
        double totalValue = 0;

        for (Map.Entry<String, Integer> entry : user.getPortfolio().entrySet()) {
            String stockSymbol = entry.getKey();
            int quantity = entry.getValue();

            Stock stock = StockManager.getInstance().getStock(stockSymbol);
            if (stock != null) {
                double stockValue = stock.getPrice() * quantity;
                totalValue += stockValue;

                System.out.println(stockSymbol + ": " + quantity + " shares (Value: $" + stockValue + ")");
            }
        }

        System.out.println("Total Portfolio Value: $" + totalValue);
       
    }
    TransactionHistory transactionHistory = new TransactionHistory();
    
    
    private static void handleStockPurchase(User user, Scanner scanner) {
       
       
            StockManager stockManager = StockManager.getInstance();
            stockManager.displayStocks();

            System.out.print("Enter the stock symbol you want to buy: ");
            String stockSymbol = scanner.next().toUpperCase();

            System.out.print("Enter the quantity you want to buy: ");
            int quantity = scanner.nextInt();
            
            

            Stock stockToBuy = stockManager.getStock(stockSymbol);
            if (stockToBuy != null) {
                user.buyStock(stockToBuy, quantity);
                displayPortfolio(user);
                
                String transaction = "Purchase: " + quantity + " shares of " + stockSymbol +
                        " for a total value of $" + (stockToBuy.getPrice() * quantity);
                user.executeTransaction(transaction);
            } else {
                System.out.println("Invalid stock symbol. Stock purchase failed.");
            }
        } //stockPurchaseContext.executeCommand();
   private static void displayLoggedInMenu(User loggedInUser, Scanner scanner) {
    int choice;
    do {System.out.println("Available Funds: $" + loggedInUser.getAvailableFunds());
    	System.out.println("1. Portfolio");
        System.out.println("2. Buy Stocks");
        System.out.println("3. Sell Stocks");
        System.out.println("4. Transaction History");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayPortfolio(loggedInUser);
                break;
            case 2:
                handleStockPurchase(loggedInUser, scanner);
                break;
            case 3:
                handleStockSale(loggedInUser, scanner);
                break;
            case 4:
                //loggedInUser.getTransactionHistory();
            	System.out.println("Nu am reusit sal implementez nu inteleg de ce nu mere");
            	
                break;
            case 5:
                System.out.println("Logging out. Goodbye, " + loggedInUser.getUsername() + "!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

    } while (choice != 5);
}
   private static void handleStockSale(User user, Scanner scanner) {
	    StockManager stockManager = StockManager.getInstance();

	    System.out.println("Stocks available for sale:");
	    stockManager.displayUserStocks(user);

	    System.out.print("Enter the stock symbol you want to sell: ");
	    String stockSymbol = scanner.next().toUpperCase();
	   

	    Stock stockToSell = stockManager.getStock(stockSymbol);
	    if (stockToSell == null) {
	        System.out.println("Invalid stock symbol. Sale canceled.");
	        return;
	    }

	    System.out.print("Enter the quantity you want to sell: ");
	    while (!scanner.hasNextInt()) {
	        System.out.println("Invalid input. Please enter a number.");
	        scanner.next();
	    }
	    int quantityToSell = scanner.nextInt();

	    int currentStockQuantity = user.getPortfolio().getOrDefault(stockSymbol, 0);
	    if (quantityToSell > currentStockQuantity) {
	        System.out.println("Insufficient shares in your portfolio. Sale canceled.");
	        return;
	    }

	    double saleValue = stockToSell.getPrice() * quantityToSell;

	    
	    user.getPortfolio().put(stockSymbol, currentStockQuantity - quantityToSell);
	    stockToSell.setQuantity(stockToSell.getQuantity() + quantityToSell);

	    System.out.println("Sale completed. You sold " + quantityToSell + " shares of " + stockSymbol +
	            " for a total value of $" + saleValue);
	    String transaction = "Sale: " + quantityToSell + " shares of " + stockSymbol +
	            " for a total value of $" + saleValue;
	    user.getTransactionHistory().addTransaction(transaction);

	    System.out.println("Sale completed. You sold " + quantityToSell + " shares of " + stockSymbol +
	            " for a total value of $" + saleValue);
	    user.setAvailableFunds(user.getAvailableFunds() + saleValue);
	}
}
