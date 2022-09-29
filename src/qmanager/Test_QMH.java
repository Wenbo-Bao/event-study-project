package qmanager;

import java.util.ArrayList;

import utilities.PriceRecord;
import utilities.StockId;

public class Test_QMH {
	public static void main(String[] args) throws Exception {

		QMH qmh = QMH.BaoManager;

		StockId ibm = new StockId("ibm");

//		PriceRecord p2 = new PriceRecord(0, 1000 * 60 * 10 + 9500 * 60 * 60 + 0 * 15 * 60 * 1000, "ibm", 0);
//		qmh.processRecord(p2);
		
		//add fake data into the manager
		for (int i = 0; i < 1560; i++) {
			int time = 1000 * 60 * 10 + 9500 * 60 * 60 + i * 15 * 60 * 1000;
			PriceRecord p1 = new PriceRecord(0, time, "ibm", i % 8 + 1);
			qmh.processRecord(p1);
		}

		for (int i = 0; i < 1560; i++) {
			int time = 1000 * 60 * 10 + 9500 * 60 * 60 + i * 15 * 60 * 1000;
			PriceRecord p1 = new PriceRecord(0, time, "imb", i % 8 + 1);
			qmh.processRecord(p1);
		}

		qmh.process();
		System.out.println("--------------------------------------");
		System.out.println("Data from the manager");
		qmh.processLag();

		System.out.println("--------------------------------------");
		System.out.println("Data from the direct calculating");
		int[] arr = new int[1560];
		for (int i = 0; i < 1560; i++) {
			arr[i] = i % 8 + 1;
		}
		double[] arr1 = new double[780];
//		System.out.println(arr[0] + " " + arr[1559]);
		for (int i = 0; i < 780; i++) {
			arr1[i] = (double) arr[2 * i + 1] / (double) arr[2 * i] - 1;
//			System.out.println(i+ ": "+ arr1[i]);
		}

		double[][] double_table = new double[30][26];
		for (int j = 0; j < 30; j++) {
			for (int i = 0; i < 26; i++) {
				double_table[j][i] = arr1[j * 4 + i];
			}
		}
		// j和lag的数量加起来是26吧
		ArrayList<Double> lagarr = new ArrayList<Double>();
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 13; j++) {
				lagarr.add(double_table[i][j + 13] / double_table[i][j]);
			}
		}
		double sumX = 0;
		double sumXX = 0;
		int count = 0;

		for (Double d : lagarr) {
			sumX += d;
			sumXX += d * d;
			count++;
		}
		double mean = sumX / count;
		double sd = sumXX / count - mean * mean;
		System.out.println("lag 13 the mean is " + mean + " and the SD is " + sd);

//		qmh.getReturnforStockId(ibm);
	}
}
