package Claycorp;

import javax.swing.*;

public class OptionsListModel extends AbstractListModel
{
    private final DataSettings settings;

    public OptionsListModel(DataSettings settings) {this.settings = settings;}

    @Override
    public int getSize()
    {
        return settings.omniBoxOptions.size();
    }

    @Override
    public Object getElementAt(int index)
    {
        return settings.omniBoxOptions.get(index);
    }
}
