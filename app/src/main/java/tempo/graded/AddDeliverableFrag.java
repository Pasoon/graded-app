package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pasoon on 2017-07-16.
 */

public class AddDeliverableFrag extends Fragment {

    public static AddDeliverableFrag newInstance() {
        AddDeliverableFrag fragment = new AddDeliverableFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_deliverable_frag, container, false);
        return rootView;
    }

}
