package Claycorp;

import java.util.ArrayList;

public class DataSettings
{
    public ArrayList omniBoxOptions = new ArrayList();
    public boolean debug = true;
    public boolean showConsole = false;
    public boolean showQuickEntry = true;
    public String dateTimeFormat = "yyyy/MM/dd-HH:mm:ss";
    public boolean redTape;
    public String selectedPrinter = "QL-700";
    public String seletedPaper = "12";

    DataSettings ()
    {
        omniBoxOptions.add("Kokomo");
        omniBoxOptions.add("Spectrum");
        omniBoxOptions.add("Oceanside Glasstile");
        omniBoxOptions.add("Armstrong");
        omniBoxOptions.add("Wismach");
    }
}
