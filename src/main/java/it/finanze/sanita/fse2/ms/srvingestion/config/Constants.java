package it.finanze.sanita.fse2.ms.srvingestion.config;

/**
 * 
 * @author vincenzoingenito
 *
 * Constants application.
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

		/** 
		 * Collection name 
		 */
		public static final String COLLECTION_NAME = "ingestion-staging";

		/** 
		 * This method is intentionally left blank 
		 */
		private ComponentScan() {
		}
	}

	/** 
	 * Contains all the constants used in the headers of HTTP calls 
	 */
	public static final class Headers {

		/**
		 * JWT header field.
		 */
		public static final String JWT_HEADER = "Authorization";

		/**
		 * This method is intentionally left blank. 
		 */
		private Headers() {
		}

	}

	/** 
	 * Contains the constants for the launch profile of the SpringBoot application. 
	 * 
	 */
	public static final class Profile {

		/**
		 * Dev Profile 
		 */
		public static final String DEV = "DEV";

		/**
		 * Test Profile 
		 */
		public static final String TEST = "TEST";

		/** 
		 * Test Prefix for MongoDB Collections 
		 */
		public static final String TEST_PREFIX = "test";

		/**
		 * Constructor. This method is intentionally left blank. 
		 */
		private Profile() {
		}

	}
	
	/** 
	 * Generic Constants used in the application. 
	 *
	 */
	public static final class App {

		/** 
		 * The JWT Token Audience 
		 */
		public static final String JWT_TOKEN_AUDIENCE = "eds-ingestion";

		/**
		 * The JWT Token Type 
		 */
		public static final String JWT_TOKEN_TYPE = "JWT";

		/** 
		 * The JWT Bearer Prefix 
		 */
		public static final String BEARER_PREFIX = "Bearer ";

		/** 
		 * The deleted field
		 */
		public static final String DELETED = "deleted";

		/**
		 * SHA-256 
		 */
		public static final String SHA_256 = "SHA-256";

		/** 
		 * Identifier 
		 */
		public static final String IDENTIFIER = "identifier";

		/** 
		 * This method is intentionally left blank. 
		 */
		private App() {
		}
	}

	/**
	 * Constants used in logging. 
	 *
	 */
	public static final class Logs {

		/**
		 * When POST /document is called 
		 */
		public static final String CALLED_API_POST_DOCUMENT = "Called POST /document";

		/**
		 * When PUT /document is called (Replace) 
		 */
		public static final String CALLED_API_PUT_DOCUMENT = "Called PUT /document";

		/**
		 * When PUT /document/metadata is called (Update) 
		 */
		public static final String CALLED_API_UPDATE_DOCUMENT = "Called PUT /document/metadata";

		/**
		 * When DELETE /document is called 
		 */
		public static final String CALLED_API_DELETE_DOCUMENT = "Called DELETE /document";

		/**
		 * When GET /document/:id is called 
		 */
		public static final String CALLED_API_GET_DOCUMENT_BY_IDENTIFIER = "Called GET /document/:id";

		/**
		 * When GET /document is called 
		 */
		public static final String CALLED_API_GET_DOCUMENTS = "Called GET /document";

		/** 
		 * When Kafka Message is sent to Data Processor (Asynchronous Flow) 
		 */
		public static final String KAFKA_SENDING_MESSAGE = "Kafka: sending message to Data Processor";

		/**
		 * When Kafka Message is correctly send 
		 */
		public static final String KAFKA_SEND_SUCCESS = "Send success.";

		/** 
		 * When there is an error sending the Kafka Message 
		 */
		public static final String KAFKA_SEND_FAILED = "Error sending kafka message";

		/**
		 * When there is a Kafka error while sending the message 
		 */
		public static final String KAFKA_SENDING_FAILED = "Kafka Error: sending message to Data Processor";

		/**
		 * When there is an error retrieving the host info 
		 */
		public static final String ERROR_RETRIEVING_HOST_INFO = "Error while retrieving host informations";

		/**
		 * When there is an error in a MongoDB insertion  
		 */
		public static final String ERROR_MONGO_INSERT = "MongoDB: Error while inserting document";

		/**
		 * When there is a Connection Refused error 
		 */
		public static final String ERROR_CONNECTION_REFUSED = "Error: connection refused by Data Processor Host";

		/** 
		 * When the document is not found on the staging database
		 */
		public static final String ERROR_DOCUMENT_NOT_FOUND = "Error: document not found on staging database";

		/**
		 * When there is an empty document in the bundle  
		 */
		public static final String ERROR_EMPTY_DOCUMENT = "Error: empty document in bundle";

		/** 
		 * Generic Exception Handler 
		 */
		public static final String ERROR_HANDLER_GENERIC_EXCEPTION = "HANDLER handleGenericException()";

		/** 
		 * Operation Exception Handler 
		 */
		public static final String ERROR_HANDLER_OPERATION_EXCEPTION = "HANDLER handleOperationException()";

		/** 
		 * Unsupported Operation Exception Handler 
		 */
		public static final String ERROR_HANDLER_UNSUPPORTED_OPERATION_EXCEPTION = "HANDLER handleUnsupportedOperationException()";

		/** 
		 * Connection Refused Exception Handler 
		 */
		public static final String ERROR_HANDLER_CONNECTION_REFUSED_EXCEPTION = "HANDLER handleConnectionRefusedException()";

		/** 
		 * Document Not Found Exception Handler 
		 */
		public static final String ERROR_HANDLER_DOCUMENT_NOT_FOUND_EXCEPTION = "HANDLER handleDocumentNotFoundException()";

		/** 
		 * Document Already Exists Exception Handler 
		 */
		public static final String ERROR_HANDLER_DOCUMENT_ALREADY_EXISTS_EXCEPTION = "HANDLER handleDocumentAlreadyExistsException()";

		/** 
		 * Handle Empty Document Exception Handler 
		 */
		public static final String ERROR_HANDLER_EMPTY_DOCUMENT_EXCEPTION = "HANDLER handleEmptyDocumentException()";

		/** 
		 * This method is intentionally left blank 
		 */
		private Logs() {
		}

	}

	/**
	 * Constants.
	 */
	private Constants() {

	}

}
