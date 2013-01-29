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

	private static final int MAX_BATCH = 100000;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	static List<MeasureMessage> list = new ArrayList<MeasureMessage>();

	@Autowired
	MeasureDAO measureDAO;

	/**
	 * @param measure
	 */
	public void aggregate(final MeasureMessage measure) {
		boolean insertBatch = false;

		// take care about overloading the list
		if (list.size() > MAX_BATCH) {
			insertBatch = true;
		}
		agragateToDAO(insertBatch, measure);
	}

	/**
	 * Executed from timer see "applicationContext-services.xml"
	 */
	public void insertBatch() {
		logger.debug("start insertBatch");

		agragateToDAO(true, null);

		logger.debug("end insertBatch");
	}

	/**
	 * @param measure
	 */
	private void agragateToDAO(final boolean insertBatch, final MeasureMessage measure) {

		synchronized (this) {
			if (insertBatch) {
				// asyncron insert data (list will be copied to another thread)
				measureDAO.insertBatch(list);

				// restart list
				list = new ArrayList<MeasureMessage>();

				// if it comes from MAX_BATCH, lets add to list
				if (measure != null) {
					list.add(measure);
				}
			}
			else {
				list.add(measure);
			}

		}
	}
}
