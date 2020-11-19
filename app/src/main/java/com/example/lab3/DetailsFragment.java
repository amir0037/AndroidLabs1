package com.example.lab3;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private Bundle dataFromActivity;
    private long id;
    private boolean checked;
     private AppCompatActivity parentActivity;

    public DetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
//    public static DetailsFragment newInstance(long id, Bundle dataFromActivity, boolean checked) {
//        DetailsFragment fragment = new DetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, String.valueOf(dataFromActivity));
//        args.putLong(ARG_PARAM2, id);
//        args.putBoolean(ARG_PARAM3, checked);
//
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getLong(ARG_PARAM2);
//            mParam3 = getArguments().getBoolean(ARG_PARAM3);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);
        checked = dataFromActivity.getBoolean(ChatRoomActivity.CHECKED);


        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_details, container, false);

        TextView message = (TextView) result.findViewById(R.id.messageHere);
        message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        TextView idView = (TextView) result.findViewById(R.id.idText);
        idView.setText("ID=" + id);

        CheckBox checkBox = result.findViewById(R.id.checkBox);
        checkBox.setChecked(checked);

        // get the delete button, and add a click listener:
        Button hide = (Button)result.findViewById(R.id.hide);
        hide.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
    }
