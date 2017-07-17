package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;


/**
 * Created by Pasoon on 2017-07-15.
 */

public class SelectedCourseFrag extends Fragment {

    private View rootView;
    private Realm realm;
    private Spinner DeliverableType;
    private EditText DeliverableName;
    private EditText DeliverableWeight;
    private AlertDialog dialog;
    private Course course;
    private DeliverableAdapter adapterAssignment;
    private DeliverableAdapter adapterLabs;
    private DeliverableAdapter adapterTest;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.selected_course_frag, container, false);
        Long id = getArguments().getLong("CourseID");
        course = realm.where(Course.class).equalTo("id", id).findFirst();
        setLayout();
        initListViews();

        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                adapterAssignment.notifyDataSetChanged();
                adapterLabs.notifyDataSetChanged();
                adapterTest.notifyDataSetChanged();
            }
        };
        course.addChangeListener(changeListener);

        ImageButton addBtn = (ImageButton) getActivity().findViewById(R.id.addItem);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Add A Deliverable", "Button Clicked");
                addDeliverable();
            }
        });

        assignmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Send the course selected and open the coursePage fragment
                Course selectedCourse = courseResults.get(position);
                openCoursePage(selectedCourse);
            }
        });
        return rootView;
    }

    private void addDeliverable(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_deliverable_frag, null);

        DeliverableType = (Spinner) view.findViewById(R.id.DeliverableTypeSpinner);
        String[] items = new String[]{"Assignment", "Lab", "Test"};
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        DeliverableType.setAdapter(adapter);
        DeliverableName = (EditText) view.findViewById(R.id.DeliverableNameInput);
        DeliverableWeight = (EditText) view.findViewById(R.id.DeliverableWeightInput);
        Button okBtn = (Button) view.findViewById(R.id.Ok);

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

    private void setLayout() {
        String name = course.getName();
        String code = course.getCourseCode();
        TextView courseName = (TextView) rootView.findViewById(R.id.CourseName);
        courseName.setText(name);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText(code);
        //getCourseGrade, get CourseCompletion, getCourseAverage should all be called
    }

    private void initListViews() {
        RealmList<Deliverable> assignments = course.getAssignments();
        RealmList<Deliverable> labs = course.getLabs();
        RealmList<Deliverable> tests = course.getTests();

        adapterAssignment = new DeliverableAdapter(getActivity(), assignments);
        adapterLabs = new DeliverableAdapter(getActivity(), labs);
        adapterTest = new DeliverableAdapter(getActivity(), tests);

        ExpandableHeightListView assignmentsListView = (ExpandableHeightListView) rootView.findViewById(R.id.assignmentsList);
        assignmentsListView.setExpanded(true);
        ExpandableHeightListView labsListView = (ExpandableHeightListView) rootView.findViewById(R.id.LabsList);
        labsListView.setExpanded(true);
        ExpandableHeightListView testsListView = (ExpandableHeightListView) rootView.findViewById(R.id.TestsList);
        testsListView.setExpanded(true);

        assignmentsListView.setAdapter(adapterAssignment);
        labsListView.setAdapter(adapterLabs);
        testsListView.setAdapter(adapterTest);
    }

    private void createDeliverable(){
        realm.beginTransaction();

        String dN = DeliverableName.getText().toString();
        Double dW = Double.parseDouble(DeliverableWeight.getText().toString());
        String dT = DeliverableType.getSelectedItem().toString();

        Number currentIdNum = realm.where(Deliverable.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }

        Deliverable deliverable = realm.createObject(Deliverable.class, nextId);

        deliverable.setName(dN);
        deliverable.setType(dT);
        deliverable.setWeight(dW);
        if(dT.equals("Assignment")){
            course.getAssignments().add(deliverable);
        }
        else if(dT.equals("Lab")){
            course.getLabs().add(deliverable);
        }
        else if(dT.equals("Test")){
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
            Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }
}

