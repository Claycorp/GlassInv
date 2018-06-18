package Claycorp;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

public class LabelMakerGUI extends JComponent
{
    private JSpinner titleXpos;
    private JSpinner titleYpos;
    private JSpinner sizeXpos;
    private JSpinner sizeYpos;
    private JSpinner priceXpos;
    private JSpinner priceYpos;
    private JSpinner barcodeXpos;
    private JSpinner barcodeYpos;
    public JPanel contentPane;
    public JPanel labelPane;
    private JSpinner barcodeScale;
    private JCheckBox barcodeDisplay;
    private JCheckBox titleDispaly;
    private JCheckBox sizeDisplay;
    private JCheckBox priceDisplay;
    private JButton cursorPos;
    private JPanel editorPane;
    private JButton print;
    private JButton colorTitle;
    private DataLabel lableImage;
    private JButton colorSize;
    private JButton colorPrice;
    private JComboBox titleFontCombobox;
    private JComboBox sizeFontCombobox;
    private JComboBox priceFontCombobox;
    private JSpinner titleTextFontSize;
    private JSpinner sizeTextFontSize;
    private JSpinner priceTextFontSize;
    private JSpinner labelHeight;
    private JSpinner labelWidth;
    private JPanel labelExtras;
    private JLabel titleLRText;
    private JLabel titleUDText;
    private JLabel priceLRText;
    private JLabel priceUDText;
    private JLabel sizeLRText;
    private JLabel sizeUDText;
    private JButton barcodeColorButton;
    private JButton barcodeBackgroundColorButton;
    private JLabel barcodeSText;
    private JLabel titleTSText;
    private JLabel titleCText;
    private JLabel titlePText;
    private JLabel titleFOText;
    private JLabel barcodePText;
    private JLabel barcodeCOText;
    private JLabel barcodeMCText;
    private JLabel barcodeBCText;
    private JLabel sizePText;
    private JLabel pricePText;
    private JLabel sizeCText;
    private JLabel sizeFSText;
    private JLabel priceFSText;
    private JLabel sizeFOText;
    private JLabel priceFOText;
    private JLabel priceCText;
    private JLabel barcodeUDText;
    private JLabel barcodeLRText;
    private JCheckBox barcodeBackground;
    private JLabel barcodeRBText;

    private DataLabel labelData = new DataLabel();
    private ImageIcon titleColorIcon = new ImageIcon(makeIcon(DataLabel.titleColor));
    private ImageIcon sizeColorIcon = new ImageIcon(makeIcon(DataLabel.sizeColor));
    private ImageIcon priceColorIcon = new ImageIcon(makeIcon(DataLabel.priceColor));
    private ImageIcon barcodeBackgroundColorIcon = new ImageIcon(makeIcon(DataLabel.barcodeBackgroundColor));
    private ImageIcon barcodeColorIcon = new ImageIcon(makeIcon(DataLabel.barcodeColor));

    /*
    Instead of manipulating the color directly we manipulate this then set the valid part to the correct color.
    This avoids the issue of someone closing or canceling the color picker and passing a null color that then defaults
    the color icon and text color to wrong colors. There is likely a better way but this works better than some other
    options.
    TL;DR: This fixes a 'bug' that a color is set to null when the picker is closed without a new color picked.
    */
    private Color tempColor;


    //TODO: Add any needed logging into this clusterfuck.
    //TODO: Fix all margins on the window so elements are not touching the side.
    //TODO: Look to see if it is possible to resize the label window on the fly, correctly.
    //TODO: Hook up a spinner to the barcode scale. (needs to work as a double)
    public LabelMakerGUI()
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();

        disableHidden();

        for (Font font : fonts)
        {
            titleFontCombobox.addItem(font.getName());
            sizeFontCombobox.addItem(font.getName());
            priceFontCombobox.addItem(font.getName());
        }


