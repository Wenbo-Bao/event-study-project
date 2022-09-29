package qmanager;

import java.util.ArrayList;
import java.util.Collections;

import utilities.PriceRecord;

/**
 * this is the value in each hashmap pairs
 * 
 * @author baowenbo
 *
 */
public class StockManagers {
	// the manager that manage all the pricerecord output returns
	private QManager<PriceRecord> returnmanager;
	// the manager that input the returns and output lags
	private QManager<Double> lagmanager;
	// the arr of 780 returns of a stock
	private ArrayList<Double> returnarr;

	// constuctor
	public StockManagers() {
		returnmanager = QManager.<PriceRecord>FixedChildQ(780);
		lagmanager = QManager.<Double>FixedChildReturnQ(30);
		returnarr = new ArrayList<Double>();
	}

	// 这个一定是要有的 不然pricerecord进去了之后，要进到return manager里面

	// process the pricerecord and add them into the array of returns
	public void processArr() throws Exception {
		for (int i = 0; i < 780; i++) {
			returnarr.add(returnmanager._calculators.getFirst().result());
			returnmanager = returnmanager.getFirstChildQ();
		}
		
		//this is like a stack so we need to reverse the data to make them the correct time sequence
		Collections.reverse(returnarr);
//		System.out.println(returnarr.size());

		// 这里加上的不对 可能是满的条件有错
		// add all the returns to the lagmanager
		for (Double d : returnarr) {
			lagmanager.addValue(d);
		}
	}
//	
//	public void printcalculatorvalues() {
//		QManager<Double> lg = this.lagmanager;
//		for(int i = 0; i <30; i++) {
//			System.out.println(lg._calculators.getFirst().)
//		}
//	}

	public QManager<PriceRecord> getReturnmanager() {
		return this.returnmanager;
	}

	public QManager<Double> getLagmanager() {
		return this.lagmanager;
	}

	public ArrayList<Double> getReturnArr() {
		return returnarr;

	}
}
