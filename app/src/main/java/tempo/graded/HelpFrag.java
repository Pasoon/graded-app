package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pasoon on 2017-07-18.
 */

public class HelpFrag extends Fragment {

    public static HelpFrag newInstance() {
        HelpFrag fragment = new HelpFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help_frag, container, false);
        return rootView;
    }

}
