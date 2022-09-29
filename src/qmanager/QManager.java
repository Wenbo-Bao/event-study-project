package qmanager;

import java.util.ArrayList;
import java.util.LinkedList;

import calculator.ICalculator;
import calculator.LagCalculator;
import calculator.ReturnCalculator;
import calculator.SDCalculator2b;
import calculator.SDCalculator2c;
import utilities.PriceRecord;

/**
 * Class to manage gueued data and feed it to calculators. It's abstract because
 * the two methods, isFull and hasElementsToRemove have no obvious defaults. For
 * example, some queues are time queues and some are size queues, with those two
 * methods implemented differentl for each.
 * 
 * @author lee
 *
 * @param <T> Data type of queued data managed by an object of this class, e.g.
 *            values of type Double if we're trying to compute a moving average.
 */
public abstract class QManager<T> {

	/**
	 * A linked list of calculators that will process the data coming into and going
	 * out of the queue.
	 */
	protected LinkedList<ICalculator<T>> _calculators;

	/**
	 * A linked list of child queue managers that receive the values being removed
	 * from this queue manager.
	 */
	protected LinkedList<QManager<T>> _childQueues;

	/**
	 * A queue of values that is added to the queue manager by its clients.
	 */
	protected LinkedList<T> _values;

	/**
	 * Construct a QManager and initialize empty lists of calculators, child queues,
	 * and value.
	 */
	public QManager() {
		_calculators = new LinkedList<ICalculator<T>>();
		_childQueues = new LinkedList<QManager<T>>();
		_values = new LinkedList<T>();
		// TODO LOGIC Oi1i7Y
	}

	/**
	 * Add an ICalculator object to this object's list of calculators. Each
	 * calculator will be fed the queue's incoming and outgoing values.
	 * 
	 * @param calculator The ICalculator object to be added to this objects list of
	 *                   calculators.
	 * @return This object for chained additions.
	 */
	public QManager<T> addCalculator(ICalculator<T> calculator) {
		_calculators.addLast(calculator);
		return this;
		// TODO LOGIC cYhgUW
	}

	public QManager<T> addChildQ(QManager<T> childQ) {
		_childQueues.add(childQ);
		return this;
	}

	/** Get size of internal value queue. */
	public int getQSize() {
		return _values.size();
		// TODO LOGIC WqrTXt
	}

	/**
	 * Add value to queue, all calculators. If necessary remove oldest values from
	 * queue and add to child queues.
	 * 
	 * @param value Value to add to queue.
	 */
	public void addValue(T value) {

		// Value must not be a null. We want to catch this
		// error here rather than in one of the calculators.
		if (value == null)
			throw new IllegalArgumentException("Can't pass a null value to addValue method of QManager");
		// TODO LOGIC cuUWsg

		// First, add value to all of the calculators.
		for (ICalculator<T> calculator : _calculators)
			calculator.add(value);
		// TODO LOGIC AuqVJS

		// Now, add value to the internal value queue.
		_values.addLast(value);

		// While there are values to remove, remove a value
		// and tell each calculator to remove it from its
		// analysis. Then tell each child queue manager to
		// add the value to its queue of values and to its
		// internal calculators.
		while (this.hasElementsToRemove()) {
			T oldestValue = _values.removeFirst();
			for (ICalculator<T> calculator : _calculators)
				calculator.remove(oldestValue);
			// TODO LOGIC liYxAx
			for (QManager<T> childQ : _childQueues)
				childQ.addValue(oldestValue);
			// TODO !!!
		}
	}

	/** Returns true if the queue has too many values. */
	public abstract boolean hasElementsToRemove();
	// TODO LOGIC 5nJo2W

	/**
	 * Returns true if the queue has enough values for a calculation.
	 * 
	 * @return True if there are enough values for a calculation otherwise false.
	 */
	public abstract boolean isFull();
	// TODO LOGIC dxjYiF

	/**
	 * Static factory method that allows us to make one of the most common forms of
	 * a QManager, a fixed length queue. Of course, we can always do this directly
	 * via the constructor - which is what this method calls, too - but packaging it
	 * in a static factory method makes it more readable and convenient.
	 * 
	 * @param <T>     The type of the values that will be added to our fixed length
	 *                queues.
	 * @param maxSize The maximum fixed size of the queue before it starts to drop
	 *                the oldest value.
	 * @return A QManager object with fixed length.
	 */
	public static <T> QManager<T> FixedSizeQ(int maxSize) {

		if (maxSize < 1)
			throw new IllegalArgumentException("Can't specify a max queue size less than 1");
		// TODO LOGIC DTjazu

		QManager<T> qm = new QManager<T>() {

			@Override
			public boolean hasElementsToRemove() {
				return this.getQSize() > maxSize;
			}

			@Override
			public boolean isFull() {
				return this.getQSize() == maxSize;
			}

		};

		return qm;
		// TODO LOGIC sJNUCm

	}

