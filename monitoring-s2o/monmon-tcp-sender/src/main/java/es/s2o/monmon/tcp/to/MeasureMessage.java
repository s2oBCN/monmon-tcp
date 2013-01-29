package es.s2o.monmon.tcp.to;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.MsgType;
import es.s2o.monmon.tcp.identifiers.MsgVersion;

/**
 * Measure message with all the information of the measure
 * 
 * @author s2o
 * 
 */
public class MeasureMessage {

	private MsgVersion mesgVersion;

	private MsgType msgType;

	private Infraestructure infrastructure;

	private String inputChannel = "000";

	private String layer = "";

	private String managedId = "";

	private int timestamp;

	private short value;

	private String invoker = "";

	private String invokerVersion = "000";

	private String target = "";

	private String targetVersion = "000";

	private String requestProtocol = "";

	private String retval = "";

	private String subchannel = "000";

	private String subsubchannel = "000";

	public MsgVersion getMsgVersion() {
		return mesgVersion;
	}

	public void setMsgVersion(final MsgVersion version) {
		mesgVersion = version;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(final MsgType msgType) {
		this.msgType = msgType;
	}

	public Infraestructure getInfrastructure() {
		return infrastructure;
	}

	public void setInfrastructure(final Infraestructure infrastructure) {
		this.infrastructure = infrastructure;
	}

	public String getInputChannel() {
		return inputChannel;
	}

	public void setInputChannel(final String channel) {
		inputChannel = channel;
	}

	public String getSubchannel() {
		return subchannel;
	}

	public void setSubchannel(final String subchannel) {
		this.subchannel = subchannel;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(final String layer) {
		this.layer = layer;
	}

	public String getManagedId() {
		return managedId;
	}

	public void setManagedId(final String managedId) {
		this.managedId = managedId;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final int timestamp) {
		this.timestamp = timestamp;
	}

	public short getValue() {
		return value;
	}

	public void setValue(final short value) {
		this.value = value;
	}

	public String getInvoker() {
		return invoker;
	}

	public void setInvoker(final String invoker) {
		this.invoker = invoker;
	}

	public String getInvokerVersion() {
		return invokerVersion;
	}

	public void setInvokerVersion(final String invokerVersion) {
		this.invokerVersion = invokerVersion;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public String getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(final String targetVersion) {
		this.targetVersion = targetVersion;
	}

	public String getRequestProtocol() {
		return requestProtocol;
	}

	public void setRequestProtocol(final String requestProtocol) {
		this.requestProtocol = requestProtocol;
	}

	public String getRetval() {
		return retval;
	}

	public void setRetval(final String retval) {
		this.retval = retval;
	}

	public String getSubsubchannel() {
		return subsubchannel;
	}

	public void setSubsubchannel(final String subsubchannel) {
		this.subsubchannel = subsubchannel;
	}

	@Override
	public String toString() {
		return mesgVersion.toString() + msgType + infrastructure + inputChannel + subchannel + "subsubchannel:"
				+ subsubchannel + layer + managedId + timestamp + value + invoker + invokerVersion + target
				+ targetVersion + requestProtocol + retval;
	}

}
