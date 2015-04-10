package com.thinkful.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteListItemAdapter mAdapter;
    private Button mButton;
    private Context mContext;
    private int bg_color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        bg_color  = prefs.getInt("BG_COLOR", Color.WHITE);


        NotesDBHelper.getInstance(this).getReadableDatabase();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setBackgroundColor(bg_color);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        mAdapter = new NoteListItemAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text in the EditText
                EditText editText = (EditText) findViewById(R.id.edit_text);
                String text = editText.getText().toString();

                // Create a new NoteListItem with the text
                NoteListItem noteListItem = new NoteListItem(text);

                // Add the item to the adapter
                mAdapter.addItem(noteListItem);

                // Set the EditText to an empty string
                editText.setText(" ");
                // editText.setText("Something here");

                Toast.makeText(getApplicationContext(), "Note Added", Toast.LENGTH_SHORT).show();
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                NoteDAO dao = new NoteDAO(MainActivity.this);
                dao.save(noteListItem);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Checking to see if id of the menu item that was selected is the settings button's id
        if (id == R.id.action_settings) {
           //openColorDialog();
            Intent intent = new Intent(this, AppSettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data.hasExtra("Note")) {
                NoteListItem note = (NoteListItem) data.getSerializableExtra("Note");
                Toast.makeText(this, note.getText(),
                        Toast.LENGTH_LONG).show();
                mAdapter.addItem(note);
            }
        }
    }

    public void openColorDialog() {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_color_title)
                .setMessage(R.string.setting_color_message)
                .setView(input)
                .setPositiveButton(R.string.positive_button_label, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("NOTE_COLOR", value);
                        editor.commit();
                        setColor();
                    }
                }).setNegativeButton(R.string.negative_button_label, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //No need to put any code to tell what to do when user clicks Cancel
            }
        }).show();

    }


    public void setColor() {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String color = prefs.getString("NOTE_COLOR", "W");
        prefs.edit().clear().commit();
        if (color.toUpperCase().contains("G")) {
            mRecyclerView.setBackgroundColor(Color.GREEN);
        } else if (color.toUpperCase().contains("R")) {
            mRecyclerView.setBackgroundColor(Color.RED);
        } else mRecyclerView.setBackgroundColor(Color.WHITE);
    }
}

