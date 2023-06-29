package br.com.poo2.bookreaderapp.utils;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.google.android.gms.common.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static String getFilePathFromUri(Context context, Uri uri) {
        String filePath = null;
        try {
            String scheme = uri.getScheme();
            if (scheme != null && scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    String displayName = cursor.getString(index);
                    File file = new File(context.getFilesDir(), displayName);
                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    FileOutputStream outputStream = new FileOutputStream(file);
                    if (inputStream != null) {
                        IOUtils.copyStream(inputStream, outputStream);
                        inputStream.close();
                    }
                    outputStream.close();
                    filePath = file.getAbsolutePath();
                }
                if (cursor != null) {
                    cursor.close();
                }
            } else if (scheme != null && scheme.equals(ContentResolver.SCHEME_FILE)) {
                filePath = uri.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

}
