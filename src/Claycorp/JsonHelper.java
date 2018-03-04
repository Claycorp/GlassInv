package Claycorp;

import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper
{
    //TODO: Add backup system for invalid/corrupt files.
    public static List<DataGlassSheet> loadDatabase(Path databaseFile)
    {
        if (Files.notExists(databaseFile))
        {
            return new ArrayList<>();
        }
        else
        {
            try (FileReader fileReader = new FileReader(databaseFile.toFile()))
            {
                ArrayList<DataGlassSheet> db = Main.gson.fromJson(fileReader, new TypeToken<ArrayList<DataGlassSheet>>() {}.getType());
                if (db == null) return new ArrayList<>();
                return db;
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
                //Helper.logger("\n" + e.getMessage(), 2, entryGUI);
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

    }

    //Save Database to JSON
    public static void saveDatabase(Path databaseFile, List<DataGlassSheet> database)
    {
        // No appending, json needs to output all the file at once.
        try (FileWriter writerFile = new FileWriter(databaseFile.toFile(), false))
        {
            Main.gson.toJson(database, writerFile);
        }
        catch (IOException i)
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), i.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
            //Helper.logger("\n" + i.getMessage(), 2, entryGUI);
            i.printStackTrace();
        }
    }

    public static DataSettings loadSettings(Path settingsFile)
    {
        DataSettings obj = new DataSettings();

        if (Files.notExists(settingsFile))
        {
            saveSettings(settingsFile, obj);
            return obj;
        }
        else
        {
            try (FileReader fileReader = new FileReader(settingsFile.toFile()))
            {
                DataSettings tmp = Main.gson.fromJson(fileReader, new TypeToken<DataSettings>() {}.getType());
                if (tmp == null) return obj;
                return tmp;
            }
            catch (IOException i)
            {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), i.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
                //Helper.logger("\n" + i.getMessage(), 2, entryGUI);
                i.printStackTrace();
                return obj;
            }
        }
    }

    //Save the settings to the file.
    public static void saveSettings(Path settingsFile, DataSettings settings)
    {
        try (FileWriter fileWriter = new FileWriter(settingsFile.toFile(), false))
        {
            Main.gson.toJson(settings, fileWriter);
        }
        catch (IOException i)
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), i.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
            //Helper.logger("\n" + i.getMessage(), 2, entryGUI);
            i.printStackTrace();
        }
    }
}
