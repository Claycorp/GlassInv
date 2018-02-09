package Claycorp;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Helper
{
    private static Pattern numberRegex = Pattern.compile("([0-9]+)");
    private static Pattern moneyRegex = Pattern.compile("(?:\\d*\\.)?\\d+");

    private Helper() {}

    public static int regexNumberCheck(String in) throws NumberFormatException
    {
        if (numberRegex.matcher(in).matches())
        {
            return Integer.parseInt(in);
        }
        else
        {
            throw new NumberFormatException("Size " + in + " is not a number!");
        }
    }

    public static BigDecimal regexCompareMoney(String price) throws NumberFormatException
    {
        if (moneyRegex.matcher(price).matches())
        {
            return BigDecimal.valueOf(Long.parseLong(price));
        }
        else
        {
            throw new NumberFormatException("Money is in an invalid format or has invalid text!");
        }
    }

    public static BigDecimal calculateCostPerInch(BigDecimal pricePaid, int totalArea)
    {
         return pricePaid.divide(BigDecimal.valueOf(totalArea), 2, BigDecimal.ROUND_HALF_UP);
    }
}
