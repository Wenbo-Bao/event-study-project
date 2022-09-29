package calculator;

import java.util.ArrayList;

/**
 * this calculator is used to get standerd deviation and mean we could dump lags
 * or return responses in this calculator to get their mean and SD
 * 
 * the input are Arraylists of lags
 * 
 * @author baowenbo
 *
 */

public class SDCalculator2c implements ICalculator<ArrayList<Double>> {

	protected ArrayList<ArrayList<Double>> _values;

	/** Count of values added to this analysis. */
	protected int _count;
	protected double _sumX;
	protected double _sumXX;

	/**
	 * Create a new SDCalculator2c object and initialize the values that will be
	 * used to compute a running standard deviation.
	 */
	public SDCalculator2c() {
		_values = new ArrayList<ArrayList<Double>>();
		_sumX = 0;
		_sumXX = 0;
		_count = 0;
	}

	/**
	 * process data and add everything together
	 */
	public void processData() {
		for (ArrayList<Double> al : this._values) {
			for (Double d : al) {
				_sumX += d;
				_sumXX += d * d;
			}
		}
//		return 0;
	}

	/**
	 * @return Number of data points that are part of the analysis.
	 */
	public int getCount() {
		return _count;
		// TODO LOGIC JspFzY
	}

	@Override
	public void add(ArrayList<Double> value) {
		_values.add(value);
		_count++;
	}

	@Override
	public void remove(ArrayList<Double> value) {
		_values.remove(value);
		_count--;
	}

	// not used because we are returning two values which are the SD and the mean
	@Override
	public double result() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	// return an arraylist of size two the first element is the mean and the second
	// element is the SD
	@Override
	public ArrayList<Double> getlag(int lag) {
		// processData could initizalize the value of sumX and sumXX
		this.processData();
		// this is the array of mean and SD we want to return
		ArrayList<Double> sumSDarr = new ArrayList<Double>();
		// the sample size this the amount of lag arrays added to the calculator and the
		// amount of lags in each array
		int samplesize = (this._count * this._values.get(0).size());
		// this add the mean to the output array
		sumSDarr.add(this._sumX / samplesize);
		// this add the SD to the output array
		sumSDarr.add((this._sumXX / samplesize - (this._sumX / samplesize) * (this._sumX / samplesize))
				/ (samplesize - 1) * samplesize);
		return sumSDarr;
	}

}
