package net.lalik.shipbattles.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.lalik.shipbattles.sdk2.entity.ShipClass;

public class ShipsInventoryListAdapter extends ArrayAdapter<ShipClass> {
    private final Context context;
    private final ShipClass[] items;

    public ShipsInventoryListAdapter(Context context, ShipClass[] items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView rowView = (TextView)inflater.inflate(android.R.layout.select_dialog_item, parent, false);
        rowView.setText(items[position].getName());
        return rowView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
