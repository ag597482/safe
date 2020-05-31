package com.example.android.safe;


import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import androidx.appcompat.app.AppCompatActivity;


import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.safe.data.SafeContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.android.safe.data.SafeContract.ItemEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int TASK_LOADER = 0;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ItemEntrys.class);
                startActivity(intent);
            }
        });

        final ListView todolist = (ListView)findViewById(R.id.list);

        View emptyview = (View)findViewById(R.id.emptyView);
        todolist.setEmptyView(emptyview);


        itemAdapter=new ItemAdapter(this,null);
        todolist.setAdapter(itemAdapter);

        todolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ItemEntrys.class);

                Uri currentUri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI,id);
                intent.setData(currentUri);
                startActivity(intent);

            }

        });



        getLoaderManager().initLoader(TASK_LOADER,null,this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                SafeContract.ItemEntry._ID,
                SafeContract.ItemEntry.COLUMN_NAME,
                SafeContract.ItemEntry.COLUMN_ID,
                SafeContract.ItemEntry.COLUMN_PASS};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,ItemEntry.CONTENT_URI,projection,null,null,null);
    }


    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        itemAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        itemAdapter.swapCursor(null);
    }



}
