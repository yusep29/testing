package com.example.yusepmaulana07.myalarm3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends android.app.ListActivity {

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayList<String> strings  = new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    int clickCounter=0;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.listview_page);
//        ListView listView = (ListView) findViewById(R.id.list1);
//        listView.setAdapter(adapter);
//        final StableArrayAdapter adapter = new StableArrayAdapter(this,
//                android.R.layout.simple_list_item_1, list);
//        listview.setAdapter(adapter);


        strings.add("hiji");
        strings.add("dua");
        ListView listView = (ListView) findViewById(R.id.list1);
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings);
        listView.setAdapter(adapter2);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                strings.remove(item);
                                adapter2.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }
        });


    }

    //METHOD  WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        listItems.add("Clicked : "+clickCounter++);
        strings.add("nambah "+clickCounter++);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }

    public void deleteItems(View v){
        listItems.clear();
        adapter.notifyDataSetChanged();
        clickCounter =0;
    }
}
