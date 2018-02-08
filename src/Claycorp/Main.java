package Claycorp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    static       Gson       gson        = new GsonBuilder().setPrettyPrinting().create();
    static       JsonObject root        = new JsonObject();
    static final TestGUI    GUIINSTANCE = new TestGUI();
    static       Helper     helper      = new Helper();

    public static void main(String [] args) throws IOException
    {
        JFrame frame = new JFrame("Glass Entry Form");
        frame.setContentPane(GUIINSTANCE.newGlassEntry);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Path databaseFile = Paths.get("databaseFile.json");
        if (Files.notExists(databaseFile))
        {
            Files.createFile(databaseFile);
        }

    }

}
