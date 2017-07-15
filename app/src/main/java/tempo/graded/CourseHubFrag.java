package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Zarif on 2017-07-12.
 */

public class CourseHubFrag extends Fragment {

    private EditText courseName;
    private EditText courseCode;
    private Button addCourseBtn;
    private Button okBtn;

    public static CourseHubFrag newInstance() {
        CourseHubFrag fragment = new CourseHubFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.course_hub_frag, container, false);

        addCourseBtn = (Button) rootView.findViewById(R.id.buttontest);

        addCourseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i("quickGrade Button", "Button Clicked");
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.add_course_frag,null);

                courseName = (EditText) view.findViewById(R.id.CourseNameInput);
                courseCode = (EditText) view.findViewById(R.id.CourseCodeInput);
                okBtn = (Button) view.findViewById(R.id.Ok);

                okBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View ßview){
                        if(!courseName.getText().toString().isEmpty() && !courseCode.getText().toString().isEmpty()){
                            Course course = new Course();
                            course.setName(courseName.getText().toString());
                            course.setCourseCode(courseCode.getText().toString());
                            Toast.makeText(getActivity(), "Course Added!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertBuilder.setView(view);
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
        return rootView;
    }




}
