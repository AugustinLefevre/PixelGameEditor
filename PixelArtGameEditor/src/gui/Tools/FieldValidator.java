package gui.Tools;

public interface FieldValidator {
	public default boolean textIsValid(String str) {
		
		// special char a completer
		CharSequence[] speChar = {"/", " ", "\\", "&", "\"", "|", "*", "+", ";"}; 
		for(CharSequence c : speChar) {
			if(str.contains(c)) {
				return false;
			}
		}
		if(str.length() <= 1 || str == null) {
			return false;
		}
		return true;
	}
}
