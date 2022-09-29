
package utilities;
/**
 * this is the pricerecord object
 * the equals is special designed return true when they are in the same half hour period
 * @author baowenbo
 *
 */
public class PriceRecord {

	int secondsFromEpocToMidnight;
	int millisFromMidnight;
	StockId stockId;
	long priceTimes10K;

	public int getPeriod() {
		return (this.millisFromMidnight - 60 * 60 * 9500) / (30 * 60 * 1000);
	}

	public int getEvent() {
		return this.secondsFromEpocToMidnight / (2 * 24 * 60 * 60);
	}

	public PriceRecord(int secondsFromEpocToMidnight, int millisFromMidnight, String stockId, long priceTimes10K) {
		this.secondsFromEpocToMidnight = secondsFromEpocToMidnight;
		this.millisFromMidnight = millisFromMidnight;
		this.stockId = new StockId(stockId);
		this.priceTimes10K = priceTimes10K;
	}

	@Override
	public boolean equals(Object obj) {
		PriceRecord p2 = (PriceRecord) obj;
		return (this.getPeriod() == p2.getPeriod());
	}

	public long getPrice() {
		return priceTimes10K;
	}

	public double getReturn(PriceRecord p2) {
		return (double) this.getPrice() / p2.getPrice() - 1;
	}

	public StockId getStockId() {
		return this.stockId;
	}

	public String toString() {
		String str = String.valueOf(priceTimes10K);
		return null;
	}

}