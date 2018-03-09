package Claycorp;

import java.awt.*;

public class DataLabel
{

    int labelWidth = 300;
    int labelHeight = 300;

    Dimension labelSize = new Dimension(labelWidth, labelHeight);

    //TODO: What should this be for displaying barcode?
    boolean displayBarcode = true;
    int xposBarcode = 50;
    int yposBarcode = 50;
    int widthBarcode = 700;
    int heightBarcode = 700;

    String title = "Title";
    boolean displayTitle = true;
    int xposTitle = 10;
    int yposTitle = 5;
    int widthTitle = 3;
    int heightTitle = 3;

    String size = "Size";
    boolean displaySize = true;
    int xposSize = 1120;
    int yposSize = 30;
    int widthSize = 30;
    int heightSize = 30;

    String price = "Price";
    boolean displayPrice = true;
    int xposPrice = 1120;
    int yposPrice = 70;
    int widthPrice = 30;
    int heightPrice = 30;

}
