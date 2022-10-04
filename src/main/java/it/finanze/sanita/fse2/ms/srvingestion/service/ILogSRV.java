package it.finanze.sanita.fse2.ms.srvingestion.service;

/** 
 * Interface for Structured Logging 
 *
 */
public interface ILogSRV {

	/** 
	 * Publishes a log on Kafka 
	 * 
	 * @param log  The log to be published on Kafka 
	 */
	void sendLoggerStatus(String log);
}
