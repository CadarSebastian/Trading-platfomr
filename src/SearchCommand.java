import java.util.Scanner;

class SearchCommand implements Command {
    private StockManager stockManager;
    private Scanner scanner;

    public SearchCommand(StockManager stockManager, Scanner scanner) {
        this.stockManager = stockManager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter the stock symbol or name: ");
        String input = scanner.next().toUpperCase();

        Stock stock = stockManager.searchStock(input);
        if (stock != null) {
            System.out.println("Stock found: " + stock);
        } else {
            System.out.println("Stock not found.");
        }
    }
}
