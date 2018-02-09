package Claycorp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException
    {
        // This must happen first, cause errors will happen otherwise
        Path databaseFile = Paths.get("databaseFile.json");
        if (Files.notExists(databaseFile))
        {
            Files.createFile(databaseFile);
        }
        new GlassEntryGUI(databaseFile);
    }
}
