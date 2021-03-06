package com.example.yusepmaulana07.myalarm3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.yusepmaulana07.myalarm3.model.Notes;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnotes_page);

        if (Notes.selected!=null){
            TextView textView = (TextView) findViewById(R.id.editText4);
            textView.setText(Notes.selected);
            textView = (TextView) findViewById(R.id.editText3);
            textView.setText(Notes.mapNotes.get(Notes.selected));
            Notes.selected =null;
        }

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {


                System.out.print("before save "+ Notes.data.toString());
                Map<String,String> notes  = Notes.mapNotes;

                TextView title = (TextView) findViewById(R.id.editText4);
                TextView content = (TextView) findViewById(R.id.editText3);
               notes.put(title.getText().toString(),content.getText().toString());

                Notes.mapNotes = notes;

                Notes.data = Notes.data + title.getText().toString()+"|.|"+content.getText().toString()+"|/|";
                writeToFile(Notes.data,EditNoteActivity.this);
                System.out.print("After save "+ Notes.data.toString());

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


    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
