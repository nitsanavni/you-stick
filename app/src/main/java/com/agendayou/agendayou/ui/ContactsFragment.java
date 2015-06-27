package com.agendayou.agendayou.ui;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.agendayou.agendayou.R;

import utils.ContactsUtils;

/**
 * Created by nitsa_000 on 24-Jun-15.
 */
public class ContactsFragment extends Fragment {

    private ListView mList;
    private SimpleCursorAdapter mCursorAdapter;
    private long mContactId;
    private String mContactKey;
    private Uri mContactUri;

    public ContactsFragment() {
    }

    public static Fragment newInstance() {
        return new ContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contacts_fragment, container, false);
    }

    /*
         * Defines an array that contains resource ids for the layout views
         * that get the Cursor column contents. The id is pre-defined in
         * the Android framework, so it is prefaced with "android.R.id"
         */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return ContactsUtils.getContactsLoader(getActivity());
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                mCursorAdapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCursorAdapter.swapCursor(null);
            }
        });

        mList = (ListView) getActivity().findViewById(R.id.list);
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contact_list_item,
                null,
                ContactsUtils.getFromColumns(), TO_IDS,
                0);
        mList.setAdapter(mCursorAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public static final int CONTACT_KEY_INDEX = 1;
            public static final int CONTACT_ID_INDEX = 0;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the Cursor
                Cursor cursor = ((CursorAdapter) parent.getAdapter()).getCursor();
                // Move to the selected contact
                cursor.moveToPosition(position);
                // Get the _ID value
                mContactId = cursor.getLong(CONTACT_ID_INDEX);
                // Get the selected LOOKUP KEY
                mContactKey = cursor.getString(CONTACT_KEY_INDEX);
                // Create the contact's content Uri
                mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);

                String name = cursor.getString(cursor.getColumnIndex(ContactsUtils.getFromColumns()[0]));
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        /*
         * You can use mContactUri as the content URI for retrieving
         * the details for a contact.
         */
            }
        });
    }

    private static class ContactsAdapter extends SimpleCursorAdapter {

        public ContactsAdapter(Context context, String[] from, int[] to) {
            super(context, 0, null, from, to, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            TextView view = (TextView) inflater.inflate(R.layout.contact_list_item, parent);
            editView(view, cursor);
            return view;
        }

        private void editView(TextView view, Cursor cursor) {
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            view.setText(displayName);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            editView((TextView) view, cursor);
        }
    }
}
