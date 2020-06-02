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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

import com.example.android.safe.data.SafeContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.android.safe.data.SafeContract.ItemEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int TASK_LOADER = 0;
    ItemAdapter itemAdapter;

    String cursorFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Cursor mCursor=getContacts();
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
        todolist.setTextFilterEnabled(true);

        itemAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {

//                String query = "SELECT _ID as _id, name FROM MYTABLE "
//                        + "where name like '%" + constraint + "%' "
//                        + "ORDER BY NAME ASC";
//                return db.rawQuery(query, null);
                return getCursor(constraint.toString());

            }
        });


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
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem =menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        final MenuItem dot=menu.findItem(R.id.dark);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                itemAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_search:

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.dark:
                if(item.getTitle().toString().equals("Dark Mode")) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                }

                return true;
            // Respond to a click on the "Up" arrow button in the app bar
//            case android.R.id.home:
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                SafeContract.ItemEntry._ID,
                SafeContract.ItemEntry.COLUMN_NAME,
                };

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



    private Cursor getContacts() {
        // Run query
        Uri uri = ItemEntry.CONTENT_URI;
        String[] projection = new String[]{ ItemEntry._ID , ItemEntry.COLUMN_NAME };

        String selection = "*";

        return getContentResolver().query(uri,projection,null ,null,null);
    }

    private Cursor getCursor(String str) {
        Cursor mCursor = null;
        if (str == null  ||  str.length () == 0)  {
            Log.e("TAG","heree");
            mCursor = getContacts();
        }
        else {
            Uri uri = ItemEntry.CONTENT_URI;
            String[] projection = {
                    ItemEntry._ID,
                    ItemEntry.COLUMN_NAME,
                    ItemEntry.COLUMN_ID,
                    ItemEntry.COLUMN_PASS};

            String selection =  ItemEntry.COLUMN_NAME + " like '" + str + "%'";
            mCursor = getContentResolver().query(uri, projection, selection, null,null);
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String arg0) {
//        cursorFilter = !TextUtils.isEmpty(arg0) ? arg0 : null;
//        getLoaderManager().restartLoader(0, null, this);
//        return true;
//    }
}