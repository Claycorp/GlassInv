package Claycorp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args)
    {
        // This must happen first, cause errors will happen otherwise
        Path databaseFile = Paths.get("database.json");
        Path settingsFile = Paths.get("settings.json");

        new EntryGUI(databaseFile, settingsFile);

    }
}
