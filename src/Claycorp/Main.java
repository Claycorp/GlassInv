package Claycorp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public final List<DataSettings> settings = new ArrayList<>();

    public static void main(String[] args)
    {
        //todo: I feel like the 'FILE' part in the name is a bit redundant, Clean this up...
        // This must happen first, cause errors will happen otherwise
        Path databaseFile = Paths.get("databaseFile.json");
        Path settingsFile = Paths.get("settingsFile.json");
        Path logFile = Paths.get("log.txt");

        new GlassEntryGUI(databaseFile, settingsFile);
        //todo: Should I load settings here? I feel like I should... LUL Also why does it seem like I should be using a single settings list?
    }
}
