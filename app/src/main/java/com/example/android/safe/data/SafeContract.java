package com.example.android.safe.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.widget.ScrollView;

public class SafeContract {

    private SafeContract() {}



    public static final String CONTENT_AUTHORITY = "com.example.android.safe";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "safe";

    public static final class ItemEntry implements BaseColumns {


        public final static String TABLE_NAME="safe";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;


        public final static String _ID=BaseColumns._ID;

        public final static String COLUMN_NAME="name";
        public final static String COLUMN_ID="id";
        public final static String COLUMN_PASS="password";

    }
}
