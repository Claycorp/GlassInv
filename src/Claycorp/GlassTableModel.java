package Claycorp;

import javax.swing.table.AbstractTableModel;

public class GlassTableModel extends AbstractTableModel
{

    public String [] columnNames = {"Manufacturer", "Item ID", "Name Of Glass", "Price Paid",
        "Price Per Inch", "Total Area", "UUID"};

    public Object[][] data = {

    };

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount()
    {
        return data.length;
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return null;
    }

    public boolean isCellEditable(int row, int col)
    { return false; }
}
