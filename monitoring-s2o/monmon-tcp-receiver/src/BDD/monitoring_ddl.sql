CREATE TABLE monmondb.MEASURE_MESSAGE (
	Version  CHAR(2),
	infrastructure CHAR(1),
	channel CHAR(3),
	layer VARCHAR2(5),
	managedId VARCHAR2(10)
	timestamp NUMBER(10),
	value NUMBER(3),
	retval NUMBER(3),
	invoker VARCHAR2(64),
	invokerVersion CHAR(3),
	target VARCHAR2(64),
	targetVersion CHAR(3),
	requestProtocol VARCHAR2(5),
	subchannel CHAR(3)
 );

 
 
 
CREATE TABLE monmondb.SESSIONS_COUNT (
	Version NUMBER(2),
	channel CHAR(3),
	subchannel CHAR(3),
	infrastructure NUMBER(2),
	layer VARCHAR2(5),
	managedId VARCHAR2(32)
	timestamp NUMBER(10),
	value NUMBER(3),
	invoker VARCHAR2(64),
	invokerVersion VARCHAR2(64),
	target VARCHAR2(64),
	targetVersion VARCHAR2(64),
	requestProtocol VARCHAR2(5),
	retval NUMBER(3)
  );        
   
        