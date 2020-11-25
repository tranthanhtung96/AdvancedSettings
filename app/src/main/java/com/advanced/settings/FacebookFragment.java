package com.advanced.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.pm.PackageManager;

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

    Button btEnable, btDisable;

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

        PackageManager pm = getContext().getPackageManager();

        btDisable = root.findViewById(R.id.bt_disable);
        btEnable = root.findViewById(R.id.bt_enable);

        if (pm.getApplicationEnabledSetting("com.facebook.orca") < pm.COMPONENT_ENABLED_STATE_DISABLED) {
            allowClickButtonDisable();
        } else {
            allowClickButtonEnable();
        }

        btDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process proc;
                    proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "pm disable-user --user 0 com.facebook.orca" });
                    proc.waitFor();
                    proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "pm disable-user --user 0 com.facebook.katana" });
                    proc.waitFor();
                    allowClickButtonEnable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process proc;
                    proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "pm enable com.facebook.orca" });
                    proc.waitFor();
                    proc = Runtime.getRuntime()
                            .exec(new String[]{ "su", "-c", "pm enable com.facebook.katana" });
                    proc.waitFor();
                    allowClickButtonDisable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return root;
    }

    private void allowClickButtonDisable() {
        btEnable.setEnabled(false);
        btDisable.setEnabled(true);
    }

    private void allowClickButtonEnable() {
        btEnable.setEnabled(true);
        btDisable.setEnabled(false);
    }
}