package Claycorp;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Logger
{
    private static DataSettings settings;
    private static EntryGUI entryGUI;

    //TODO: Name this properly.
    static Path logFile = Paths.get("inventoryprogram.log");

    Logger(DataSettings settingsIn, EntryGUI entryGUIIn)
    {
        settings = settingsIn;
        entryGUI = entryGUIIn;
    }

    public static void setEntryGUI(EntryGUI entryGUI1)
    {
        entryGUI = entryGUI1;
    }

    public static void log(String input, int level)
    {
        if (Files.notExists(logFile))
        {
            try
            {
                Files.createFile(logFile);
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), " \" " + e.getMessage() + "\"  Yell at the programmer for being bad!", "Logging File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //TODO: Can this be better?
        try
        {
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(settings.dateTimeFormat);
            LocalDateTime timeNow = LocalDateTime.now();
            String infoBase = ("<br>" + dateTimeFormat.format(timeNow) + "-[INFO]: " + input);
            String warnBase = ("<br>" + dateTimeFormat.format(timeNow) + "-[WARN]: " + input);
            String errorBase = ("<br>" + dateTimeFormat.format(timeNow) + "-[ERROR]: " + input);
            String youFuckedUpBigTime = ("<br>" + dateTimeFormat.format(timeNow) + "-[ERROR]PROGRAMING ERROR! " + input);
            if (settings.debug && settings.showConsole)
            {
                switch (level)
                {
                    case 0:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[INFO]: " + input), StandardOpenOption.APPEND);
                        //entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "-[INFO]: " + input);
                        entryGUI.consoleAppend( "<font color=#6c6c6>"+ infoBase + "</font></br></html>");
                        break;
                    case 1:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[WARN]: " + input), StandardOpenOption.APPEND);
                        //entryGUI.consoleAppend("\n" + "<html><font color=yellow> " + dateTimeFormat.format(timeNow) + "-[WARN]: " + input + "</html>");
                        entryGUI.consoleAppend( "<font color=#dbd300>"+ warnBase + "</font></br></html>");
                        break;
                    case 2:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[ERROR]: " + input), StandardOpenOption.APPEND);
                        //entryGUI.consoleAppend("\n" + "<html><font color=#ffffdd> "  + dateTimeFormat.format(timeNow) + "-[ERROR]: " + input + "</font></html>");
                        entryGUI.consoleAppend( "<font color=#ff0000>"+ errorBase + "</font></br></html>");
                        break;
                    default:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR!"), StandardOpenOption.APPEND);
                        //entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR! \"" + level + "\" is not a valid logging level!");
                        entryGUI.consoleAppend( "<font color=#ff0000>"+ youFuckedUpBigTime + "</font></br></html>");
                }
            }
            else
                switch (level)
                {
                    case 0:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[INFO]: " + input), StandardOpenOption.APPEND);
                        break;
                    case 1:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[WARN]: " + input), StandardOpenOption.APPEND);
                        break;
                    case 2:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[ERROR]: " + input), StandardOpenOption.APPEND);
                        break;
                    default:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR! \"" + level + "\" is not a valid logging level!"), StandardOpenOption.APPEND);
                }

        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Your logging file \" " + e.getMessage() + "\" Does not exist or is corrupt! Yell at the programer for being bad!", "Logging File Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }
        //TODO: Check the date format BEFORE saving to file or closing the settings, This will do for now...
        catch (IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Date/Time format is not valid! \n " + e.getMessage(), "Date/Time Format Error", JOptionPane.ERROR_MESSAGE);
            System.out.print(e);
        }
    }
}
