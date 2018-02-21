package Claycorp;

import com.google.gson.reflect.TypeToken;

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
        try (FileReader fileReader = new FileReader(databaseFile.toFile()))
        {
            ArrayList<DataGlassSheet> db = Main.gson.fromJson(fileReader, new TypeToken<ArrayList<DataGlassSheet>>(){}.getType());
            if (db == null) return new ArrayList<>();
            return db;
        }
        catch (IOException e)
        {
            // todo: Show popup or yell at user
            e.printStackTrace();
        }
        return new ArrayList<>();

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
            // todo: Show popup or yell at user
            i.printStackTrace();
        }
    }

    public static List<DataSettings> loadSettings(Path settingsFile)
    {
        final List<DataSettings> settings = new ArrayList<>();
        if (Files.notExists(settingsFile))
        {
            try
            {
                Files.createFile(settingsFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            DataSettings obj = new DataSettings();
            obj.omniBoxOptions = new String[] {"Kokomo", "Spectrum", "Oceanside Glasstile", "Armstrong", "Wismach"};
            obj.debug = false;
            obj.showConsole = true;
            obj.showQuickEntry = true;
            settings.add(obj);
            saveSettings(settingsFile, settings);
        }
        else try (FileReader fileReader = new FileReader(settingsFile.toFile()))
        {
            Main.gson.fromJson(fileReader, new TypeToken<ArrayList<DataSettings>>(){}.getType());
        }
        catch (IOException i)
        {
            // todo: Show popup or yell at user
            i.printStackTrace();
        }
        return new ArrayList<>();
    }

    //Save the settings to the file.
    public static void saveSettings(Path settingsFile, List<DataSettings> settings)
    {
        try (FileWriter fileWriter = new FileWriter(settingsFile.toFile(), false))
        {
            Main.gson.toJson(settings, fileWriter);
        }
        catch (IOException i)
        {
            // todo: Show popup or yell at user
            i.printStackTrace();
        }
    }
}
