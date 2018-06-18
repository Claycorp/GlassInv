package Claycorp;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


public class EntryGUI
{
    private final List<DataGlassSheet> db;
    private DataSettings settings;

    private JTextField sizeTextBox1;
    private JTextField sizeTextBox2;
    private JComboBox companySelect;
    private JTextField nameOfGlassTextBox;
    private JLabel nameOfGlassText;
    private JButton clearButton;
    private JPanel newGlassEntry;
    private JTextPane console;
    private JTextField pricePaidTextBox;
    private JTextField partIDTextBox;
    private JButton saveButton;
    private JTable glassTable;
    private JTabbedPane entryPane;
    private JTabbedPane tablePane;
    private JButton settingsButton;
    private JButton saveButton1;
    private JButton loadButton;
    private JScrollPane consolePane;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JScrollPane ScrollPane;
    private JButton labelEditor;
    private JButton deleteButton;
    private JButton edit;
    private JButton printButton;
    private JPanel labelPreview;

    //TODO: Fix table sorting being incorrect.
    //TODO: Add save on exit prompt (some how).
    //TODO: Add printing based off the setup label template.
    EntryGUI(final Path databaseFile, final Path settingsFile)
    {
        db = JsonHelper.loadDatabase(databaseFile);
        settings = JsonHelper.loadSettings(settingsFile);

        //Set the console to work with HTML
        console.setContentType("text/html");
        //Some start text and the all important first HTML tag. THIS MUST HAVE <html> OR EVERYTHING IS FUCKED.
        console.setText("<html>Welcome to The List");

        //TODO: This is being done to stop the columns from being reorderd and messing up what the label data gets, It is possible to fix but this will do...
        glassTable.getTableHeader().setReorderingAllowed(false);

        uiUpdate(settingsFile);

        //Clears all text fields and console.
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cleanEntry();
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
                    Logger.log("\n" + ex.getMessage(), 2);
                    return;
                }

