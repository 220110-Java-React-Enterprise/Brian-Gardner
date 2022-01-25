package InputValidation;

//Class holding static methods to format currency to $XX,XXX.XX
public class CurrencyFormatter {
    public static String format(double dbl) {
        boolean isNegative = false;

        //Check if dbl is negative
        if (dbl < 0) {
            dbl = -dbl;
            isNegative = true;
        }

        //Convert to 2-decimal-place format String
        String decimalFormat = String.format("%.2f", dbl);

        //String to be returned
        String tmpString = "";

        //Index for the start of the substring that gets added to tmpString before the ','
        //Starts at 0 then gets set to x when a comma is added to tmpString
        int y = 0;

        //Iterate through the string, starting at index 1
        for (int x = 1; x < decimalFormat.length() - 5; x++) {
            if ((decimalFormat.length() - x) % 3 == 0) {
                tmpString += decimalFormat.substring(y, x) + ',';
                y = x;
            }
        }

        //Add remaining digits after commas are done being added in
        tmpString += decimalFormat.substring(y, decimalFormat.length());

        //Add dollar sign to start of string
        tmpString = "$" + tmpString;

        //Add - if negative
        tmpString = (isNegative ? "-" : "") + tmpString;

        //Return string
        return tmpString;
    }
}
