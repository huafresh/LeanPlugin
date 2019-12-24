package com.hua.leanplugin.core;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-23 17:18
 */

public class PluginContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            PluginManager.get().context = (Application) context.getApplicationContext();
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
