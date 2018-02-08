package Claycorp;

import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonHelper
{

    //Add backup system for invalid/corrupt files.
    public static ArrayList<DataGlassSheet> loadDatabase()
    {
        try (FileReader fileReader = new FileReader("databaseFile.json"))
        {
            ArrayList<DataGlassSheet> db = Main.gson.fromJson(fileReader, new TypeToken<ArrayList<DataGlassSheet>>(){}.getType());
            if (db == null) return new ArrayList<>();
            return db;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void saveDatabase(ArrayList<DataGlassSheet> database)
    {
        // No appending, json needs to output all the file at once.
        try (FileWriter writerFile = new FileWriter("databaseFile.json", false))
        {
            Main.gson.toJson(database, writerFile);
        }
        catch (IOException i)
        {
            i.printStackTrace();
        }
    }
}
