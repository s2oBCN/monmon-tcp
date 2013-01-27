package es.s2o.monmon.tcp.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.s2o.monmon.repository.dao.MeasureDAO;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * Agregate message and insert every x-seconds
 * @author s2o
 * 
 */
public class MeasureAggregatorService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	static List<MeasureMessage> list = new ArrayList<MeasureMessage>();

	@Autowired
	MeasureDAO measureDAO;

	/**
	 * @param measure
	 */
	public void aggregate(final MeasureMessage measure) {

		synchronized (this) {
			list.add(measure);
			if (list.size() >= 10000) {
				// insert data with a new thread
				measureDAO.insertBatch(list);

				// restart list
				list = new ArrayList<MeasureMessage>();
			}
		}

	}

}
