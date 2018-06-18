package Claycorp;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Helper
{
    private static Pattern numberRegex = Pattern.compile("([0-9]+)");
    private static Pattern moneyRegex = Pattern.compile("(?:\\d*\\.)?\\d+");
    private static Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

    public Helper() {}

    public static int regexNumberCheck(String in) throws NumberFormatException
    {
        if (numberRegex.matcher(in).matches())
        {
            return Integer.parseInt(in);
        }
        else
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "\'" + in + "\' is not a valid size!", "Number Error", JOptionPane.ERROR_MESSAGE);
            Logger.log("\'" + in + "\' is not a valid size!", 2);
            throw new NumberFormatException("\'" + in + "\' is not a valid size!");
        }
    }

    public static BigDecimal regexCompareMoney(String price) throws NumberFormatException
    {
        if (moneyRegex.matcher(price).matches())
        {
            return BigDecimal.valueOf(Long.parseLong(price));
        }
        else
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "\'" + price + "\' is not a valid price!", "Number Error", JOptionPane.ERROR_MESSAGE);
            Logger.log("\'" + price + "\' is not a valid price!", 2);
            throw new NumberFormatException("\'" + price + "\' is not a valid price!");
        }
    }

    public static BigDecimal calculateCostPerInch(BigDecimal pricePaid, int totalArea)
    {
        return pricePaid.divide(BigDecimal.valueOf(totalArea), 2, BigDecimal.ROUND_HALF_UP);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            Logger.log("Couldn't find file: " + path, 2);
            return null;
        }
    }

    //TODO: Finish adding support for these POS printers, String needs to be broken into parts for user selecting.
    //TODO: Temp folder needs to be made for saving to. We are always going to save to the same image file. Makes things easy....
    //TODO: Find out how to install the 8 dependencies required to use this stupid thing on windows. (Python, Pyusb, Goofy windows driver)
    //TODO: What can we do to make this work with other OS's as this is currently 100% windows dependent. Can we find out what we are running on then run separate command?
    //TODO: Add printing section to the commands, Currently it only creates the data to be printed and never prints it.
    //TODO: Add lots of logging for debug purposes.
    //TODO: MAKE SURE TO REDIRECT OUTPUT AND ERRORS TO LOGS! ALWAYS!
    public static void brotherLabelPrint(final Path settingsFile)
    {
        DataSettings settings = JsonHelper.loadSettings(settingsFile);

        // String 1: The program to run.
        // String 2: Command for CMD to run command and terminate.
        // String 3: The command being sent to the python printing program. This "brother_ql_create" only creates the printing data, "brother_ql_print" is needed to send the data.
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "c/" ,"brother_ql_create -m " + settings.selectedPrinter + " -s " + settings.selectedPaper + " F:\\Java\\Game\\src\\Claycorp\\code128.png F:\\Java\\Game\\temp\\test.bin");
        try
        {
            Process p = builder.start();
            System.out.print(p.getErrorStream());

            System.out.print("Attepmting to print?");
        }
        catch (IOException e)
        {
            //TODO: Log shit correctly.
            e.printStackTrace();
        }

    }

    public Font[] getFontList()
    {
        //TODO: Do something with this.
        return fonts;
    }

    //TODO: Clean this up of all the printlines.
    //TODO: Log this stuff proper.
    //TODO: (Zero Priority) We might as well keep this, there isn't a point in limiting anything so lets make sure it works and has all the options it needs.
    public static void printLabel()
    {
        DataLabel labelData = new DataLabel();

        //Get the list of printers. We don't care about anything so null works.
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        //Need this to set the selected printer when opening the print UI.
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        //All the magic settings for the magic box to do magic. This is empty and is filled in after print confirmation. (AKA Page settings.)
        PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();

        //Open the print UI.
        PrintService selectedPrintService = ServiceUI.printDialog(null, 150, 150, printServices, defaultPrintService, null, attrib);

        //PrintServiceAttributeSet printJob = printServices[3].getAttributes();
        Media[] res = (Media[]) printServices[3].getSupportedAttributeValues(Media.class, null, null);
        for (Media media : res) {
            if (media instanceof MediaSizeName) {
                MediaSizeName msn = (MediaSizeName) media;
                MediaSize ms = MediaSize.getMediaSizeForName(msn);
                float width = ms.getX(MediaSize.MM);
                float height = ms.getY(MediaSize.MM);
                System.out.println(media + ": width = " + width + "; height = " + height);
            }
        }

        Doc doc = new SimpleDoc(labelData, DocFlavor.SERVICE_FORMATTED.PRINTABLE,null);

        if(selectedPrintService!=null)
        {
            DocPrintJob job = selectedPrintService.createPrintJob();

            try
            {
                job.print(doc, attrib);
            }
            catch (PrintException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("selection cancelled");
    }
}
