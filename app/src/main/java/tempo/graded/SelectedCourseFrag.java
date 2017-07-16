package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import io.realm.Realm;

/**
 * Created by Pasoon on 2017-07-15.
 */

public class SelectedCourseFrag extends Fragment {

    private ImageButton addBtn;
    private View rootView;
    private Realm realm;

    public static SelectedCourseFrag newInstance() {
        SelectedCourseFrag fragment = new SelectedCourseFrag();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Long id = getArguments().getLong("CourseID");
        rootView = inflater.inflate(R.layout.selected_course_frag, container, false);
        setLayout(id);
        addBtn = (ImageButton) getActivity().findViewById(R.id.addItem);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i("Add A Deliverable", "Button Clicked");
            }
        });

        return rootView;
    }

    private void setLayout(Long id) {
        Course course = realm.where(Course.class).equalTo("id", id).findFirst();
        String name = course.getName();
        String code = course.getCourseCode();
        TextView courseName = (TextView) rootView.findViewById(R.id.CourseName);
        courseName.setText(name);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText(code);
    }
}
