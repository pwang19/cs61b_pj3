package dict;

public class InvalidKeyException extends Exception {
	public InvalidKeyException() {
		super();
	}
	
	public InvalidKeyException(Object key) {
		System.out.println(key);
	}
}
