package es.s2o.monmon.tcp.to;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.InputChannel;
import es.s2o.monmon.tcp.identifiers.Type;
import es.s2o.monmon.tcp.identifiers.Version;

/**
 * Measure message with all the information of the measure
 * 
 * @author s2o
 * 
 */
public class MeasureMessage {

	private Version version;

	private Type msgType;

	private Infraestructure infrastructure;

	private InputChannel channel;

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

	public Version getVersion() {
		return version;
	}

	public void setVersion(final Version version) {
		this.version = version;
	}

	public Type getMsgType() {
		return msgType;
	}

	public void setMsgType(final Type msgType) {
		this.msgType = msgType;
	}

	public Infraestructure getInfrastructure() {
		return infrastructure;
	}

	public void setInfrastructure(final Infraestructure infrastructure) {
		this.infrastructure = infrastructure;
	}

	public InputChannel getChannel() {
		return channel;
	}

	public void setChannel(final InputChannel channel) {
		this.channel = channel;
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

	@Override
	public String toString() {
		return version.toString() + msgType + infrastructure + channel + subchannel + layer + managedId + timestamp
				+ value + invoker + invokerVersion + target + targetVersion + requestProtocol + retval;
	}
}
