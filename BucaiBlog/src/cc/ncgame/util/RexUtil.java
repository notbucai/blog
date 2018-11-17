package cc.ncgame.util;

import java.util.regex.Pattern;

public class RexUtil {
	public static boolean rexEmail(String email) {
		return Pattern.matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$", email);
	}
	public static void main(String[] args) {
		boolean rexEmail = rexEmail("145921@qq.com");
		System.out.println(rexEmail);
	}
}
