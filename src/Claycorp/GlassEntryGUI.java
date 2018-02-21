package Claycorp;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private final List<DataSettings> settings;

    private JButton     okButton;
    private JTextField  sizeTextBox1;
    private JTextField  sizeTextBox2;
    private JComboBox   companySelect;
    private JTextField  nameOfGlassTextBox;
    private JLabel      nameOfGlassText;
    private JButton     clearButton;
    private JPanel      newGlassEntry;
    private JTextArea   console;
    private JTextField  pricePaidTextBox;
    private JTextField  partIDTextBox;
    private JButton     saveButton;
    private JTable      glassTable;
    private JTabbedPane entryPane;
    private JTabbedPane tablePane;
    private JButton     settingsButton;
    private JButton     saveButton1;
    private JButton     loadButton;
    private JButton     TABLESSUCKButton;

    //todo: Refactor this to EntryGUI
    //TODO: Add Console logging where needed based on settings, Also make any console printing save to a log file.
    //TODO: Add save on exit prompt (some how).
    public GlassEntryGUI(final Path databaseFile, final Path settingsFile)
    {
        db = JsonHelper.loadDatabase(databaseFile);
        settings = JsonHelper.loadSettings(settingsFile);

        //TODO: Someday in the future all the junk in this can be done via the console and save to file.
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
        //todo: Decide if this is really needed... If so, make sure to reset everything to default.
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

        //TODO: Perhaps glass should have it's own JSON file separate from everything else as it will change the most and has the most detail?
        // Saves all data to the JSON
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DataGlassSheet obj = new DataGlassSheet();

                try
                {
                    //TODO: Is there a better way to handle this checking? Then we can can catch all errors in one shot instead of only the last error?
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
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage(), "Number Error", JOptionPane.ERROR_MESSAGE);
                    // todo: Add in log file shit and set console output to only work if enabled.
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

        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                console.append("\nTEST");
                // Clear & fill because db is final.
                db.clear();
                db.addAll(JsonHelper.loadDatabase(databaseFile));
                // The underlying data has changed, so the UI must be refreshed
                glassTable.updateUI();
            }
        });

        glassTable.setModel(new GlassTableModel(db));

        // Actually show the window
        JFrame frame = new JFrame("Glass Entry Form");
        frame.setContentPane(newGlassEntry);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //TODO: Remove this after all is done, its just a test button...
        TABLESSUCKButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JsonHelper.loadSettings(settingsFile);
            }
        });

        //TODO: Make sure to load new
        settingsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SettingsGUIDialog dialog = new SettingsGUIDialog(settingsFile);
                dialog.pack();
                dialog.setVisible(true);
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
        newGlassEntry.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(5, 5, 5, 5), -1, -1));
        newGlassEntry.setForeground(new Color(-6598469));
        newGlassEntry.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "What shit do I own?"));
        entryPane = new JTabbedPane();
        newGlassEntry.add(entryPane, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 0, 0, 0), -1, -1));
        entryPane.addTab("Glass Entry", panel1);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 4, new Insets(0, 3, 3, 3), -1, -1));
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
        label4.setText("Manufacturer");
        panel2.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        partIDTextBox = new JTextField();
        partIDTextBox.setText("");
        panel2.add(partIDTextBox,
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
        pricePaidTextBox = new JTextField();
        pricePaidTextBox.setText("");
        pricePaidTextBox.setToolTipText("Price paid for this sheet of glass.");
        panel2.add(pricePaidTextBox,
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
        nameOfGlassTextBox = new JTextField();
        panel2.add(nameOfGlassTextBox,
                new com.intellij.uiDesigner.core.GridConstraints(1,
                        1,
                        1,
                        3,
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
        panel2.add(companySelect, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("x");
        panel2.add(label5,
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
        clearButton = new JButton();
        clearButton.setText("Clear");
        clearButton.setToolTipText("Clears all data from feilds.");
        panel2.add(clearButton,
                new com.intellij.uiDesigner.core.GridConstraints(5,
                        1,
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
        entryPane.addTab("Basic Item Entry", panel3);
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
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
        tablePane = new JTabbedPane();
        newGlassEntry.add(tablePane,
                new com.intellij.uiDesigner.core.GridConstraints(1,
                        0,
                        3,
                        1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        tablePane.addTab("Untitled", scrollPane1);
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
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(30, -1),
                        new Dimension(30, -1),
                        new Dimension(768, -1),
                        0,
                        false));
        scrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Console", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4514048)));
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
