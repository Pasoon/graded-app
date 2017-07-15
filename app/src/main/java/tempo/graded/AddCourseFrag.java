package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zarif on 2017-07-12.
 */

public class AddCourseFrag extends Fragment {

    public static AddCourseFrag newInstance() {
        AddCourseFrag fragment = new AddCourseFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_course_frag, container, false);
        return rootView;
    }


}
