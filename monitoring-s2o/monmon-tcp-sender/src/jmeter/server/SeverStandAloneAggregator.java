package es.s2o.monmon.tcp.server;

import java.util.ArrayList;
import java.util.List;

import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * @author s2o
 * 
 */
public class SeverStandAloneAggregator {

	private static List<MeasureMessage> list = new ArrayList<MeasureMessage>();

	private static SeverStandAloneAggregator aggregator = new SeverStandAloneAggregator();

	public static SeverStandAloneAggregator getInstance() {
		return aggregator;

	}

	/**
	 * @param measure
	 */
	public void aggregate(final MeasureMessage measure) {
		// list.add(measure);
	}

	public int listSize() {
		return list.size();
	}

	public void resetList() {
		list = new ArrayList<MeasureMessage>();
	}

}
