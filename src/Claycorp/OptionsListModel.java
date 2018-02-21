package Claycorp;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;

public class OptionsListModel extends AbstractListModel
{
    private final List<DataSettings> settings;

    public OptionsListModel(List<DataSettings> settings) {this.settings = settings;}

    @Override
    public int getSize()
    {
        return settings.size();
    }

    @Override
    public Object getElementAt(int index)
    {
        DataSettings item = settings.get(index);
        return item.omniBoxOptions;
    }

    @Override
    public void addListDataListener(ListDataListener l)
    {

    }

    @Override
    public void removeListDataListener(ListDataListener l)
    {

    }
}
