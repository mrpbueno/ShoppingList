package com.example.shoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.NumberFormat;
import java.util.List;

public class AdapterShoppingList extends BaseAdapter {

    private List<ShoppingList> shoppingLists;
    private Context context;
    private LayoutInflater inflater;
    private ItemSupport item;


    public AdapterShoppingList(Context context, List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shoppingLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return shoppingLists.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list, null);

            item = new ItemSupport();
            item.tvName = convertView.findViewById(R.id.tvName);
            item.tvQuantity = convertView.findViewById(R.id.tvQuantity);
            item.tvPrice = convertView.findViewById(R.id.tvPrice);
            item.cbStatus = convertView.findViewById(R.id.cbStatus);
            item.layout = convertView.findViewById(R.id.llList);
            convertView.setTag(item);
        } else {
            item = (ItemSupport) convertView.getTag();
        }

        ShoppingList list = shoppingLists.get(position);
        item.tvName.setText(list.getName());
        item.tvQuantity.setText(String.valueOf(list.getQuantity()));
        item.tvPrice.setText(NumberFormat.getCurrencyInstance().format(list.getDoublePrice()));
        item.cbStatus.setId(list.getId());
        item.cbStatus.setChecked(list.getStatus());

        if (position % 2 == 0) {
            item.layout.setBackgroundColor(Color.rgb(230, 230, 230));
        } else {
            item.layout.setBackgroundColor( Color.WHITE );
        }
        return convertView;

    }
}
