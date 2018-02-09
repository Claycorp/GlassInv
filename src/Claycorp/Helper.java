package Claycorp;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Helper
{
    int size1 = 1;
    int size2 = 1;
    int totalArea = 1;

    BigDecimal pricePaid = BigDecimal.valueOf(1);
    BigDecimal pricePerIN = BigDecimal.valueOf(0);

    private Pattern numberRegex = Pattern.compile("([0-9]+)");
    private Pattern moneyRegex = Pattern.compile("(?:\\d*\\.)?\\d+");

    int regexNumberCheck1(String in)
    {
        if (numberRegex.matcher(in).matches())
        {
            size1 = Integer.parseInt(in);
            return size1;
        }
        else {
            Main.GUIINSTANCE.console.append("\nSize " + in + " is not a number!");
        }
        //TODO: Make error window to notify user of incorrect data when attepting to save it.
        return 0;
    }

    int regexNumberCheck2(String in)
    {
        if (numberRegex.matcher(in).matches())
        {
            size2 = Integer.parseInt(in);
            return size2;
        }
        else {
            Main.GUIINSTANCE.console.append("\nSize " + in + " is not a number!");
        }
        //TODO: Make error window to notify user of incorrect data when attepting to save it.
        return 0;
    }

    void regexCompareMoney(String price)
    {
        if (moneyRegex.matcher(price).matches())
        {
            pricePaid = BigDecimal.valueOf(Long.parseLong(price));
        }
        else {
            Main.GUIINSTANCE.console.append("\nMoney is in an invalid format or has invalid text!");
        }
    }

    BigDecimal calculateCostPerInch ()
    {
         pricePerIN = pricePaid.divide(BigDecimal.valueOf(totalArea), 2, BigDecimal.ROUND_HALF_UP);
         return pricePerIN;
    }

    int calculateArea ()
    {
        totalArea = size1 * size2;
        return totalArea;
    }
}