        //Need to do this else the icons are missing when first opening the editor.
        colorTitle.setIcon(titleColorIcon);
        colorSize.setIcon(sizeColorIcon);
        colorPrice.setIcon(priceColorIcon);
        barcodeBackgroundColorButton.setIcon(barcodeBackgroundColorIcon);
        barcodeColorButton.setIcon(barcodeColorIcon);

        //Make sure to set all our defaults or things are going to get awkward.
        barcodeXpos.setValue(DataLabel.xposBarcode);
        barcodeYpos.setValue(DataLabel.yposBarcode);
        // Lets us use doubles in our spinner at .01 increments.
        barcodeScale.setModel(new SpinnerNumberModel(DataLabel.barcodeScale, null, null, 0.01d));

        titleXpos.setValue(DataLabel.xposTitle);
        titleYpos.setValue(DataLabel.yposTitle);

        priceXpos.setValue(DataLabel.xposPrice);
        priceYpos.setValue(DataLabel.yposPrice);

        sizeXpos.setValue(DataLabel.xposSize);
        sizeYpos.setValue(DataLabel.yposSize);

        labelWidth.setValue(DataLabel.labelWidth);
        labelHeight.setValue(DataLabel.labelHeight);

        titleTextFontSize.setValue(DataLabel.titleFontSize);
        sizeTextFontSize.setValue(DataLabel.sizeFontSize);
        priceTextFontSize.setValue(DataLabel.priceFontSize);

        //TODO: Better way to do this?
        if (DataLabel.displayBarcode)
        {
            barcodeDisplay.setSelected(true);
        }
        if (DataLabel.displayTitle)
        {
            titleDispaly.setSelected(true);
        }
        if (DataLabel.displaySize)
        {
            sizeDisplay.setSelected(true);
        }
        if (DataLabel.displayPrice)
        {
            priceDisplay.setSelected(true);
        }
        if (DataLabel.barcodeHasBackground)
        {
            barcodeBackground.setSelected(false);
        }

