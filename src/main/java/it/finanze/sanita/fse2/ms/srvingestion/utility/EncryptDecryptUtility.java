package it.finanze.sanita.fse2.ms.srvingestion.utility;

import org.jasypt.util.text.AES256TextEncryptor;

/** 
 * Utility Class used to encrypt and decrypt strings 
 * 
 *
 */
public class EncryptDecryptUtility {

	private EncryptDecryptUtility() {
	}
	
    /** 
     *  Method for the encrypt.
     *  @param pwd  The pwd for encryption 
     *  @param msg  The message to encrypt 
     *  @return String  The encrypted string 
     */
	public static final String encrypt(String pwd, String msg) {
	    AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
    	 
    	textEncryptor.setPassword(pwd);
    	return textEncryptor.encrypt(msg);
	}

	/** 
     *  Method for decrypt. 
     *  
     *  @param pwd  The pwd for decryption 
     *  @param cryptedMsg  The crypted message 
     *  @return String  The decrypted string 
     */
	public static final String decrypt(String pwd, String cryptedMsg) {
    	AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
    	textEncryptor.setPassword(pwd);
    	return textEncryptor.decrypt(cryptedMsg);
	}
}
