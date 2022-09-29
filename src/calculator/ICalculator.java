package calculator;

import java.util.ArrayList;

/**
 * this is the interface for all calculators
 * 
 * @author baowenbo
 *
 * @param <T>
 */
public interface ICalculator<T> {

	// add an element to the calculator
	public void add(T value);

	// remove an element from the calculator
	public void remove(T value);

	// get the result from the calculator
	public double result() throws Exception;

	// i added the getlag method by myself because i have two kinds output for
	// calculators when I am calculating the lags, I need the output to be ArrayList
	public ArrayList<Double> getlag(int lag);
}
