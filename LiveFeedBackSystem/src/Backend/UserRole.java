package Backend;

public enum UserRole {
	Student(2), Professor(1), admin(0);
	
	private final int value;
   /**
    * Constructor von UserRole
    * @param value
    */
	private UserRole(int value) {
        this.value = value;
    }
/**
 * gibt die value von einer Role an.
 * @return
 */
    public int getValue() {
        return value;
    }
}
