package com.advanced.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PowerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PowerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PowerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PowerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PowerFragment newInstance(String param1, String param2) {
        PowerFragment fragment = new PowerFragment();
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_power, null);

        Button btShutdown = root.findViewById(R.id.bt_shutdown);
        btShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "reboot -p" });
                    proc.waitFor();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button btReboot2OS = root.findViewById(R.id.bt_reboot2OS);
        btReboot2OS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "reboot" });
                    proc.waitFor();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button btReboot2Recovery = root.findViewById(R.id.bt_reboot2Recovery);
        btReboot2Recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "reboot recovery" });
                    proc.waitFor();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return root;
    }
}