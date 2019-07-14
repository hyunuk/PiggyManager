package helper;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
	public static String getCanDollarFormat(Double value){
		NumberFormat canFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
		return canFormat.format(value);
	}
}
