package com.example.lab3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private MyListAdapter adapter = new MyListAdapter();
    private ArrayList<Message> arr = new ArrayList<>();
    private ListView list;
    Button sendButton;
    Button receiveButton;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter( adapter );
        sendButton = findViewById(R.id.send);
        receiveButton = findViewById(R.id.receive);
        editText = findViewById(R.id.editText);

        list.setOnItemClickListener((parent, view, position, id) ->{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Do you want to delete this?").setMessage("The selected raw is " +
                            (position) + " The DB id is " + adapter.getItemId(position)).
                            setPositiveButton("Yes", (click, arg) -> {
                                arr.remove(position);
                                list.setAdapter(adapter); }).
                            setNegativeButton("No", (click, arg) -> {
                        return;
                    }).create().show();

                });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                arr.add(new Message(true, editText.getText().toString()));
                editText.setText("");
                adapter.notifyDataSetChanged();

            }
        });
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.add(new Message(false, editText.getText().toString()));
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });

    }

        class Message{
            public boolean sOr; //true for send, false for receive
            public String message;

            public Message(boolean sOr, String message) {
                this.sOr = sOr;
                this.message = message;
            }
                public String getMessage(){
                return message;
                }
            public boolean getsOr() {
                return sOr;
            }
        }

        class MyListAdapter extends BaseAdapter{
            @Override
            public int getCount() {
                return arr.size();
            }
            @Override
            public Message getItem(int position) {
                return arr.get(position);
            }
            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Message message = arr.get(position);
                LayoutInflater inflater = getLayoutInflater();
                View rowView = null;
                if(message.sOr)
                    rowView= inflater.inflate(R.layout.activity_send, parent, false);
                else
                    rowView= inflater.inflate(R.layout.activity_receive, parent, false);
                TextView rowText = rowView.findViewById(R.id.sendText);
                rowText.setText(message.getMessage());
                return rowView;
            }
        }
    }

