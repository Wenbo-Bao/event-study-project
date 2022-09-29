package utilities;

import java.util.LinkedList;

public class Return {
	LinkedList<PriceRecord> _values;
	int eventnumber;

	public Return() {
		this._values = new LinkedList<PriceRecord>();
		this.eventnumber = 0;
	}

	public double calculateReturn() throws Exception {
		if (_values.size() < 2) {
			throw new Exception("not enough data");
		}
		return _values.getLast().getReturn(_values.getFirst());
	}

	public void add(PriceRecord pricerecord) {
		_values.add(pricerecord);
		eventnumber = pricerecord.getEvent();
	}

	public void remove(PriceRecord pricerecord) {
		_values.remove(pricerecord);
	}

}