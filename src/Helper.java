import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Helper
{
    int size1 = 1;
    int size2 = 1;
    int totalArea = 1;

    Integer UUID = 0;

    BigDecimal pricePaid = BigDecimal.valueOf(1);
    BigDecimal pricePerIN = BigDecimal.valueOf(0);

    private Pattern numberRegex = Pattern.compile("([0-9]+)");
    private Pattern moneyRegex = Pattern.compile("(?:\\d*\\.)?\\d+");

    void regexNumberCheck1(String in)
    {
        if (numberRegex.matcher(in).matches())
        {
            size1 = Integer.parseInt(in);
        }
        else {
            Main.GUIINSTANCE.console.append("\nSize " + in + " is not a number!");
        }
    }

    void regexNumberCheck2(String in)
    {
        if (numberRegex.matcher(in).matches())
        {
            size2 = Integer.parseInt(in);
        }
        else {
            Main.GUIINSTANCE.console.append("\nSize " + in + " is not a number!");
        }
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

    void calculateCostPerInch ()
    {
         pricePerIN = pricePaid.divide(BigDecimal.valueOf(totalArea), 2, BigDecimal.ROUND_HALF_UP);
    }

    void calculateArea ()
    {
        totalArea = size1 * size2;
    }

      //Main.GUIINSTANCE.console.append("\nArea " + Main.GUIINSTANCE.totalArea.toString());

      //  Main.GUIINSTANCE.console.append("\nCost " + pricePaid.toString() + " Price per IN " + pricePerIN.toString());

    public void saveToJson()
    {
        Main.gson.toJson(Helper.class);
    }

    public void writeToFile()
    {
        try (FileWriter writerFile = new FileWriter("databaseFile.json", true))
        {

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
