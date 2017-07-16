package tempo.graded;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Zarif on 2017-07-12.
 */

public class CourseHubFrag extends Fragment {

    private EditText courseName;
    private EditText courseCode;
    private ImageButton addCourseBtn;
    private Button okBtn;

    private RealmResults<Course> courseResults;
    private ListView mListView;
    private Realm realm;
    CourseAdapter adapter;
    private View rootView;
    private AlertDialog dialog;
    private ListView courseList;


    public static CourseHubFrag newInstance() {
        CourseHubFrag fragment = new CourseHubFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getActivity());
        //RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        //Realm.deleteRealm(configuration); //For flushing Realm DB
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.course_hub_frag, container, false);

        getCourses();
        initListView();
        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                adapter.notifyDataSetChanged();
            }
        };
        courseResults.addChangeListener(changeListener);

        addCourseBtn = (ImageButton) getActivity().findViewById(R.id.addItem);
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
                    public void onClick(View view){
                        okBtnClicked();
                    }
                });

                alertBuilder.setView(view);
                dialog = alertBuilder.create();
                dialog.show();
            }
        });


        courseList = (ListView) rootView.findViewById(R.id.courseList);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                SelectedCourseFrag frag = new SelectedCourseFrag();
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, frag);
                transaction.commit();

            }
        });
        return rootView;
    }

    private void initListView() {
        adapter = new CourseAdapter(getActivity(),courseResults);
        mListView = (ListView) rootView.findViewById(R.id.courseList);
        mListView.setAdapter(adapter);
    }

    private void getCourses(){
        courseResults = realm.where(Course.class).findAll();
        Log.i("Get Courses", "Got all courses.");
    }

    private void addCourseToRealm(){
        realm.beginTransaction();

        Number currentIdNum = realm.where(Course.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }


        Course course = realm.createObject(Course.class, nextId);
        course.setName(courseName.getText().toString());
        course.setCourseCode(courseCode.getText().toString());

        realm.commitTransaction();
    }



    public void okBtnClicked(){

        if(!courseName.getText().toString().isEmpty() && !courseCode.getText().toString().isEmpty()){
            addCourseToRealm();
            Toast.makeText(getActivity(), "Course Added!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        }
        else{
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

}
