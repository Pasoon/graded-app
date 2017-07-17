package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import io.realm.Realm;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * Created by Pasoon on 2017-07-15.
 */

public class SelectedCourseFrag extends Fragment {

    private ImageButton addBtn;
    private View rootView;
    private Realm realm;
    private Button okBtn;
    private Spinner DeliverableType;
    private EditText DeliverableName;
    private EditText DeliverableWeight;
    private AlertDialog dialog;
    private Course course;


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
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Add A Deliverable", "Button Clicked");
                Log.i("quickGrade Button", "Button Clicked");
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.add_deliverable_frag, null);

                DeliverableType = (Spinner) view.findViewById(R.id.DeliverableTypeSpinner);
                String[] items = new String[]{"Assignment", "Lab", "Test"};
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
                DeliverableType.setAdapter(adapter);;

                DeliverableName = (EditText) view.findViewById(R.id.DeliverableNameInput);
                DeliverableWeight = (EditText) view.findViewById(R.id.DeliverableWeightInput);
                okBtn = (Button) view.findViewById(R.id.Ok);

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        okBtnClicked();
                    }
                });

                alertBuilder.setView(view);
                dialog = alertBuilder.create();
                dialog.show();
            }
        });

        return rootView;
    }

    private void setLayout(Long id) {
        course = realm.where(Course.class).equalTo("id", id).findFirst();
        String name = course.getName();
        String code = course.getCourseCode();
        TextView courseName = (TextView) rootView.findViewById(R.id.CourseName);
        courseName.setText(name);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText(code);
    }


    private void createDeliverable(){

        String dN = DeliverableName.getText().toString();
        Double dW = Double.parseDouble(DeliverableWeight.getText().toString());
        String dT = DeliverableType.getSelectedItem().toString();

        Deliverable deliverable = new Deliverable(dN, dW, dT);

        realm.beginTransaction();
        if(dT.equals("Assignment")){
            course.getAssignments().add(deliverable);
        }

        if(dT.equals("Lab")){
            course.getLabs().add(deliverable);
        }

        if(dT.equals("Test")){
            course.getTests().add(deliverable);
        }
        realm.commitTransaction();


    }



    public void okBtnClicked() {

        if (!DeliverableWeight.getText().toString().isEmpty() && !DeliverableName.getText().toString().isEmpty() && !DeliverableType.getSelectedItem().toString().isEmpty()) {
            createDeliverable();
            Toast.makeText(getActivity(), "Deliverable Added!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        } else {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}

