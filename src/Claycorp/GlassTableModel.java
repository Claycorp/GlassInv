package Claycorp;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GlassTableModel extends AbstractTableModel
{
    private final List<DataGlassSheet> db;
    private final String[] columnNames = {"Manufacturer", "Item ID", "Name Of Glass", "Price Paid", "Price Per Inch", "Total Area", "UUID"};

    public GlassTableModel(List<DataGlassSheet> db)
    {
        this.db = db;
    }

    @Override
    public int getRowCount()
    {
        return db.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        DataGlassSheet item = db.get(rowIndex);
        switch (columnIndex)
        {
            case 0: return item.Manufacturer;
            case 1: return item.ItemID;
            case 2: return item.NameOfGlass;
            case 3: return item.PricePaid;
            case 4: return item.PricePerInch;
            case 5: return item.TotalArea;
            case 6: return item.UUID;
            default: return null; // fuck. This shouldn't happen.
        }
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    public boolean isCellEditable(int row, int col)
    {
        return false;
    }
}
