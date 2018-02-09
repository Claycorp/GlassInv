package Claycorp;

import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper
{
    //Add backup system for invalid/corrupt files.
    public static List<DataGlassSheet> loadDatabase(Path databaseFile)
    {
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
}
