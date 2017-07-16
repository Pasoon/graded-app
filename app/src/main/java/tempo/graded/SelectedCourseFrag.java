package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Pasoon on 2017-07-15.
 */

public class SelectedCourseFrag extends Fragment {

    private ViewPager myPager;
    private ImageButton addBtn;

    public static SelectedCourseFrag newInstance() {
        SelectedCourseFrag fragment = new SelectedCourseFrag();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.selected_course_frag, container, false);



        return rootView;
    }
}