	/**
	 * this is the static factory method that create the FixedPeriodQ that could
	 * generate a fixed period q, only data from the same period could be in this
	 * qmanager because as soon as a pricerecord from another period enter the
	 * haselementtoremove method would be able to detect it and put all the
	 * pricerecord from the previous period to its child queue
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> QManager<T> FixedPeriodQ() {

//		if( maxSize < 1 )
//			throw new IllegalArgumentException(
//				"Can't specify a max queue size less than 1"
//			);
//			// TODO LOGIC DTjazu

		QManager<T> qm = new QManager<T>() {
			@Override
			public boolean hasElementsToRemove() {
				// if there are only zero or one pricerecord, they could not be from different
				// periods
				if (this._values.size() < 2) {
					return false;
				}
				// return whether they are from the same period and equals is writen in the
				// pricerecord object
				return !(this._values.getFirst().equals(this._values.getLast()));
			}

			@Override
			public boolean isFull() {
				return this.getQSize() > 1;
			}
		};

		return qm;
	}

	// this q manager is used in the first round of the process that we put all the
	// data from a single stock in the qmanager

	// this generate a sequence of fixedperiodqs and also have the calcultor add to
	// them and the calculator inside them are returncalcultors and the q they are
	// managering are pricerecords
	// 这个是第一层加上pricerecord的q，里面的计算器会算出return，每个stockmanager里面都有一个
	// 每个的size都是一个q里面有n条数据，一共有780个q
	// 每条数据是一个pricerecord
	public static QManager<PriceRecord> FixedChildQ(int childNumber) {
		QManager<PriceRecord> qm = QManager.<PriceRecord>FixedPeriodQ();
		qm.FixedChildQrec(qm, childNumber);
		return qm;
		// TODO LOGIC sJNUCm
	}

	// this is the core of the fixedchildperiodq writen with recursion to add every
	// child queue in a sequence
	private void FixedChildQrec(QManager<PriceRecord> qm, int childNumber) {
		if (childNumber == 0) {
			return;
		}
		// add the calculator we need to the childqueue
		qm.addCalculator(new ReturnCalculator());

		// create new childq add to it
		qm.addChildQ(QManager.<PriceRecord>FixedPeriodQ());

		// recursion step
		FixedChildQrec(qm._childQueues.getFirst(), childNumber - 1);
		// TODO LOGIC sJNUCm
	}

	// this generate a sequence of fixed sized queues and it is used in the process
	// when adding every return of one stock to it's lagmanager
	// each qmanager manages 26 return doubles and there are 30 qmanagers like this
	// connected together because there are 30 events for each stock in the 60days
	// period
	// 这个是第二层加上return的q，里面的计算器会算出来lag，lag的output是arraylist，每个stockmanager里面都有一个
	// 每个的size都是一个q里面有26条数据，一共有30个q
	// 每条数据是一个return
	public static QManager<Double> FixedChildReturnQ(int childNumber) {
		QManager<Double> qm = QManager.<Double>FixedSizeQ(26);
		qm.FixedChildQReturnrec(qm, childNumber);
		return qm;
		// TODO LOGIC sJNUCm
	}

	// this is the core for the above qmanager use recursion to connect them
	// together
	private void FixedChildQReturnrec(QManager<Double> qm, int childNumber) {
		if (childNumber == 0) {
			return;
		}
		qm.addCalculator(new LagCalculator());
		qm.addChildQ(QManager.<Double>FixedSizeQ(26));

//		System.out.println("hello world");
		FixedChildQReturnrec(qm._childQueues.getFirst(), childNumber - 1);
		// TODO LOGIC sJNUCm
	}

	// ！！！！可以把return sm里面第二个manager那个改一下，但是要动constructor，要把calculator加上去

	// this is the last qmanager and there are only one of these managers in the
	// baomanager
	// it manage arraylists of lags and in each childqueues they could store 30
	// arraylists of lags because there are 30 arraylist of each lag since there are
	// 30 events

	// and it's 25 of these qmanagers connected together becuase there are 25
	// different kinds of lags

	// 这个是最后一层，把所有算出来的lag都放在这个里面，里面的计算器会算出来mean和sd，每个计算器的output是一个[mean,
	// output]的一个数组，baosmanager里面只有一个这个manager
	// 一共有25个qmanager连在一起，因为有lag1-25
	// 每个qmanager代表一种lag
	// 长度是因总共的stock数量而变化的，每个stock有30个lagn的list，所以长度就应该是stock数量*30，这样iterate over n
	// stocks，就满了
	public static QManager<ArrayList<Double>> FixedChildlagQ(int childNumber, int qmhsize) {
		QManager<ArrayList<Double>> qm = QManager.<ArrayList<Double>>FixedSizeQ(qmhsize);
		qm.FixedChildQlagrec(qm, childNumber, qmhsize);
		return qm;
		// TODO LOGIC sJNUCm
	}

	private void FixedChildQlagrec(QManager<ArrayList<Double>> qm, int childNumber, int qmhsize) {
		if (childNumber == 0) {
			return;
		}
		qm.addCalculator(new SDCalculator2c());
		qm.addChildQ(QManager.<ArrayList<Double>>FixedSizeQ(qmhsize));

//		System.out.println("hello world");
		FixedChildQlagrec(qm._childQueues.getFirst(), childNumber - 1, qmhsize);
		// TODO LOGIC sJNUCm
	}

	public LinkedList<QManager<T>> get_childQueues() {
		return this._childQueues;
	}

	public T get(int idx) {
		return _values.get(idx);
		// TODO !!!
	}

	public QManager<T> getFirstChildQ() {
		return this._childQueues.getFirst();
	}

}
