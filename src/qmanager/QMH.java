package qmanager;

/**
 * this is the major manager, it's like the exchange class in the exchange assignment
 * 
 * it is consist of two conponents which is a _stockmap which is consist of <stockid, stockmanagers> key-value pairs
 * 
 * another component is the _lagmanager which we dump the lags from every stockmanager in it to calculate all the means and SDs
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import utilities.PriceRecord;
import utilities.StockId;

public class QMH {
	// change to private later after testing
	private HashMap<StockId, StockManagers> _stockMap;

	// this is like lee's exchange
	public static final QMH BaoManager = new QMH();

	// initilize BaoManager
	private QMH() {
		_stockMap = new HashMap<StockId, StockManagers>();
	}

	// retrive the stockmanager from the hashmap with stockid
	// if the stockmanager does not exist we would put a new element in the table
	// this method is mainly used in add new pricerecord
	private QManager<PriceRecord> getSQForStockId(StockId stockId) {
		StockManagers sm = this._stockMap.get(stockId);
		if (sm == null) {
//			System.out.println("A new Stockmanager has been created");
			sm = new StockManagers();
			_stockMap.put(stockId, sm);
		}
		;
		return sm.getReturnmanager();
	}

	// put the new pricerecord in the stockmap
	public void processRecord(PriceRecord pricerecord) {
		this.getSQForStockId(pricerecord.getStockId()).addValue(pricerecord);
	}

//	 output of 780 returns from 780 periods of the stock we want
	public void getReturnforStockId(StockId stockId) throws Exception {
		StockManagers sm = this._stockMap.get(stockId);
		// if there is no such stock, print err
		if (sm == null) {
			System.err.print("No such stock exist!!!");
		}
//		sm.processArr();
		// print returns for the users to see
		ArrayList<Double> arr = sm.getReturnArr();
		for (int i = 0; i < 780; i++) {
			System.out.println(i + ": " + arr.get(i));
		}
	}

	// let all the stockmanagers in the map to process its datas
	public void process() throws Exception {
		for (Map.Entry mapElement : _stockMap.entrySet()) {
			StockManagers sm = (StockManagers) mapElement.getValue();
			sm.processArr();
		}
	}

	/**
	 * this is the manager for lags and we would dump all the lags into this manager
	 * and the output of the calculators in this managers are mean and SD of each kind of lags
	 */
	/*
	 * 这个是一个新的manager，25个连在一起，早initialize和晚initialize都可以，这里先写成晚initialize的，
	 * 就是所有事情都process好了，再建立和跟stock数量有关的_lagmanager
	 *
	 * 每个stock的lag都以arraylist的形式存在，每个stock有30个lag1的arraylist，30个lag2的arraylist，
	 * 这样可以把所有的东西都倒进去，但是得挨个倒进去需要iterate很多遍，但是code应该不是很难写
	 * 
	 */
	public QManager<ArrayList<Double>> _lagmanager;

	public void processLag() {
		this._lagmanager = QManager.<ArrayList<Double>>FixedChildlagQ(25, this._stockMap.size() * 30);
		// iterate over 25 kinds of lags
		for (int i = 1; i < 26; i++) {
			// iterate over all stocks
			for (Map.Entry mapElement : _stockMap.entrySet()) {
				StockManagers sm = (StockManagers) mapElement.getValue();
				QManager<Double> qm = sm.getLagmanager();
				// iterate over 30 events in each stock and add them to the lagmanager
				for (int j = 0; j < 30; j++) {
					this._lagmanager.addValue(qm._calculators.getFirst().getlag(i));
					qm = qm.getFirstChildQ();
				}
			}
		}
		
		//get the lag results
		QManager<ArrayList<Double>> lg = this._lagmanager;
		for (int i = 0; i < 25; i++) {
			ArrayList<Double> ms = lg._calculators.getFirst().getlag(0);
			System.out.println("Lag " + (25 - i) + " mean: " + ms.get(0) + " SD: " + ms.get(1));
			lg = lg.getFirstChildQ();
		}
	}

}
