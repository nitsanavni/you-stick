package utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by nitsa_000 on 24-Jun-15.
 */
public class ContactsUtils {
    /*
         * Defines an array that contains column names to move from
         * the Cursor to the ListView.
         */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    private static final String TAG = ContactsUtils.class.getSimpleName();

    public static String[] getFromColumns() {
        return FROM_COLUMNS;
    }

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };

    public static String[] getProjection() {
        return PROJECTION;
    }

    public static CursorLoader getContactsLoader(Context context) {
        Context appContext = context.getApplicationContext();
        final String displayName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String selection = displayName + " is not null and " + displayName + "!=?";
        String[] selectionArgs = new String[]{""};
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        return new CursorLoader(appContext,
                uri,
                null,
                selection,
                selectionArgs,
                null);
    }
}