        //TODO: Do I need a new Listener for EVERY element? Can I recycle some of them to make this less big?
        print.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO: Add some logging here related to printing.
                Helper.printLabel();
            }
        });
        colorTitle.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tempColor = JColorChooser.showDialog(labelPane, "Title Color", DataLabel.titleColor);
                if (tempColor != null)
                {
                    titleColorIcon = new ImageIcon(makeIcon(tempColor));
                    colorTitle.setIcon(titleColorIcon);
                    DataLabel.titleColor = tempColor;
                    makePurdy();
                }
            }
        });
        colorSize.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tempColor = JColorChooser.showDialog(labelPane, "Size Color", DataLabel.titleColor);
                if (tempColor != null)
                {
                    sizeColorIcon = new ImageIcon(makeIcon(tempColor));
                    colorSize.setIcon(sizeColorIcon);
                    DataLabel.sizeColor = tempColor;
                    makePurdy();
                }
            }
        });
        colorPrice.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tempColor = JColorChooser.showDialog(labelPane, "Price Color", DataLabel.titleColor);
                if (tempColor != null)
                {
                    priceColorIcon = new ImageIcon(makeIcon(tempColor));
                    colorPrice.setIcon(priceColorIcon);
                    DataLabel.priceColor = tempColor;
                    makePurdy();
                }
            }
        });

        barcodeDisplay.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    DataLabel.displayBarcode = false;
                    makePurdy();
                    disableHidden();
                }
                else
                {
                    DataLabel.displayBarcode = true;
                    makePurdy();
                    disableHidden();
                }
            }
        });
        titleDispaly.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    DataLabel.displayTitle = false;
                    makePurdy();
                    disableHidden();
                }
                else
                {
                    DataLabel.displayTitle = true;
                    makePurdy();
                    disableHidden();
                }
            }
        });
        sizeDisplay.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    DataLabel.displaySize = false;
                    makePurdy();
                    disableHidden();
                }
                else
                {
                    DataLabel.displaySize = true;
                    makePurdy();
                    disableHidden();
                }
            }
        });
        priceDisplay.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    DataLabel.displayPrice = false;
                    makePurdy();
                    disableHidden();
                }
                else
                {
                    DataLabel.displayPrice = true;
                    makePurdy();
                    disableHidden();
                }
            }
        });
        titleFontCombobox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DataLabel.titleFont = titleFontCombobox.getSelectedItem().toString();
                makePurdy();
            }
        });
        sizeFontCombobox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DataLabel.sizeFont = sizeFontCombobox.getSelectedItem().toString();
                makePurdy();
            }
        });
        priceFontCombobox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DataLabel.priceFont = priceFontCombobox.getSelectedItem().toString();
                makePurdy();
            }
        });
        barcodeXpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.xposBarcode = (int) barcodeXpos.getValue();
                makePurdy();
            }
        });
        barcodeYpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.yposBarcode = (int) barcodeYpos.getValue();
                makePurdy();
            }
        });
        titleXpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.xposTitle = (int) titleXpos.getValue();
                makePurdy();
            }
        });
        titleYpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.yposTitle = (int) titleYpos.getValue();
                makePurdy();
            }
        });
        sizeXpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.xposSize = (int) sizeXpos.getValue();
                makePurdy();
            }
        });
        sizeYpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.yposSize = (int) sizeYpos.getValue();
                makePurdy();
            }
        });
        priceXpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.xposPrice = (int) priceXpos.getValue();
                makePurdy();
            }
        });
        priceYpos.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.yposPrice = (int) priceYpos.getValue();
                makePurdy();
            }
        });
        labelWidth.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.labelWidth = (int) labelWidth.getValue();
                makeSuperPurdy();
            }
        });
        labelHeight.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.labelHeight = (int) labelHeight.getValue();
                makeSuperPurdy();
            }
        });
        titleTextFontSize.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.titleFontSize = (int) titleTextFontSize.getValue();
                makePurdy();
            }
        });
        sizeTextFontSize.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.sizeFontSize = (int) sizeTextFontSize.getValue();
                makePurdy();
            }
        });
        priceTextFontSize.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.priceFontSize = (int) priceTextFontSize.getValue();
                makePurdy();
            }
        });
        barcodeColorButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tempColor = JColorChooser.showDialog(labelPane, "Barcode Color", DataLabel.barcodeColor);
                if (tempColor != null)
                {
                    barcodeColorIcon = new ImageIcon(makeIcon(tempColor));
                    barcodeColorButton.setIcon(barcodeColorIcon);
                    DataLabel.barcodeColor = tempColor;
                    makePurdy();
                }
            }
        });
        barcodeBackgroundColorButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tempColor = JColorChooser.showDialog(labelPane, "Barcode Background Color", DataLabel.barcodeBackgroundColor);
                if (tempColor != null)
                {
                    barcodeBackgroundColorIcon = new ImageIcon(makeIcon(tempColor));
                    barcodeBackgroundColorButton.setIcon(barcodeBackgroundColorIcon);
                    DataLabel.barcodeBackgroundColor = tempColor;
                    makePurdy();
                }
            }
        });
        barcodeBackground.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    DataLabel.barcodeHasBackground = true;
                    makePurdy();
                    disableHidden();
                }
                else
                {
                    DataLabel.barcodeHasBackground = false;
                    makePurdy();
                    disableHidden();
                }
            }
        });
        barcodeScale.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                DataLabel.barcodeScale = (double) barcodeScale.getValue();
                makePurdy();
            }
        });
    }

    public void makePurdy()
    {
        labelData.revalidate();
        labelPane.repaint();
    }

    //TODO: Not sure how to do this.
    public void makeSuperPurdy()
    {
        //contentPane.revalidate();
        contentPane.repaint();
        labelPane.repaint();
        labelExtras.repaint();
        editorPane.repaint();
    }

    private void disableHidden()
    {
        if (DataLabel.displayTitle)
        {
            titleTextFontSize.setEnabled(true);
            titleYpos.setEnabled(true);
            titleXpos.setEnabled(true);
            colorTitle.setEnabled(true);
            titleFontCombobox.setEnabled(true);
            titleLRText.setEnabled(true);
            titleUDText.setEnabled(true);
            titlePText.setEnabled(true);
            titleCText.setEnabled(true);
            titleFOText.setEnabled(true);
            titleTSText.setEnabled(true);
        }
        else
        {
            titleTextFontSize.setEnabled(false);
            titleYpos.setEnabled(false);
            titleXpos.setEnabled(false);
            colorTitle.setEnabled(false);
            titleFontCombobox.setEnabled(false);
            titleLRText.setEnabled(false);
            titleUDText.setEnabled(false);
            titlePText.setEnabled(false);
            titleCText.setEnabled(false);
            titleFOText.setEnabled(false);
            titleTSText.setEnabled(false);
        }

        if (DataLabel.displayPrice)
        {
            priceTextFontSize.setEnabled(true);
            priceYpos.setEnabled(true);
            priceXpos.setEnabled(true);
            colorPrice.setEnabled(true);
            priceFontCombobox.setEnabled(true);
            priceLRText.setEnabled(true);
            priceUDText.setEnabled(true);
            pricePText.setEnabled(true);
            priceCText.setEnabled(true);
            priceFOText.setEnabled(true);
            priceFSText.setEnabled(true);
        }
        else
        {
            priceTextFontSize.setEnabled(false);
            priceYpos.setEnabled(false);
            priceXpos.setEnabled(false);
            colorPrice.setEnabled(false);
            priceFontCombobox.setEnabled(false);
            priceLRText.setEnabled(false);
            priceUDText.setEnabled(false);
            pricePText.setEnabled(false);
            priceCText.setEnabled(false);
            priceFOText.setEnabled(false);
            priceFSText.setEnabled(false);
        }

        if (DataLabel.displaySize)
        {
            sizeTextFontSize.setEnabled(true);
            sizeYpos.setEnabled(true);
            sizeXpos.setEnabled(true);
            colorSize.setEnabled(true);
            sizeFontCombobox.setEnabled(true);
            sizeLRText.setEnabled(true);
            sizeUDText.setEnabled(true);
            sizePText.setEnabled(true);
            sizeCText.setEnabled(true);
            sizeFOText.setEnabled(true);
            sizeFSText.setEnabled(true);

        }
        else
        {
            sizeTextFontSize.setEnabled(false);
            sizeYpos.setEnabled(false);
            sizeXpos.setEnabled(false);
            colorSize.setEnabled(false);
            sizeFontCombobox.setEnabled(false);
            sizeLRText.setEnabled(false);
            sizeUDText.setEnabled(false);
            sizePText.setEnabled(false);
            sizeCText.setEnabled(false);
            sizeFOText.setEnabled(false);
            sizeFSText.setEnabled(false);
        }

        if (DataLabel.displayBarcode)
        {
            barcodeColorButton.setEnabled(true);
            barcodeYpos.setEnabled(true);
            barcodeXpos.setEnabled(true);
            barcodeSText.setEnabled(true);
            barcodeCOText.setEnabled(true);
            barcodePText.setEnabled(true);
            barcodeMCText.setEnabled(true);
            barcodeUDText.setEnabled(true);
            barcodeLRText.setEnabled(true);
            barcodeScale.setEnabled(true);
            barcodeBackground.setEnabled(true);
            barcodeRBText.setEnabled(true);
            if (DataLabel.barcodeHasBackground)
            {
                barcodeBackgroundColorButton.setEnabled(true);
                barcodeBCText.setEnabled(true);
            }
            else
            {
                barcodeBackgroundColorButton.setEnabled(false);
                barcodeBCText.setEnabled(false);
            }
        }
        else
        {
            barcodeColorButton.setEnabled(false);
            barcodeYpos.setEnabled(false);
            barcodeXpos.setEnabled(false);
            barcodeSText.setEnabled(false);
            barcodeBackgroundColorButton.setEnabled(false);
            barcodeCOText.setEnabled(false);
            barcodeBCText.setEnabled(false);
            barcodePText.setEnabled(false);
            barcodeMCText.setEnabled(false);
            barcodeUDText.setEnabled(false);
            barcodeLRText.setEnabled(false);
            barcodeScale.setEnabled(false);
            barcodeBackground.setEnabled(false);
            barcodeRBText.setEnabled(false);
            barcodeBackgroundColorButton.setEnabled(false);
            barcodeBCText.setEnabled(false);
        }
    }

    private BufferedImage makeIcon(Color colorIn)
    {
        BufferedImage image = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(colorIn);
        g2d.fillRect(0, 0, 15, 15);

        return image;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.setName("Label Editor");
        labelPane = new JPanel();
        labelPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        labelPane.setBackground(new Color(-1250068));
        labelPane.setOpaque(true);
        contentPane.add(labelPane, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Label Preview", TitledBorder.CENTER, TitledBorder.BELOW_TOP, this.$$$getFont$$$(null, -1, -1, labelPane.getFont())));
        lableImage = new DataLabel();
        labelPane.add(lableImage, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        editorPane = new JPanel();
        editorPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(12, 17, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(editorPane, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleLRText = new JLabel();
        titleLRText.setText("Left/Right (X)");
        editorPane.add(titleLRText, new com.intellij.uiDesigner.core.GridConstraints(7, 4, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleUDText = new JLabel();
        titleUDText.setText("Up/Down (Y)");
        editorPane.add(titleUDText, new com.intellij.uiDesigner.core.GridConstraints(8, 4, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeLRText = new JLabel();
        sizeLRText.setText("Left/Right (X)");
        editorPane.add(sizeLRText, new com.intellij.uiDesigner.core.GridConstraints(7, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeUDText = new JLabel();
        sizeUDText.setText("Up/Down (Y)");
        editorPane.add(sizeUDText, new com.intellij.uiDesigner.core.GridConstraints(8, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceLRText = new JLabel();
        priceLRText.setText("Left/Right (X)");
        editorPane.add(priceLRText, new com.intellij.uiDesigner.core.GridConstraints(7, 14, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        editorPane.add(separator1, new com.intellij.uiDesigner.core.GridConstraints(9, 13, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        editorPane.add(separator2, new com.intellij.uiDesigner.core.GridConstraints(5, 13, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator3 = new JSeparator();
        separator3.setOrientation(1);
        editorPane.add(separator3, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 10, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator4 = new JSeparator();
        separator4.setOrientation(1);
        editorPane.add(separator4, new com.intellij.uiDesigner.core.GridConstraints(2, 7, 10, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator5 = new JSeparator();
        separator5.setOrientation(1);
        editorPane.add(separator5, new com.intellij.uiDesigner.core.GridConstraints(2, 12, 10, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator6 = new JSeparator();
        editorPane.add(separator6, new com.intellij.uiDesigner.core.GridConstraints(5, 8, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator7 = new JSeparator();
        editorPane.add(separator7, new com.intellij.uiDesigner.core.GridConstraints(9, 8, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator8 = new JSeparator();
        editorPane.add(separator8, new com.intellij.uiDesigner.core.GridConstraints(9, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator9 = new JSeparator();
        editorPane.add(separator9, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator10 = new JSeparator();
        editorPane.add(separator10, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator11 = new JSeparator();
        editorPane.add(separator11, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        sizeTextFontSize = new JSpinner();
        editorPane.add(sizeTextFontSize, new com.intellij.uiDesigner.core.GridConstraints(4, 11, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleTextFontSize = new JSpinner();
        editorPane.add(titleTextFontSize, new com.intellij.uiDesigner.core.GridConstraints(4, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceTextFontSize = new JSpinner();
        editorPane.add(priceTextFontSize, new com.intellij.uiDesigner.core.GridConstraints(4, 16, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Barcode:");
        editorPane.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Size:");
        editorPane.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Price:");
        editorPane.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator12 = new JSeparator();
        editorPane.add(separator12, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 17, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator13 = new JSeparator();
        separator13.setOrientation(1);
        editorPane.add(separator13, new com.intellij.uiDesigner.core.GridConstraints(0, 12, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator14 = new JSeparator();
        separator14.setOrientation(1);
        editorPane.add(separator14, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator15 = new JSeparator();
        separator15.setOrientation(1);
        editorPane.add(separator15, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        titleXpos = new JSpinner();
        editorPane.add(titleXpos, new com.intellij.uiDesigner.core.GridConstraints(7, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleYpos = new JSpinner();
        editorPane.add(titleYpos, new com.intellij.uiDesigner.core.GridConstraints(8, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeXpos = new JSpinner();
        editorPane.add(sizeXpos, new com.intellij.uiDesigner.core.GridConstraints(7, 11, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeYpos = new JSpinner();
        editorPane.add(sizeYpos, new com.intellij.uiDesigner.core.GridConstraints(8, 11, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeXpos = new JSpinner();
        editorPane.add(barcodeXpos, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeYpos = new JSpinner();
        editorPane.add(barcodeYpos, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceXpos = new JSpinner();
        editorPane.add(priceXpos, new com.intellij.uiDesigner.core.GridConstraints(7, 16, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceYpos = new JSpinner();
        editorPane.add(priceYpos, new com.intellij.uiDesigner.core.GridConstraints(8, 16, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleFontCombobox = new JComboBox();
        editorPane.add(titleFontCombobox, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        colorTitle = new JButton();
        colorTitle.setHorizontalTextPosition(0);
        colorTitle.setText("");
        editorPane.add(colorTitle, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, 15), null, new Dimension(15, 15), 0, false));
        sizeFontCombobox = new JComboBox();
        editorPane.add(sizeFontCombobox, new com.intellij.uiDesigner.core.GridConstraints(3, 8, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        colorSize = new JButton();
        colorSize.setText("");
        editorPane.add(colorSize, new com.intellij.uiDesigner.core.GridConstraints(4, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, 15), null, new Dimension(15, 15), 0, false));
        priceFontCombobox = new JComboBox();
        editorPane.add(priceFontCombobox, new com.intellij.uiDesigner.core.GridConstraints(3, 13, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(200, -1), 0, false));
        colorPrice = new JButton();
        colorPrice.setText("");
        editorPane.add(colorPrice, new com.intellij.uiDesigner.core.GridConstraints(4, 14, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, 15), null, new Dimension(15, 15), 0, false));
        barcodeLRText = new JLabel();
        barcodeLRText.setText("Left/Right (X):");
        editorPane.add(barcodeLRText, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeUDText = new JLabel();
        barcodeUDText.setText("Up/Down (Y)");
        editorPane.add(barcodeUDText, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeSText = new JLabel();
        barcodeSText.setText("Scale (Multiplier)");
        editorPane.add(barcodeSText, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeScale = new JSpinner();
        editorPane.add(barcodeScale, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeColorButton = new JButton();
        barcodeColorButton.setText("");
        editorPane.add(barcodeColorButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, 15), null, new Dimension(15, 15), 0, false));
        barcodeBackgroundColorButton = new JButton();
        barcodeBackgroundColorButton.setText("");
        editorPane.add(barcodeBackgroundColorButton, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(15, 15), null, new Dimension(15, 15), 0, false));
        priceUDText = new JLabel();
        priceUDText.setText("Up/Down (Y)");
        editorPane.add(priceUDText, new com.intellij.uiDesigner.core.GridConstraints(8, 14, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeMCText = new JLabel();
        barcodeMCText.setText("Main Color");
        editorPane.add(barcodeMCText, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeBCText = new JLabel();
        barcodeBCText.setText("Background Color");
        editorPane.add(barcodeBCText, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleFOText = new JLabel();
        titleFOText.setText("Font Options:");
        editorPane.add(titleFOText, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titlePText = new JLabel();
        titlePText.setText("Position:");
        editorPane.add(titlePText, new com.intellij.uiDesigner.core.GridConstraints(6, 3, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleCText = new JLabel();
        titleCText.setText("Color:");
        editorPane.add(titleCText, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setAlignmentX(1.0f);
        label4.setText("Title:");
        editorPane.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleDispaly = new JCheckBox();
        titleDispaly.setHorizontalTextPosition(10);
        titleDispaly.setText("");
        editorPane.add(titleDispaly, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleTSText = new JLabel();
        titleTSText.setText("Size:");
        editorPane.add(titleTSText, new com.intellij.uiDesigner.core.GridConstraints(4, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodePText = new JLabel();
        barcodePText.setText("Position:");
        editorPane.add(barcodePText, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeDisplay = new JCheckBox();
        barcodeDisplay.setHorizontalTextPosition(10);
        barcodeDisplay.setText("");
        editorPane.add(barcodeDisplay, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeCOText = new JLabel();
        barcodeCOText.setText("Barcode Color Options:");
        editorPane.add(barcodeCOText, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizePText = new JLabel();
        sizePText.setText("Position:");
        editorPane.add(sizePText, new com.intellij.uiDesigner.core.GridConstraints(6, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pricePText = new JLabel();
        pricePText.setText("Position:");
        editorPane.add(pricePText, new com.intellij.uiDesigner.core.GridConstraints(6, 14, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeDisplay = new JCheckBox();
        sizeDisplay.setHorizontalTextPosition(10);
        sizeDisplay.setText("");
        editorPane.add(sizeDisplay, new com.intellij.uiDesigner.core.GridConstraints(0, 9, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeCText = new JLabel();
        sizeCText.setText("Color:");
        editorPane.add(sizeCText, new com.intellij.uiDesigner.core.GridConstraints(4, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeFSText = new JLabel();
        sizeFSText.setText("Size:");
        editorPane.add(sizeFSText, new com.intellij.uiDesigner.core.GridConstraints(4, 10, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceFSText = new JLabel();
        priceFSText.setText("Size:");
        editorPane.add(priceFSText, new com.intellij.uiDesigner.core.GridConstraints(4, 15, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeFOText = new JLabel();
        sizeFOText.setText("Font Options:");
        editorPane.add(sizeFOText, new com.intellij.uiDesigner.core.GridConstraints(2, 8, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceFOText = new JLabel();
        priceFOText.setText("Font Options:");
        editorPane.add(priceFOText, new com.intellij.uiDesigner.core.GridConstraints(2, 13, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceDisplay = new JCheckBox();
        priceDisplay.setHorizontalTextPosition(10);
        priceDisplay.setText("");
        editorPane.add(priceDisplay, new com.intellij.uiDesigner.core.GridConstraints(0, 14, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceCText = new JLabel();
        priceCText.setText("Color:");
        editorPane.add(priceCText, new com.intellij.uiDesigner.core.GridConstraints(4, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeRBText = new JLabel();
        barcodeRBText.setText("Remove Background");
        editorPane.add(barcodeRBText, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barcodeBackground = new JCheckBox();
        barcodeBackground.setText("");
        editorPane.add(barcodeBackground, new com.intellij.uiDesigner.core.GridConstraints(11, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JToolBar toolBar1 = new JToolBar();
        contentPane.add(toolBar1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        cursorPos = new JButton();
        cursorPos.setText("Enable CursorPos");
        toolBar1.add(cursorPos);
        print = new JButton();
        print.setText("Print");
        toolBar1.add(print);
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        contentPane.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        contentPane.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelExtras = new JPanel();
        labelExtras.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(labelExtras, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(300, 300), 0, false));
        labelWidth = new JSpinner();
        labelExtras.add(labelWidth, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelHeight = new JSpinner();
        labelExtras.add(labelHeight, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Label");
        labelExtras.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Label");
        labelExtras.add(label6, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont)
    {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {resultName = currentFont.getName();}
        else
        {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {resultName = fontName;}
            else {resultName = currentFont.getName();}
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() { return contentPane; }
}
