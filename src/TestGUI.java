
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class TestGUI
{
    public JButton    okButton;
    public JTextField sizeTextBox1;
    public JTextField sizeTextBox2;
    public JLabel     sizeLable;
    public JLabel     xLable;
    public JComboBox  companySelect;
    public JTextField nameOfGlassTextBox;
    public JLabel     nameOfGlassText;
    public JButton    cancelButton;
    public JPanel     newGlassEntry;
    public JTextArea  console;
    public JTextField pricePaidTextBox;
    public JTextField partIDTextBox;
    public JLabel     price;
    public JButton    saveButton;

    private TestGUI()
    {

        okButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.helper.regexNumberCheck1(sizeTextBox1.getText().trim());
                Main.helper.regexNumberCheck2(sizeTextBox2.getText().trim());
                Main.helper.regexCompareMoney(pricePaidTextBox.getText().trim());
                Main.helper.calculateArea();
                Main.helper.calculateCostPerInch();

                Main.GUIINSTANCE.console.append("~A1-" + Main.helper.size1 + " A2-" + Main.helper.size2 + " TA-" + Main.helper.totalArea + " PP-" + Main.helper.pricePaid + " PPI-" + Main.helper.pricePerIN + " ");
            }
        });
        //Listener for cancel button, Clears all text fields and console.
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sizeTextBox1.setText("");
                sizeTextBox2.setText("");
                nameOfGlassTextBox.setText("");
                console.setText("");
            }
        });

        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.helper.UUID++;
                JsonHelper obj = new JsonHelper();

                try (FileWriter writerFile = new FileWriter("databaseFile.json", true))
                {
                    Main.gson.toJson(obj);
                    /*
                    Main.root.addProperty("Company", companySelect.getSelectedItem().toString());
                    Main.root.addProperty("PricePaid", pricePaidTextBox.getText().trim());
                    Main.root.addProperty("CostPerInch", pricePerIN);
                    Main.root.addProperty("Length", sizeTextBox1.getText().trim());
                    Main.root.addProperty("With", sizeTextBox2.getText().trim());
                    Main.root.addProperty("Area", totalArea);
                    Main.root.addProperty("UUID", UUID);
                    Main.gson.toJson(Main.root, writerFile);
*/
                }
                catch (IOException i)
                {
                    i.printStackTrace();
                }

            }
        });

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
        newGlassEntry = new JPanel();
        newGlassEntry.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 7, new Insets(0, 0, 0, 0), -1, -1));
        newGlassEntry.setForeground(new Color(-6598469));
        newGlassEntry.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "New Glass Entry"));
        nameOfGlassText = new JLabel();
        nameOfGlassText.setText("Name of Glass");
        newGlassEntry.add(nameOfGlassText,
                new com.intellij.uiDesigner.core.GridConstraints(1,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        companySelect = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Spectrum");
        defaultComboBoxModel1.addElement("Uroboros");
        defaultComboBoxModel1.addElement("Kokomo");
        defaultComboBoxModel1.addElement("Armstrong");
        defaultComboBoxModel1.addElement("Youghiogheny");
        companySelect.setModel(defaultComboBoxModel1);
        companySelect.setToolTipText("Company that manufactured the glass.");
        newGlassEntry.add(companySelect,
                new com.intellij.uiDesigner.core.GridConstraints(0,
                        0,
                        1,
                        4,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        new Dimension(300, -1),
                        0,
                        false));
        nameOfGlassTextBox = new JTextField();
        newGlassEntry.add(nameOfGlassTextBox,
                new com.intellij.uiDesigner.core.GridConstraints(1,
                        1,
                        1,
                        3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
                        null,
                        0,
                        false));
        pricePaidTextBox = new JTextField();
        pricePaidTextBox.setText("");
        pricePaidTextBox.setToolTipText("Price paid for this sheet of glass.");
        newGlassEntry.add(pricePaidTextBox,
                new com.intellij.uiDesigner.core.GridConstraints(2,
                        1,
                        1,
                        3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(45, -1),
                        null,
                        new Dimension(45, -1),
                        0,
                        false));
        price = new JLabel();
        price.setText("Price Paid");
        newGlassEntry.add(price,
                new com.intellij.uiDesigner.core.GridConstraints(2,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        sizeTextBox1 = new JTextField();
        sizeTextBox1.setText("");
        newGlassEntry.add(sizeTextBox1,
                new com.intellij.uiDesigner.core.GridConstraints(3,
                        1,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(100, -1),
                        null,
                        new Dimension(100, -1),
                        0,
                        false));
        sizeLable = new JLabel();
        sizeLable.setText("Size");
        newGlassEntry.add(sizeLable,
                new com.intellij.uiDesigner.core.GridConstraints(3,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("Clears all data from feilds.");
        newGlassEntry.add(cancelButton,
                new com.intellij.uiDesigner.core.GridConstraints(4,
                        6,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        new Dimension(100, -1),
                        0,
                        false));
        okButton = new JButton();
        okButton.setText("Ok");
        newGlassEntry.add(okButton,
                new com.intellij.uiDesigner.core.GridConstraints(4,
                        4,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        new Dimension(100, -1),
                        0,
                        false));
        partIDTextBox = new JTextField();
        partIDTextBox.setText("");
        newGlassEntry.add(partIDTextBox,
                new com.intellij.uiDesigner.core.GridConstraints(4,
                        1,
                        1,
                        3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(200, -1),
                        new Dimension(150, -1),
                        new Dimension(400, -1),
                        0,
                        false));
        final JLabel label1 = new JLabel();
        label1.setText("Part ID");
        newGlassEntry.add(label1,
                new com.intellij.uiDesigner.core.GridConstraints(4,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        xLable = new JLabel();
        xLable.setText("x");
        newGlassEntry.add(xLable,
                new com.intellij.uiDesigner.core.GridConstraints(3,
                        2,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        new Dimension(10, 10),
                        0,
                        false));
        sizeTextBox2 = new JTextField();
        newGlassEntry.add(sizeTextBox2,
                new com.intellij.uiDesigner.core.GridConstraints(3,
                        3,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(100, -1),
                        null,
                        new Dimension(100, -1),
                        0,
                        false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        newGlassEntry.add(spacer1,
                new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        newGlassEntry.add(spacer2,
                new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        newGlassEntry.add(spacer3,
                new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        newGlassEntry.add(scrollPane1,
                new com.intellij.uiDesigner.core.GridConstraints(5,
                        0,
                        1,
                        7,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        console = new JTextArea();
        console.setEditable(false);
        console.setToolTipText("Information about what is going on!");
        scrollPane1.setViewportView(console);
        saveButton = new JButton();
        saveButton.setText("Save to JSON");
        saveButton.setToolTipText("Saves the current entry to the current JSON file.");
        newGlassEntry.add(saveButton,
                new com.intellij.uiDesigner.core.GridConstraints(4,
                        5,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        new Dimension(100, -1),
                        0,
                        false));
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$() { return newGlassEntry; }
}
