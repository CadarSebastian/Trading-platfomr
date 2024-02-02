public interface TradingStrategy {
    boolean shouldBuy(Stock stock);
    boolean shouldSell(Stock stock);
}
