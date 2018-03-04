package Claycorp;

import uk.org.okapibarcode.backend.QrCode;
import uk.org.okapibarcode.output.Java2DRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JFrameLabelBuilder extends JPanel
{
    QrCode barcode = new QrCode();

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        int widith = barcode.getWidth();
        int hight = barcode.getHeight();

        BufferedImage image = new BufferedImage(widith, hight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0,0, widith, hight);

        Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
        renderer.render(barcode);

        g.drawImage(image, 100, 100, this);
    }

    public void setBarcodeContent(String barcodeContent)
    {
        barcode.setContent(barcodeContent);
    }
}
