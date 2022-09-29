package calculator;

import java.util.ArrayList;

/**
 * this is the implementation of ICalculator and <T> is Double and the values
 * put into this calculator are returns and we could calculate all the lags from
 * this calculator
 * 
 * @author baowenbo
 *
 */

public class LagCalculator implements ICalculator<Double> {

	// this is the data set that stores all the values
	ArrayList<Double> _values;
	
	// construct a new calculator
	public LagCalculator() {
		this._values = new ArrayList<Double>();
	}
	
	//add the value to the _value data set
	@Override
	public void add(Double value) {
		// TODO Auto-generated method stub
		this._values.add(value);
	}
	
	//remove the value from the _value data set
	@Override
	public void remove(Double value) {
		// TODO Auto-generated method stub
		this._values.remove(value);

	}
	
	// since of the output of a lag from each event is a arraylist
	// so i am not using this method to get the output
	@Override
	public double result() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// the input is the lag we want
	// the output is an arraylist of lags calculated for example the the lag1 array is consist of 25 lags
	@Override
	public ArrayList<Double> getlag(int lag) {
		if (_values.size() < 26) {
			System.err.println("Not enough data to calculate all othe lag");
		}
		if (lag > 25) {
			System.err.println("No such lag");
		}
		ArrayList<Double> laglist = new ArrayList<Double>();
		int i = 0;
		try {
			while (true) {
				laglist.add(_values.get(i + lag) / _values.get(i));
				i++;
			}
		} catch (Exception e) {
		}
		return laglist;
	}

}
