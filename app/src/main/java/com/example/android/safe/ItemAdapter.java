package com.example.android.safe;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.safe.data.SafeContract.ItemEntry;

public class ItemAdapter extends CursorAdapter {


    public ItemAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView = (TextView) view.findViewById(R.id.name);
        int ColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_NAME);
        String Name = cursor.getString(ColumnIndex);
        textView.setText(Name);

    }
}
