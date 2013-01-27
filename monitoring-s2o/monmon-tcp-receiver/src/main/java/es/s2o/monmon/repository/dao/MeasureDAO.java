package es.s2o.monmon.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * Communication with BDD
 * 
 * @author s2o
 * 
 */
public class MeasureDAO {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataSource dataSource;

	/**
	 * Insert in mode bath and prepareStatement
	 * 
	 * @param list
	 */
	@Async
	public void insertBatch(final List<MeasureMessage> list) {
		logger.debug("Executing SQL batch update of " + list.size() + " statements");

		final long startTime = System.currentTimeMillis();
		PreparedStatement pstmt = null;
		try {
			final Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement("INSERT INTO MEASURE_MESSAGE VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			for (final MeasureMessage measure : list) {

				pstmt.setString(1, measure.getVersion().toString());
				pstmt.setString(2, measure.getInfrastructure().toString());
				pstmt.setString(3, measure.getChannel().getValue());
				pstmt.setString(4, measure.getLayer());
				pstmt.setString(5, measure.getManagedId());
				pstmt.setInt(6, measure.getTimestamp());
				pstmt.setShort(7, measure.getValue());
				pstmt.setString(8, measure.getInvoker());
				pstmt.setString(9, measure.getInvokerVersion());
				pstmt.setString(10, measure.getTarget());
				pstmt.setString(11, measure.getTargetVersion());
				pstmt.setString(12, measure.getRequestProtocol());
				pstmt.setString(13, measure.getRetval());
				pstmt.setString(14, measure.getSubchannel());

				pstmt.addBatch();
			}

			final int[] executedOK = pstmt.executeBatch();
			if (executedOK.length != list.size()) {
				logger.error("Total number inserts no OK:" + (list.size() - executedOK.length));
			}
			connection.commit();
			connection.close();
		}
		catch (final SQLException e) {
			// TODO revisar
			System.out.println(e);
			logger.error("ERROR DE SQL POR CONTROLAR", e);
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			}
			catch (final SQLException e) {
				// TODO revisar
				logger.error("ERROR DE SQL POR CONTROLAR", e);
			}
		}
		final long endTime = System.currentTimeMillis();
		final long totalTime = endTime - startTime;
		logger.debug("ExecuteBatch took: " + totalTime + " startTime:" + startTime + " endTime:" + endTime);
	}
}
