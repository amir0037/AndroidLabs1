package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    MyListAdapter adapter = new MyListAdapter();
    public ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter( adapter );}

        class MyListAdapter extends BaseAdapter{

            @Override
            public int getCount() {
                return arr.size();
            }

            @Override
            public Object getItem(int position) {
                return arr.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.activity_send, parent, false);
                TextView textView = view.findViewById(R.id.sendText);
                textView.setText(getItem(position).toString());

                ImageButton ib = view.findViewById(R.id.sendImage);
               // ib.setImageIcon();
                return view;//step 9
            }
        }
    }

