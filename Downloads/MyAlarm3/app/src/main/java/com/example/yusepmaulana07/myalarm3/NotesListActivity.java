package com.example.yusepmaulana07.myalarm3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yusepmaulana07.myalarm3.model.Notes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NotesListActivity extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_page);

        String data = readFromFile(this);
        Notes.data = data;
        setDataToMemory(data);


        if (!Notes.mapNotes.isEmpty()){
            Set<String> set = Notes.mapNotes.keySet();
            listItems =  new ArrayList<>(set);
            Collections.sort(listItems);
            System.out.println("list data :"+listItems.toString());
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
                                view.animate().setDuration(1000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                listItems.remove(item);
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);

                                                Notes.mapNotes.remove(item);

                                                String data = convertMapToSavingFormat();
                                                Notes.data = data;

                                                writeToFile(data,NotesListActivity.this);
                                                System.out.println("after delete : "+Notes.mapNotes.toString());

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

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void setDataToMemory(String data){

//        Notes.data + title.getText().toString()+"|.|"+content.getText().toString()+"|/|";
        Map<String,String> mapsss = new HashMap<>();

        try{
            while (data.length()>1){
                String key = data.substring(0, data.indexOf("|.|"));
                data = data.substring(key.length()+3);
                String value = data.substring(0, data.indexOf("|/|"));
                data = data.substring(value.length()+3);
                mapsss.put(key,value);
//                System.out.println("pencahar : "+data);
            }
            Notes.mapNotes = mapsss;

        }catch (Exception e){
                System.out.print("Destroy DATAAAAAA");
                writeToFile("",this);
        }


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

    private String convertMapToSavingFormat(){
        String s ="";
        Set<String> set2 = Notes.mapNotes.keySet();
        List<String> listItems2 =  new ArrayList<>(set2);
        for (String s1 :listItems2){
            s = s + s1+"|.|"+ Notes.mapNotes.get(s1)+"|/|";
        }
        return s;
    }

}