                // No loading here!
                db.add(obj);
                // SAVE SAVE SAVE!
                JsonHelper.saveDatabase(databaseFile, db);
                // The underlying data has changed, so the UI must be refreshed
                glassTable.updateUI();
                cleanEntry();
            }
        });

        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Logger.log("Loading: " + databaseFile, 0);
                // Clear & fill because db is final.
                db.clear();
                db.addAll(JsonHelper.loadDatabase(databaseFile));
                // The underlying data has changed, so the UI must be refreshed
                glassTable.updateUI();
            }
        });

        glassTable.setModel(new GlassTableModel(db));

        // Actually show the window
        JFrame frame = new JFrame("Entry Form");
        frame.setContentPane(newGlassEntry);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Open the settings dialog
        settingsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SettingsDialogGUI dialog = new SettingsDialogGUI(settings, settingsFile);
                dialog.setTitle("Settings");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        //TODO: How to check this correctly so that if something is missing it will turn it off... else if?
        //TODO: How to size? Our size isn't saved in our table but is in the data somewhere. We need to liberate it!
        labelEditor.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int row = glassTable.getSelectedRow();

                if (row > -1)
                {
                    DataLabel.title = glassTable.getValueAt(row, 2).toString();
                    DataLabel.price = glassTable.getValueAt(row, 4).toString();

                    if (DataLabel.title.isEmpty())
                    {
                        if (JOptionPane.showConfirmDialog(newGlassEntry, "The title is empty, are you sure you want to continue?", "Missing Title", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0)
                        {
                            DataLabel.title = "";
                            DataLabel.displayTitle = false;

                            if (DataLabel.price.isEmpty())
                            {

                                if (JOptionPane.showConfirmDialog(newGlassEntry, "The price is empty, are you sure you want to continue?", "Missing Price", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0)
                                {
                                    DataLabel.price = "";
                                    DataLabel.displayPrice = false;
                                }
                            }
                        }
                    }
                    else
                    {
                        JFrame frame1 = new JFrame("Label Editor");
                        frame1.setContentPane(new LabelMakerGUI().contentPane);
                        frame1.setTitle("Label Editor");
                        frame1.pack();
                        frame1.setVisible(true);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(newGlassEntry, "You can't make a label from nothing, Please select a row first!", "OOF", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Get the selected row.
                int row = glassTable.getSelectedRow();
                // Make sure the selected row exists. 0 is valid thus we need to look for anything more than -1 to continue.
                if (row > -1)
                {
                    // We can now safely get values from the table.
                    Object title = glassTable.getValueAt(row, 0);
                    Object uuid = glassTable.getValueAt(row, 6);

                    // Lets make sure the person picked the correct line before we shitcan this entry.
                    if (JOptionPane.showInternalConfirmDialog(newGlassEntry, "You are about to delete \"" + title + " " + uuid + "\" Are you sure?", "Confirm Delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == 0)
                    {
                        // If they accept their fate fuck that shit up.
                               db.remove(row);
                               JsonHelper.saveDatabase(databaseFile, db);
                               db.clear();
                               db.addAll(JsonHelper.loadDatabase(databaseFile));
                               glassTable.updateUI();
                    }
                }
                // If they think they can some how manage to delete all of nothing lets make sure they know... #StupidProof.
                else
                {
                    JOptionPane.showMessageDialog(newGlassEntry, "You can't delete nothing, Please select a row first!", "OOF", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        edit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Helper.brotherLabelPrint(settingsFile);
            }
        });
        printButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    //TODO: I do want to save labels at some point and must to print, This only works if the label editor is open!
                    ImageIO.write(DataLabel.getLabel(), "PNG", new File("test.png"));
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void uiUpdate(Path settingsFile)
    {
        JsonHelper.loadSettings(settingsFile);

        //Show the console window if selected.
        consolePane.setVisible(settings.showConsole);

        //Go through our settings for omniBoxOptions and add all the options from the settings file to the GUI.
        for (Object option : settings.omniBoxOptions)
        {
            companySelect.addItem(option);
        }

        //Update the whole thing. MAKE SURE TO DO ANY CHANGES TO THE GUI ABOVE THIS OR IT WILL NEVER UPDATE CORRECTLY!!
        newGlassEntry.updateUI();
    }

    public void consoleAppend(String input) throws IOException, BadLocationException
    {
        HTMLDocument doc = (HTMLDocument) console.getStyledDocument();

        doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), input);
    }

    public void cleanEntry()
    {
        sizeTextBox1.setText("");
        sizeTextBox2.setText("");
        nameOfGlassTextBox.setText("");
        pricePaidTextBox.setText("");
        partIDTextBox.setText("");
    }
    public void getSelectedRow()
    {
        glassTable.getSelectedRow();
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
        newGlassEntry.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(5, 5, 5, 5), -1, -1));
        newGlassEntry.setForeground(new Color(-6598469));
        newGlassEntry.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "What shit do I own?"));
        entryPane = new JTabbedPane();
        newGlassEntry.add(entryPane, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 0, 0, 0), -1, -1));
        entryPane.addTab("Glass Entry", panel1);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 4, new Insets(0, 3, 3, 3), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Part ID");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sizeTextBox1 = new JTextField();
        sizeTextBox1.setText("");
        panel2.add(sizeTextBox1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, -1), null, new Dimension(100, -1), 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Size");
        panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Price Paid");
        panel2.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameOfGlassText = new JLabel();
        nameOfGlassText.setText("Name of Glass");
        panel2.add(nameOfGlassText, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Manufacturer");
        panel2.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
        partIDTextBox = new JTextField();
        partIDTextBox.setText("");
        panel2.add(partIDTextBox, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(150, -1), new Dimension(400, -1), 0, false));
        pricePaidTextBox = new JTextField();
        pricePaidTextBox.setText("");
        pricePaidTextBox.setToolTipText("Price paid for this sheet of glass.");
        panel2.add(pricePaidTextBox, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(45, -1), null, new Dimension(45, -1), 0, false));
        nameOfGlassTextBox = new JTextField();
        panel2.add(nameOfGlassTextBox, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        companySelect = new JComboBox();
        companySelect.setEditable(true);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        companySelect.setModel(defaultComboBoxModel1);
        companySelect.setToolTipText("Company that manufactured the glass.");
        panel2.add(companySelect, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("x");
        panel2.add(label5, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(10, 10), 0, false));
        sizeTextBox2 = new JTextField();
        panel2.add(sizeTextBox2, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, -1), null, new Dimension(100, -1), 0, false));
        clearButton = new JButton();
        clearButton.setText("Clear");
        clearButton.setToolTipText("Clears all data from feilds.");
        panel2.add(clearButton, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(100, -1), 0, false));
        saveButton = new JButton();
        saveButton.setText("Save to JSON");
        saveButton.setToolTipText("Saves the current entry to the current JSON file.");
        panel2.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(150, -1), 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        entryPane.addTab("Basic Item Entry", panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 3, 3, 3), -1, -1));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Label");
        panel4.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textField1 = new JTextField();
        panel4.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Label");
        panel4.add(label7, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        panel4.add(textField2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Label");
        panel4.add(label8, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField3 = new JTextField();
        panel4.add(textField3, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        newGlassEntry.add(toolBar1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        saveButton1 = new JButton();
        saveButton1.setText("Save");
        toolBar1.add(saveButton1);
        loadButton = new JButton();
        loadButton.setText("Load");
        toolBar1.add(loadButton);
        settingsButton = new JButton();
        settingsButton.setText("Settings");
        toolBar1.add(settingsButton);
        labelEditor = new JButton();
        labelEditor.setText("Label Editor");
        toolBar1.add(labelEditor);
        tablePane = new JTabbedPane();
        newGlassEntry.add(tablePane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        ScrollPane = new JScrollPane();
        ScrollPane.setAutoscrolls(true);
        tablePane.addTab("Database", ScrollPane);
        glassTable = new JTable();
        glassTable.setAutoCreateColumnsFromModel(true);
        glassTable.setAutoCreateRowSorter(true);
        glassTable.setColumnSelectionAllowed(true);
        glassTable.setName("");
        ScrollPane.setViewportView(glassTable);
        consolePane = new JScrollPane();
        consolePane.setAutoscrolls(true);
        consolePane.putClientProperty("html.disable", Boolean.FALSE);
        newGlassEntry.add(consolePane, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(30, -1), new Dimension(30, -1), new Dimension(768, -1), 0, false));
        consolePane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Console", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4514048)));
        console = new JTextPane();
        console.setDragEnabled(false);
        console.setEditable(false);
        console.setEnabled(true);
        console.setToolTipText("Information about what is going on!");
        consolePane.setViewportView(console);
        final JToolBar toolBar2 = new JToolBar();
        newGlassEntry.add(toolBar2, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        toolBar2.add(deleteButton);
        edit = new JButton();
        edit.setHideActionText(false);
        edit.setText("Edit?");
        toolBar2.add(edit);
        printButton = new JButton();
        printButton.setText("Print Selected");
        toolBar2.add(printButton);
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        newGlassEntry.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelPreview = new JPanel();
        labelPreview.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        newGlassEntry.add(labelPreview, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final DataLabel dataLabel1 = new DataLabel();
        labelPreview.add(dataLabel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() { return newGlassEntry; }
}
