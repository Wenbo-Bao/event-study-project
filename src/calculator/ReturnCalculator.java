package calculator;

import java.util.ArrayList;
/**
 * this is the calculator that calculate return from each period
 */

import utilities.PriceRecord;
import utilities.Return;

public class ReturnCalculator implements ICalculator<PriceRecord> {

	// this store the pricerecords needed to calculate the return of one period
	Return data;
	
	//constuctor of the return calculator
	public ReturnCalculator() {
		this.data = new Return();
	}
	
	// the return could actually be calculated in the return object
	@Override
	public double result() throws Exception {
		return data.calculateReturn();
	}
	
	// add one value to the data set 
	@Override
	public void add(PriceRecord value) {
		data.add(value);
	}
	
	
	@Override
	public void remove(PriceRecord value) {
		data.remove(value);
	}
	
	// in this case the output is double so I don't need to use this method
	@Override
	public ArrayList<Double> getlag(int lag) {
		// TODO Auto-generated method stub
		return null;
	}

}
