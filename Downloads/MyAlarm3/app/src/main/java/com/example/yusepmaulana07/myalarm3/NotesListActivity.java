package com.example.yusepmaulana07.myalarm3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yusepmaulana07.myalarm3.model.Notes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NotesListActivity extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_page);

//        Button finish = (Button) findViewById(R.id.button3);
//        finish.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//
//        });
        if (!Notes.mapNotes.isEmpty()){
            Set<String> set = Notes.mapNotes.keySet();
            listItems =  new ArrayList<>(set);
            Collections.sort(listItems);
        }

        ListView listView = (ListView) findViewById(R.id.listNotes);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Notes.selected = item;
                                Intent myIntent = new Intent(view.getContext(), EditNoteActivity.class);
                                startActivityForResult(myIntent, 0);
                                break;

                            case DialogInterface.BUTTON_NEUTRAL:
                                view.animate().setDuration(2000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                listItems.remove(item);
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                                Notes.mapNotes.remove(item);
                                            }
                                        });
                                break;

                             case DialogInterface.BUTTON_NEGATIVE:
                                 break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(NotesListActivity.this);
                builder.setMessage("What do you want ?").setPositiveButton("Open", dialogClickListener)
                        .setNeutralButton("Delete", dialogClickListener).setNegativeButton("Back", dialogClickListener).show();


            }
        });
    }

    public void addNote(View view){
//        listItems.add("addddd");
//        adapter.notifyDataSetChanged();
        Intent myIntent = new Intent(view.getContext(), EditNoteActivity.class);
        startActivityForResult(myIntent, 0);

    }

}
