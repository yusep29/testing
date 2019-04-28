package com.example.yusepmaulana07.myalarm3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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
                            case DialogInterface.BUTTON_NEUTRAL:

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                view.animate().setDuration(2000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                listItems.remove(item);
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                            }
                                        });
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(NotesListActivity.this);
                builder.setMessage("Are you sure?").setNeutralButton("Open", dialogClickListener)
                        .setNegativeButton("Delete", dialogClickListener).show();


            }
        });
    }

    public void addNote(View view){
        listItems.add("addddd");
        adapter.notifyDataSetChanged();
    }

}