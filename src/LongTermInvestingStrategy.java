public class LongTermInvestingStrategy implements TradingStrategy {
    private static final int LONG_TERM_HOLD_THRESHOLD = 30; 

    @Override
    public boolean shouldBuy(Stock stock) {
        return stock.getQuantity() > LONG_TERM_HOLD_THRESHOLD;
    }

    @Override
    public boolean shouldSell(Stock stock) {
        return stock.getQuantity() < -LONG_TERM_HOLD_THRESHOLD;
    }
}
