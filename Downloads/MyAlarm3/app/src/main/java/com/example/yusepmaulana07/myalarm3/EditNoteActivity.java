package com.example.yusepmaulana07.myalarm3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.yusepmaulana07.myalarm3.model.Note;
import com.example.yusepmaulana07.myalarm3.model.Notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnotes_page);


        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Map<String,String> notes  = Notes.mapNotes;

                TextView title = (TextView) findViewById(R.id.editText4);
                TextView content = (TextView) findViewById(R.id.editText3);
               notes.put(title.getText().toString(),content.getText().toString());

                Notes.mapNotes = notes;

//                Intent intent = new Intent();
//                setResult(RESULT_OK, intent);
//                finish();
                Intent myIntent = new Intent(view.getContext(), NotesListActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });
    }
}
