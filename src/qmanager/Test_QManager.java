package qmanager;

import utilities.PriceRecord;

public class Test_QManager {

	public static void main(String[] args) {

		QManager<PriceRecord> qm = QManager.FixedChildQ(780);
		QManager<PriceRecord> qm1 = qm.getFirstChildQ();
		int count = 0;
		while (true) {
			qm1 = qm1.getFirstChildQ();
			count++;
			System.out.println(count);
		}

	}

}
