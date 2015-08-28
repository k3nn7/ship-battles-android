package net.lalik.shipbattles.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.lalik.shipbattles.sdk.values.ShipsInventory;

public class ShipsInventoryListAdapter extends ArrayAdapter<ShipsInventory.Item> {
    private final Context context;
    private final ShipsInventory.Item[] items;

    public ShipsInventoryListAdapter(Context context, ShipsInventory.Item[] items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView rowView = (TextView)inflater.inflate(android.R.layout.select_dialog_item, parent, false);
        rowView.setText(items[position].getShipClass().getName());
        return rowView;
    }

    @Override
    public long getItemId(int position) {
        return items[position].getShipClass().getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
