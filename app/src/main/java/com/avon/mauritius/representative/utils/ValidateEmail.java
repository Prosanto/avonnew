package com.avon.mauritius.representative.utils;

public class ValidateEmail {

	public final static boolean validateEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

}
