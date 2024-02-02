public class DayTradingStrategy implements TradingStrategy {
    private static final int DAY_TRADE_THRESHOLD = 5; 

    @Override
    public boolean shouldBuy(Stock stock) {
        return stock.getPrice() > DAY_TRADE_THRESHOLD;
    }

    @Override
    public boolean shouldSell(Stock stock) {
        return stock.getPrice() < -DAY_TRADE_THRESHOLD;
    }
}
