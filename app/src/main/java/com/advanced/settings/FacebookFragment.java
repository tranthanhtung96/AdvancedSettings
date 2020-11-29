package com.advanced.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FacebookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacebookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean facebookEnabledStatus, messengerEnabledStatus;
    private Switch facebookSwitch, messengerSwitch;
    private MyApplication myApp;
    private BroadcastReceiver facebookBroadcastReceiver, messengerBroadcastReceiver;
    private static boolean running = false;

    public FacebookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FacebookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FacebookFragment newInstance(String param1, String param2) {
        FacebookFragment fragment = new FacebookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_facebook, null);

        myApp = (MyApplication) getActivity().getApplication();

        facebookSwitch = root.findViewById(R.id.sw_facebook);
        messengerSwitch = root.findViewById(R.id.sw_messenger);

        updateFacebookSwitch();
        updateMessengerSwitch();

        facebookSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facebookEnabledStatus) {
                    myApp.disablePackage(MyApplication.FACEBOOK_PACKAGE_NAME);
                } else {
                    myApp.enablePackage(MyApplication.FACEBOOK_PACKAGE_NAME);
                }
                updateFacebookSwitch();
            }
        });

        messengerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messengerEnabledStatus) {
                    myApp.disablePackage(MyApplication.MESSENGER_PACKAGE_NAME);
                } else {
                    myApp.enablePackage(MyApplication.MESSENGER_PACKAGE_NAME);
                }
                updateMessengerSwitch();
            }
        });

        facebookBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateFacebookSwitch();
            }
        };
        messengerBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateMessengerSwitch();
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(facebookBroadcastReceiver,
                new IntentFilter(FacebookTileService.UPDATE_FACEBOOK_ENABLED_STATUS_ACTION));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(messengerBroadcastReceiver,
                new IntentFilter(MessengerTileService.UPDATE_MESSENGER_ENABLED_STATUS_ACTION));

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        running = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        running = false;
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(facebookBroadcastReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(messengerBroadcastReceiver);
    }

    private void updateFacebookSwitch() {
        facebookEnabledStatus = myApp.getPackageEnabledStatus(MyApplication.FACEBOOK_PACKAGE_NAME);
        facebookSwitch.setChecked(facebookEnabledStatus);
    }

    private void updateMessengerSwitch() {
        messengerEnabledStatus = myApp.getPackageEnabledStatus(MyApplication.MESSENGER_PACKAGE_NAME);
        messengerSwitch.setChecked(messengerEnabledStatus);
    }

    public static boolean isRunning() {
        return running;
    }
}