package Claycorp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static EntryGUI entrygui;

    public static void main(String[] args)
    {
        // This must happen first, cause errors will happen otherwise
        Path databaseFile = Paths.get("database.json");
        Path settingsFile = Paths.get("settings.json");
        new File("temp").mkdir();

        new Claycorp.Logger(JsonHelper.loadSettings(settingsFile), null);
        entrygui = new EntryGUI(databaseFile, settingsFile);
        Claycorp.Logger.setEntryGUI(entrygui);
    }
}
