package Claycorp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.regex.Pattern;

public class Helper
{
    private static Pattern numberRegex = Pattern.compile("([0-9]+)");
    private static Pattern moneyRegex = Pattern.compile("(?:\\d*\\.)?\\d+");

    public Helper() {}

    public static int regexNumberCheck(String in) throws NumberFormatException
    {
        if (numberRegex.matcher(in).matches())
        {
            return Integer.parseInt(in);
        }
        else
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "\'" + in + "\' is not a valid size!", "Number Error", JOptionPane.ERROR_MESSAGE);
            //TODO: Fix this :/
            //logger("\'" + in + "\' is not a valid size!", 2, settings, entryGUI);
            throw new NumberFormatException("\'" + in + "\' is not a valid size!");
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
            throw new NumberFormatException("\'" + price + "\' is not a valid price!");
        }
    }

    public static BigDecimal calculateCostPerInch(BigDecimal pricePaid, int totalArea)
    {
        return pricePaid.divide(BigDecimal.valueOf(totalArea), 2, BigDecimal.ROUND_HALF_UP);
    }

    //todo: I feel like the settings and entry GUI stuff is incorrect... :/ Ask Dries about it. Also I be the logging stuff can be simplified. Perhaps nested swiching would be better.
    public static void logger(String input, int level, EntryGUI entryGUI)
    {

        DataSettings settings;
        Path logFile = Paths.get(settings.logfile + ".txt");

        //todo: How to catch this if it shits the bed? User needs to be notified...
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(settings.dateTimeFormat);
        LocalDateTime timeNow = LocalDateTime.now();

        try
        {
            if (settings.debug && settings.showConsole)
            {
                switch (level)
                {
                    case 0:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "-[INFO]: " + input), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "-[INFO]: " + input, Color.gray);
                        break;
                    case 1:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "-[WARN]: " + input), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "-[WARN]: " + input, Color.yellow);
                        break;
                    case 2:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "-[ERROR]: " + input), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "-[ERROR]: " + input, Color.red);
                        break;
                    default:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR!"), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR! \"" + level + "\" is not a valid logging level!", Color.red);
                }
            }
            else
                switch (level)
                {
                    case 0:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "-[INFO]: " + input), StandardOpenOption.APPEND);
                        break;
                    case 1:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "-[WARN]: " + input), StandardOpenOption.APPEND);
                        break;
                    case 2:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "-[ERROR]: " + input), StandardOpenOption.APPEND);
                        break;
                    default:
                        Files.write(logFile, Collections.singleton("\n" + dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR! \"" + level + "\" is not a valid logging level!"), StandardOpenOption.APPEND);
                }

        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(), "Number Error", JOptionPane.ERROR_MESSAGE);
            logger(e.getMessage(), 2, entryGUI);
        }
    }

}
