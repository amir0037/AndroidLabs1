package com.example.lab3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    SQLiteDatabase db;
    FrameLayout fl;
    Bundle dataToPass;
    FragmentManager fm;
    DetailsFragment dFragment;
    // Boolean isTablet = true;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_ID = "ID";
    public static final String CHECKED = "CHECKED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        fl = findViewById(R.id.fl);

        boolean isTablet = findViewById(R.id.fl) !=null;

        list = (ListView) findViewById(R.id.list);
        list.setAdapter( adapter );
        sendButton = findViewById(R.id.send);
        receiveButton = findViewById(R.id.receive);
        editText = findViewById(R.id.editText);





        list.setOnItemClickListener((list, view, position, id) ->{

           Message selectedMassage = arr.get(position);

            dataToPass = new Bundle();
            dataToPass.putString(ITEM_SELECTED, arr.get(position).message);
            dataToPass.putLong(ITEM_ID, id);
                if(selectedMassage.sOr) {
            dataToPass.putBoolean(CHECKED, true);}
                else {
            dataToPass.putBoolean(CHECKED, false);
             }

            if(isTablet)
            {
                dFragment = new DetailsFragment();
                dFragment.setArguments( dataToPass );
                fm = getSupportFragmentManager();
                        fm.beginTransaction()
                        .replace(R.id. fl, dFragment)
                        .commit();
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);
            }

                });

        list.setOnItemLongClickListener((parent, view, position, id) ->{
            Message selectedMassage = arr.get(position);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?").setMessage("The selected raw is " +
                    (position) + " The DB id is " + adapter.getItemId(position)).
                    setPositiveButton("Yes", (click, arg) -> {
                        db.delete(MyOpener.table_name, MyOpener.col_id + "= ?", new String[] {Long.toString(selectedMassage.getID())});
                        arr.remove(position);
                        list.setAdapter(adapter);
                        if(isTablet) {
                            fm.beginTransaction().remove(this.dFragment).commit();
                        }

                    }).
                    setNegativeButton("No", (click, arg) -> {
                        return;
                    }).create().show();
            return true;
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(MyOpener.col_message, editText.getText().toString());
                newRowValues.put(MyOpener.col_type, 1);
                long newID = db.insert(MyOpener.table_name, null, newRowValues);
                arr.add(new Message(true, editText.getText().toString(), newID));
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(MyOpener.col_message, editText.getText().toString());
                newRowValues.put(MyOpener.col_type, 0);
                long newID = db.insert(MyOpener.table_name, null, newRowValues);
                arr.add(new Message(false, editText.getText().toString(), newID));
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        loadDataFromDatabase();
    }
    private void loadDataFromDatabase() {

        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        String[] columns = {MyOpener.col_id, MyOpener.col_message, MyOpener.col_type};
        Cursor results = db.query(false,
                MyOpener.table_name,
                columns,
                null,
                null,
                null,
                null, null, null);
      printCursor(results, MyOpener.version);
        int messageColumnIndex = results.getColumnIndex(MyOpener.col_message);
        int typeColumnIndex = results.getColumnIndex(MyOpener.col_type);
        int idColumnIndex = results.getColumnIndex(MyOpener.col_id);

        while (results.moveToNext()) {
            String message = results.getString(messageColumnIndex);
            int type = results.getInt(typeColumnIndex);
            long id = results.getLong(idColumnIndex);

           if(type==1){
            arr.add(new Message(true, message, id));
          } else if (type==0){
                arr.add(new Message(false, message, id));}
        }

    }
        class Message{
            public boolean sOr;//true for send, false for receive
            public String message;
            public long id;

            public Message(boolean sOr, String message, long id) {
                this.sOr = sOr;
                this.message = message;
                this.id=id;
            }

            public long getID(){
                return  id;}

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

        public void printCursor( Cursor c, int version){
            Log.d("MyTag", "The DB version number: " + String.valueOf(db.getVersion()));
            Log.d("MyTag", "The number of columns in the cursor: " + String.valueOf(c.getColumnCount()));
            for(int i=0; i<c.getColumnNames().length;i++){
            Log.d("MyTag", "Column name: " + c.getColumnNames()[i]);}
            Log.d("MyTag", "The number of rows in the cursor:" + String.valueOf(c.getCount()));
            Log.d("MyTag","Result row: " + DatabaseUtils.dumpCursorToString(c));

        }
}

