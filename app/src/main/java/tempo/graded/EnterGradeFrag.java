package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pasoon on 2017-07-17.
 */

public class EnterGradeFrag extends Fragment {

    public static EnterGradeFrag newInstance() {
        EnterGradeFrag fragment = new EnterGradeFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.enter_grade_frag, container, false);
        return rootView;
    }


}
