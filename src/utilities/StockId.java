package utilities;

public class StockId {
	private String _stringId;

	public StockId(String string) {
		_stringId = string;
	}

	@Override
	public String toString() {
		return String.format("%s( %s )", this.getClass().getSimpleName(), _stringId);
	}

	@Override
	public int hashCode() {
		return _stringId.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StockId))
			return false;
		if (o == this)
			return true;
		return _stringId.equals(((StockId) o)._stringId);
	}
}
