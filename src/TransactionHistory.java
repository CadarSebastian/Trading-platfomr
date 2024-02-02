import java.util.ArrayList;
import java.util.List;

public class TransactionHistory implements Observer {
    private List<String> transactions = new ArrayList<>();

    @Override
    public void update(List<String> transactions) {
        this.transactions.addAll(transactions);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }

    
    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    
    public List<String> getTransactions() {
        return transactions;
    }
}
