package com.agendayou.agendayou.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.agendayou.agendayou.R;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, ContactsFragment.newInstance(), null)
                .commit();
    }
}
