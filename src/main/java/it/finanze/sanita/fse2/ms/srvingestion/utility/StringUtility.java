package it.finanze.sanita.fse2.ms.srvingestion.utility;

import com.google.gson.Gson;

public final class StringUtility {

	/**
	 * Private constructor to avoid instantiation.
	 */
	private StringUtility() {
		// Constructor intentionally empty.
	}

	/**
	 * Returns {@code true} if the String passed as parameter is null or empty.
	 * 
	 * @param str	String to validate.
	 * @return		{@code true} if the String passed as parameter is null or empty.
	 */
	public static boolean isNullOrEmpty(final String str) {
	    boolean out = false;
		if (str == null || str.isEmpty()) {
			out = true;
		}
		return out;
	} 
	
	/**
	 * Transformation from Json to Object.
	 * 
	 * @param <T>	Generic type of return
	 * @param json	json
	 * @param cls	Object class to return
	 * @return		object
	 */
	public static <T> T fromJSON(final String json, final Class<T> cls) {
		return new Gson().fromJson(json, cls);
	}

	/**
	 * Transformation from Object to Json.
	 * 
	 * @param obj	object to transform
	 * @return		json
	 */
	public static String toJSON(final Object obj) {
		return new Gson().toJson(obj);
	}
	

}

