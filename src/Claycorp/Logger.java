package Claycorp;

import javax.swing.*;
import java.awt.*;
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

        //todo: How to catch this if it shits the bed? User needs to be notified...
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(settings.dateTimeFormat);
        LocalDateTime timeNow = LocalDateTime.now();

        //todo: Rework all of this to a new system. This current version sucks ass.
        try
        {
            if (settings.debug && settings.showConsole)
            {
                switch (level)
                {
                    case 0:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[INFO]: " + input), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "-[INFO]: " + input, Color.gray);
                        break;
                    case 1:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[WARN]: " + input), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "-[WARN]: " + input, Color.yellow);
                        break;
                    case 2:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "-[ERROR]: " + input), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "-[ERROR]: " + input, Color.red);
                        break;
                    default:
                        Files.write(logFile, Collections.singleton(dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR!"), StandardOpenOption.APPEND);
                        entryGUI.consoleAppend("\n" + dateTimeFormat.format(timeNow) + "[ERROR] PROGRAMING ERROR! \"" + level + "\" is not a valid logging level!", Color.red);
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
    }
}
