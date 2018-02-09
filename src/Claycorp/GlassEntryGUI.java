package Claycorp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


public class GlassEntryGUI
{
    private final List<DataGlassSheet> db;

    private JButton okButton;
    private JTextField sizeTextBox1;
    private JTextField sizeTextBox2;
    private JComboBox companySelect;
    private JTextField nameOfGlassTextBox;
    private JLabel nameOfGlassText;
    private JButton clearButton;
    private JPanel newGlassEntry;
    private JTextArea console;
    private JTextField pricePaidTextBox;
    private JTextField partIDTextBox;
    private JButton saveButton;
    private JTable glassTable;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JButton settingsButton;
    private JButton saveButton1;
    private JButton loadButton;
    private JButton TABLESSUCKButton;

    public GlassEntryGUI(final Path databaseFile)
    {
        db = JsonHelper.loadDatabase(databaseFile);

        //TODO: Someday in the future all the junk in this can be done via a log window and file.
        //Adds information to the console to check the data input. Isn't really needed TBH...
        okButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                int size1 = Helper.regexNumberCheck(sizeTextBox1.getText().trim());
                int size2 = Helper.regexNumberCheck(sizeTextBox2.getText().trim());
                BigDecimal paid = Helper.regexCompareMoney(pricePaidTextBox.getText().trim());
                int totalArea = size1 * size2;
                BigDecimal ppi = Helper.calculateCostPerInch(paid, totalArea);
                console.append("\nS1-" + size1 + " S2-" + size2 + " TA-" + totalArea + " PP-" + paid + " PPI-" + ppi + " ");
            }
        });
        //TODO: In the future the cancel button will close the glass entry window rater than clear current data.
        //Clears all text fields and console.
        clearButton.addActionListener(new ActionListener()
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

        //TODO: Perhaps glass should have it's own JSON file seperate from everything else as it will change the most and has the most detail?
        // Saves all datafields to the JSON
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DataGlassSheet obj = new DataGlassSheet();

                try
                {
                    int size1 = Helper.regexNumberCheck(sizeTextBox1.getText().trim());
                    int size2 = Helper.regexNumberCheck(sizeTextBox2.getText().trim());
                    BigDecimal paid = Helper.regexCompareMoney(pricePaidTextBox.getText().trim());
                    int totalArea = size1 * size2;
                    BigDecimal ppi = Helper.calculateCostPerInch(paid, totalArea);

                    obj.UUID = UUID.randomUUID();
                    obj.Manufacturer = companySelect.getSelectedItem().toString();
                    obj.ItemID = partIDTextBox.getText();
                    obj.NameOfGlass = nameOfGlassTextBox.getText();
                    obj.PricePaid = pricePaidTextBox.getText();
                    obj.PricePerInch = ppi;
                    obj.TotalArea = size1 * size2;
                    obj.Size1 = size1;
                    obj.Size2 = size2;
                }
                catch (NumberFormatException ex)
                {
                    // todo: replace with dialog box
                    console.append("\n" + ex.getMessage());
                    return;
                }

                // No loading here!
                db.add(obj);
                // SAVE SAVE SAVE!
                JsonHelper.saveDatabase(databaseFile, db);
                // The underlying data has changed, so the UI must be refreshed
                glassTable.updateUI();
            }
        });

        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Clear & fill because db is final.
                db.clear();
                db.addAll(JsonHelper.loadDatabase(databaseFile));
                // The underlying data has changed, so the UI must be refreshed
                glassTable.updateUI();
            }
        });


        /*
        glass.addColumn("Manufacturer");
        glass.addColumn("Item ID");
        glass.addColumn("Name Of Glass");
        glass.addColumn("Price Paid");
        glass.addColumn("Price Per Inch");
        glass.addColumn("Total Area");
        glass.addColumn("UUID");
        */

        glassTable.setModel(new GlassTableModel(db));

        // Actually show the window
        JFrame frame = new JFrame("Glass Entry Form");
        frame.setContentPane(newGlassEntry);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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
        newGlassEntry.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        newGlassEntry.setForeground(new Color(-6598469));
        newGlassEntry.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "What shit do I own?"));
        tabbedPane1 = new JTabbedPane();
        newGlassEntry.add(tabbedPane1,
                new com.intellij.uiDesigner.core.GridConstraints(1,
                        1,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Glass Entry", panel1);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2,
                new com.intellij.uiDesigner.core.GridConstraints(0,
                        0,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JLabel label1 = new JLabel();
        label1.setText("Part ID");
        panel2.add(label1,
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
        sizeTextBox2 = new JTextField();
        panel2.add(sizeTextBox2,
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
        sizeTextBox1 = new JTextField();
        sizeTextBox1.setText("");
        panel2.add(sizeTextBox1,
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
        final JLabel label2 = new JLabel();
        label2.setText("Size");
        panel2.add(label2,
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
        final JLabel label3 = new JLabel();
        label3.setText("Price Paid");
        panel2.add(label3,
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
        nameOfGlassText = new JLabel();
        nameOfGlassText.setText("Name of Glass");
        panel2.add(nameOfGlassText,
                new com.intellij.uiDesigner.core.GridConstraints(1,
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
        final JLabel label4 = new JLabel();
        label4.setText("x");
        panel2.add(label4,
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
        final JLabel label5 = new JLabel();
        label5.setText("Manufacturer");
        panel2.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        partIDTextBox = new JTextField();
        partIDTextBox.setText("");
        panel2.add(partIDTextBox,
                new com.intellij.uiDesigner.core.GridConstraints(4,
                        1,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(200, -1),
                        new Dimension(150, -1),
                        new Dimension(400, -1),
                        0,
                        false));
        pricePaidTextBox = new JTextField();
        pricePaidTextBox.setText("");
        pricePaidTextBox.setToolTipText("Price paid for this sheet of glass.");
        panel2.add(pricePaidTextBox,
                new com.intellij.uiDesigner.core.GridConstraints(2,
                        1,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(45, -1),
                        null,
                        new Dimension(45, -1),
                        0,
                        false));
        nameOfGlassTextBox = new JTextField();
        panel2.add(nameOfGlassTextBox,
                new com.intellij.uiDesigner.core.GridConstraints(1,
                        1,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(150, -1),
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
        panel2.add(companySelect, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        clearButton = new JButton();
        clearButton.setText("Clear");
        clearButton.setToolTipText("Clears all data from feilds.");
        panel2.add(clearButton,
                new com.intellij.uiDesigner.core.GridConstraints(5,
                        2,
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
        saveButton = new JButton();
        saveButton.setText("Save to JSON");
        saveButton.setToolTipText("Saves the current entry to the current JSON file.");
        panel2.add(saveButton,
                new com.intellij.uiDesigner.core.GridConstraints(5,
                        3,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        new Dimension(150, -1),
                        0,
                        false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Basic Item Entry", panel3);
        final JToolBar toolBar1 = new JToolBar();
        newGlassEntry.add(toolBar1,
                new com.intellij.uiDesigner.core.GridConstraints(0,
                        0,
                        1,
                        2,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        new Dimension(-1, 20),
                        null,
                        0,
                        false));
        saveButton1 = new JButton();
        saveButton1.setText("Save");
        toolBar1.add(saveButton1);
        loadButton = new JButton();
        loadButton.setText("Load");
        toolBar1.add(loadButton);
        settingsButton = new JButton();
        settingsButton.setText("Settings");
        toolBar1.add(settingsButton);
        TABLESSUCKButton = new JButton();
        TABLESSUCKButton.setText("TABLES SUCK");
        toolBar1.add(TABLESSUCKButton);
        tabbedPane2 = new JTabbedPane();
        newGlassEntry.add(tabbedPane2,
                new com.intellij.uiDesigner.core.GridConstraints(1,
                        0,
                        3,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        new Dimension(678, 200),
                        null,
                        0,
                        false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        tabbedPane2.addTab("Untitled", scrollPane1);
        glassTable = new JTable();
        glassTable.setAutoCreateColumnsFromModel(true);
        glassTable.setAutoCreateRowSorter(true);
        glassTable.setColumnSelectionAllowed(true);
        scrollPane1.setViewportView(glassTable);
        final JScrollPane scrollPane2 = new JScrollPane();
        newGlassEntry.add(scrollPane2,
                new com.intellij.uiDesigner.core.GridConstraints(2,
                        1,
                        1,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        new Dimension(300, -1),
                        null,
                        null,
                        0,
                        false));
        console = new JTextArea();
        console.setDragEnabled(false);
        console.setEditable(false);
        console.setEnabled(true);
        console.setToolTipText("Information about what is going on!");
        scrollPane2.setViewportView(console);
        okButton = new JButton();
        okButton.setText("Ok");
        newGlassEntry.add(okButton,
                new com.intellij.uiDesigner.core.GridConstraints(3,
                        1,
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
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() { return newGlassEntry; }
}
