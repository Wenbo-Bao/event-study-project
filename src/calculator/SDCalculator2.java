package calculator;

/**
 * 
 * this calculator is not used in the implementation
 * 
 * First attempt at online implementation of standard deviation calculator. Does
 * not manage its own queue.
 * 
 * @author lee
 *
 */
public class SDCalculator2 {

	/** Running sum of values added to this analysis. */
	protected double _sumX;

	/** Running sum of squared values added to this analysis. */
	protected double _sumXX;

	/** Count of values added to this analysis. */
	protected int _count;

	/**
	 * Create a new SDCalculator2a object and initialize the values that will be
	 * used to compute a running standard deviation.
	 */
	public SDCalculator2() {
		// To perform an online calculation of standard deviation,
		// we need three things: the count, the sum of the values,
		// and the sum of the squared values.
		_sumX = 0;
		_sumXX = 0;
		_count = 0;
		// TODO LOGIC pGJMFM
	}

	/**
	 * Add value to running sum of values and running sum of squared values. Update
	 * count.
	 * 
	 * @param value Value to add to analysis.
	 */
	public void add(double value) {
		_sumXX += value * value;
		_sumX += value;
		_count++;
		// TODO LOGIC xQMHZj
	}

	/**
	 * Remove value from running sum of values and running sum of squared values. If
	 * the count of values in the analysis is zero, reset the sums to zero.
	 * 
	 * @param value Value to remove from analysis
	 * @throws Exception Thrown if the count is zero, i.e. there is no data
	 *                   remaining in the analysis to remove.
	 */
	public void remove(double value) {
		if (_count < 1)
			throw new IllegalStateException("Can't remove elements from an empty queue");
		// TODO LOGIC HdhQtT
		_count--;
		// Has the count gone to zero?
		if (_count == 0) {
			// Yes, the count has gone to zero. We will now
			// assume that whatever is left in the accumulators
			// is round off error and set it to zero as well.
			_sumX = 0.0D;
			_sumXX = 0.0D;
			// TODO LOGIC 6vy7fb
		} else {
			// No, the count has not gone to zero, so subtract
			// the value being removed from the sum of all values
			// and the squared sum of all values.
			_sumX -= value; // Round off
			_sumXX -= value * value; // Round off
			// TODO LOGIC 1h72e6
		}
	}

	/**
	 * Compute the sum of previously added values.
	 * 
	 * @return Sum of previously added values.
	 * @throws Exception Thrown if sum can't be computed because there are no data
	 *                   points in the analysis.
	 * 
	 */
	public double getSum() {
		if (_count < 1)
			throw new IllegalStateException("Need at least one value to compute a sum");
		// TODO LOGIC Htgzqy
		return _sumX;
		// TODO LOGIC AOwUto
	}

	/**
	 * Compute mean of values previously added to analysis.
	 * 
	 * @return Mean of values previously added to analysis.
	 * @throws Exception Thrown if there are no values in the analysis.
	 */
	public double getMean() {
		if (_count < 1)
			throw new IllegalStateException("Need at least one value to compute a mean");
		// TODO LOGIC 3030aQ
		return _sumX / _count;
		// TODO LOGIC JzQeEO
	}

	/**
	 * Compute sample standard deviation.
	 * 
	 * @return Sample standard deviation.
	 * @throws Exception Thrown if there are less than two data points in the
	 *                   analysis.
	 */
	public double getSampleSD() {
		// Do we have enough data to perform a sample standard
		// deviation calculation?
		if (_count < 2)
			// No, we do not have enough data to perform a sample
			// standard deviation calculation.
			throw new IllegalStateException("Need at least two values to compute sample standard deviation");
		// TODO LOGIC 0pFqGb
		// Yes, we have enough data to perform a sample standard
		// deviation calculation. Do it and return the result.
		double diffOfSums = (_sumXX / _count) - ((_sumX / _count) * (_sumX / _count));
		return Math.sqrt(diffOfSums * ((double) _count / (_count - 1)));
		// TODO LOGIC chF4m0
	}

	/**
	 * @return Number of data points that are part of the analysis.
	 */
	public int getCount() {
		return _count;
		// TODO LOGIC JspFzY
	}

}
