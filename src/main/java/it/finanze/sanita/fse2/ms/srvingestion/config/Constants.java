package it.finanze.sanita.fse2.ms.srvingestion.config;

/**
 * 
 * @author vincenzoingenito
 *
 *         Constants application.
 */
public final class Constants {

	/**
	 * Path scan.
	 */
	public static final class ComponentScan {

		/**
		 * Base path.
		 */
		public static final String BASE = "it.sanita.srvingestion";

		/**
		 * Controller path.
		 */
		public static final String CONTROLLER = "it.sanita.srvingestion.controller";

		/**
		 * Service path.
		 */
		public static final String SERVICE = "it.sanita.srvingestion.service";

		/**
		 * Configuration path.
		 */
		public static final String CONFIG = "it.sanita.srvingestion.config";

		/**
		 * Configuration mongo path.
		 */
		public static final String CONFIG_MONGO = "it.sanita.srvingestion.config.mongo";

		/**
		 * Configuration mongo repository path.
		 */
		public static final String REPOSITORY_MONGO = "it.sanita.srvingestion.repository";

		public static final String COLLECTION_NAME = "ingestion-staging";

		private ComponentScan() {
			// This method is intentionally left blank.
		}
	}

	public static final class Headers {

		/**
		 * JWT header field.
		 */
		public static final String JWT_HEADER = "Authorization";

		private Headers() {
			// This method is intentionally left blank.
		}

	}

	public static final class Profile {

		public static final String DEV = "DEV";

		public static final String TEST = "test";

		public static final String TEST_PREFIX = "test";

		/**
		 * Constructor.
		 */
		private Profile() {
			// This method is intentionally left blank.
		}

	}

	public static final class App {

		public static final String JWT_TOKEN_AUDIENCE = "eds-ingestion";

		public static final String JWT_TOKEN_TYPE = "JWT";

		public static final String BEARER_PREFIX = "Bearer ";

		public static final String DELETED = "deleted";

		public static final String SHA_256 = "SHA-256";

		public static final String IDENTIFIER = "identifier";

		private App() {
			// This method is intentionally left blank.
		}

		public static final class Collections {
			public static final String INGESTION_STAGING = "ingestion-staging";
			
			private Collections() {
			}
		}
	}

	public static final class Logs {

		public static final String CALLED_API_POST_DOCUMENT = "Called POST /document";

		public static final String CALLED_API_PUT_DOCUMENT = "Called PUT /document";

		public static final String CALLED_API_UPDATE_DOCUMENT = "Called PUT /document/metadata";

		public static final String CALLED_API_DELETE_DOCUMENT = "Called DELETE /document";

		public static final String CALLED_API_GET_DOCUMENT_BY_IDENTIFIER = "Called GET /document/:id";

		public static final String CALLED_API_GET_DOCUMENTS = "Called GET /document";

		public static final String KAFKA_SENDING_MESSAGE = "Kafka: sending message to Data Processor";

		public static final String KAFKA_SEND_SUCCESS = "Send success.";

		public static final String KAFKA_SEND_FAILED = "Error sending kafka message";

		public static final String KAFKA_SENDING_FAILED = "Kafka Error: sending message to Data Processor";

		public static final String ERROR_RETRIEVING_HOST_INFO = "Error while retrieving host informations";

		public static final String ERROR_MONGO_INSERT = "MongoDB: Error while inserting document";

		public static final String ERROR_CONNECTION_REFUSED = "Error: connection refused by Data Processor Host";

		public static final String ERROR_DOCUMENT_NOT_FOUND = "Error: document not found on staging database";

		public static final String ERROR_EMPTY_DOCUMENT = "Error: empty document in bundle";

		public static final String ERROR_HANDLER_GENERIC_EXCEPTION = "HANDLER handleGenericException()";

		public static final String ERROR_HANDLER_OPERATION_EXCEPTION = "HANDLER handleOperationException()";

		public static final String ERROR_HANDLER_UNSUPPORTED_OPERATION_EXCEPTION = "HANDLER handleUnsupportedOperationException()";

		public static final String ERROR_HANDLER_CONNECTION_REFUSED_EXCEPTION = "HANDLER handleConnectionRefusedException()";

		public static final String ERROR_HANDLER_DOCUMENT_NOT_FOUND_EXCEPTION = "HANDLER handleDocumentNotFoundException()";

		public static final String ERROR_HANDLER_DOCUMENT_ALREADY_EXISTS_EXCEPTION = "HANDLER handleDocumentAlreadyExistsException()";

		public static final String ERROR_HANDLER_EMPTY_DOCUMENT_EXCEPTION = "HANDLER handleEmptyDocumentException()";

		private Logs() {
			// This method is intentionally left blank.
		}

	}

	/**
	 * Constants.
	 */
	private Constants() {

	}

}
