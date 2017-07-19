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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import org.honorato.multistatetogglebutton.MultiStateToggleButton;


import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;


/**
 * Created by Pasoon on 2017-07-15.
 */

public class SelectedCourseFrag extends Fragment {

    private View rootView;
    private Realm realm;
    private EditText DeliverableName;
    private EditText DeliverableWeight;
    private AlertDialog dialog;
    private Course course;
    private DeliverableAdapter adapterAssignment;
    private DeliverableAdapter adapterLabs;
    private DeliverableAdapter adapterTest;
    private ImageButton toggleTests;
    private ImageButton toggleLabs;
    private ImageButton toggleAssignments;
    private MultiStateToggleButton dType;
    private Toolbar toolbar;


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
        updatePage();
        setLayout();
        ImageButton addBtn = (ImageButton) getActivity().findViewById(R.id.addItem);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Add A Deliverable", "Button Clicked");
                addDeliverable();
            }
        });

        return rootView;
    }

    private void addDeliverable(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_deliverable_frag, null);
        dType = (MultiStateToggleButton) view.findViewById(R.id.mstb_multi_id);
        dType.setValue(0); //Set default button to assignment.
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
        //Setup action bar information
        String code = course.getCourseCode();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        System.out.println(toolbar);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText(code);


        TextView courseGrade = (TextView) rootView.findViewById(R.id.Grade);
        TextView courseLetterGrade = (TextView) rootView.findViewById(R.id.GradeLetter);
        TextView courseName = (TextView) rootView.findViewById(R.id.CourseName);

        if(course.getCourseCompletion() == 0){
            courseGrade.setText("-");
            courseLetterGrade.setText("-");
        }

        courseName.setText(course.getName());
        //setup list views
        initListViews();

        //setup expandable buttons
        setUpAssignmentExpandableLists();
        setUpLabsExpandableLists();
        setUpTestsExpandableLists();

    }

    private void updatePage(){
        String name = course.getName();
        String letterGrade = course.getLetterGrade();
        Double grade = course.getGrade();
        Double completion = course.getCourseCompletion();

        TextView courseName = (TextView) rootView.findViewById(R.id.CourseName);
        TextView courseGrade = (TextView) rootView.findViewById(R.id.Grade);
        TextView courseLetterGrade = (TextView) rootView.findViewById(R.id.GradeLetter);
        TextView courseCompletion = (TextView) rootView.findViewById(R.id.CourseCompletion);

        if(completion == 0){
            courseGrade.setText("-");
            courseLetterGrade.setText("-");
            courseCompletion.setText(new DecimalFormat("##.##").format(completion)+"%");
        }
        else{
            courseName.setText(name);
            courseGrade.setText(new DecimalFormat("##.##").format(grade)+"%");
            courseLetterGrade.setText(letterGrade);
            courseCompletion.setText(new DecimalFormat("##.##").format(completion)+"%");
        }
    }


    private void toggleVisibilityAndSetCaret(LinearLayout layout, ImageButton toggle){
        if(layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
            toggle.setImageResource(R.drawable.expand_up);
        }
        else {
            layout.setVisibility(View.GONE);
            toggle.setImageResource(R.drawable.expand_down);
        }
    }

    private void setUpTestsExpandableLists() {
        toggleTests = (ImageButton) rootView.findViewById(R.id.toggleTests);
        toggleTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Tests Button", "Clicked");
                LinearLayout tests = (LinearLayout) rootView.findViewById(R.id.tests);
                 toggleVisibilityAndSetCaret(tests, toggleTests);
            }
        });
    }

    private void setUpLabsExpandableLists() {
        toggleLabs = (ImageButton) rootView.findViewById(R.id.toggleLabs);
        toggleLabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Labs Button", "Clicked");
                LinearLayout labs = (LinearLayout) rootView.findViewById(R.id.labs);
                 toggleVisibilityAndSetCaret(labs, toggleLabs);
            }
        });
    }

    private void setUpAssignmentExpandableLists() {
        toggleAssignments = (ImageButton) rootView.findViewById(R.id.toggleAssignment);
        toggleAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Assignment Button", "Clicked");
                LinearLayout assignments = (LinearLayout) rootView.findViewById(R.id.assignments);
                 toggleVisibilityAndSetCaret(assignments, toggleAssignments);
            }
        });
    }

    private void initListViews() {
        final RealmList<Deliverable> assignments = course.getAssignments();
        final RealmList<Deliverable> labs = course.getLabs();
        final RealmList<Deliverable> tests = course.getTests();

        adapterAssignment = new DeliverableAdapter(getActivity(), assignments);
        adapterLabs = new DeliverableAdapter(getActivity(), labs);
        adapterTest = new DeliverableAdapter(getActivity(), tests);

        final ExpandableHeightListView assignmentsListView = (ExpandableHeightListView) rootView.findViewById(R.id.assignmentsList);
        assignmentsListView.setExpanded(true);
        final ExpandableHeightListView labsListView = (ExpandableHeightListView) rootView.findViewById(R.id.LabsList);
        labsListView.setExpanded(true);
        final ExpandableHeightListView testsListView = (ExpandableHeightListView) rootView.findViewById(R.id.TestsList);
        testsListView.setExpanded(true);

        assignmentsListView.setAdapter(adapterAssignment);
        labsListView.setAdapter(adapterLabs);
        testsListView.setAdapter(adapterTest);


        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                adapterAssignment.notifyDataSetChanged();
                adapterLabs.notifyDataSetChanged();
                adapterTest.notifyDataSetChanged();
                updatePage();

            }
        };
        course.addChangeListener(changeListener);

        assignmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {
                //Send the course selected and open the coursePage fragment
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                view = getActivity().getLayoutInflater().inflate(R.layout.enter_grade_frag,null);
                final EditText deliverableGrade = (EditText) view.findViewById(R.id.DeliverableGradeInput);
                Button enterBtn = (Button) view.findViewById(R.id.Enter);

                enterBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        realm.beginTransaction();
                        if(!deliverableGrade.getText().toString().isEmpty() && Double.parseDouble(deliverableGrade.getText().toString()) <= 100){
                            Deliverable selectedDeliverable = assignments.get(position);
                            selectedDeliverable.setGrade(Double.parseDouble(deliverableGrade.getText().toString()));
                            Toast.makeText(getActivity(), "Grade Added!", Toast.LENGTH_SHORT).show();
                            assignmentsListView.setAdapter(adapterAssignment);
                            dialog.dismiss();
                        }

                        else{
                            Toast.makeText(getActivity(), "Please Enter a Valid Grade (0 - 100)", Toast.LENGTH_SHORT).show();
                        }
                        course.calculateGrade();
                        realm.commitTransaction();

                    }
                });

                alertBuilder.setView(view);
                dialog = alertBuilder.create();
                dialog.show();

            }
        });

        labsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                view = getActivity().getLayoutInflater().inflate(R.layout.enter_grade_frag,null);
                final EditText deliverableGrade = (EditText) view.findViewById(R.id.DeliverableGradeInput);
                Button enterBtn = (Button) view.findViewById(R.id.Enter);

                enterBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        realm.beginTransaction();
                        if(!deliverableGrade.getText().toString().isEmpty() && Double.parseDouble(deliverableGrade.getText().toString()) <= 100){
                            Deliverable selectedDeliverable = labs.get(position);
                            selectedDeliverable.setGrade(Double.parseDouble(deliverableGrade.getText().toString()));
                            Toast.makeText(getActivity(), "Grade Added!", Toast.LENGTH_SHORT).show();
                            labsListView.setAdapter(adapterLabs);
                            dialog.dismiss();
                        }

                        else{
                            Toast.makeText(getActivity(), "Please Enter a Valid Grade (0 - 100)", Toast.LENGTH_SHORT).show();
                        }
                        course.calculateGrade();
                        realm.commitTransaction();
                    }
                });

                alertBuilder.setView(view);
                dialog = alertBuilder.create();
                dialog.show();

            }
        });

        testsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                view = getActivity().getLayoutInflater().inflate(R.layout.enter_grade_frag,null);
                final EditText deliverableGrade = (EditText) view.findViewById(R.id.DeliverableGradeInput);
                Button enterBtn = (Button) view.findViewById(R.id.Enter);

                enterBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        realm.beginTransaction();
                        if(!deliverableGrade.getText().toString().isEmpty() && Double.parseDouble(deliverableGrade.getText().toString()) <= 100){
                            Deliverable selectedDeliverable = tests.get(position);
                            selectedDeliverable.setGrade(Double.parseDouble(deliverableGrade.getText().toString()));
                            Toast.makeText(getActivity(), "Grade Added!", Toast.LENGTH_SHORT).show();
                            testsListView.setAdapter(adapterTest);
                            dialog.dismiss();
                        }

                        else{
                            Toast.makeText(getActivity(), "Please Enter a Valid Grade (0 - 100)", Toast.LENGTH_SHORT).show();
                        }
                        course.calculateGrade();
                        realm.commitTransaction();
                    }
                });

                alertBuilder.setView(view);
                dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

    private void createDeliverable(){

        int i = dType.getValue();
        String dN = DeliverableName.getText().toString();
        Double dW = Double.parseDouble(DeliverableWeight.getText().toString());

        Number currentIdNum = realm.where(Deliverable.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        Deliverable deliverable = realm.createObject(Deliverable.class, nextId);
        deliverable.setName(dN);
        deliverable.setWeight(dW);

        System.out.println("INSIDE CREATE");
        switch (i) {
            case 0:
                deliverable.setType("Assignment");
                course.getAssignments().add(deliverable);
                break;
            case 1:
                deliverable.setType("Lab");
                course.getLabs().add(deliverable);
                break;
            case 2:
                deliverable.setType("Test");
                course.getTests().add(deliverable);
                break;
        }

    }


    public void okBtnClicked() {

        realm.beginTransaction();
        course.calculateTotalWeight();
        Double deliverableWeight = Double.parseDouble(DeliverableWeight.getText().toString());
        Double weightLimit = deliverableWeight + course.getTotalWeight();
        if (!DeliverableWeight.getText().toString().isEmpty() && !DeliverableName.getText().toString().isEmpty()
                && Double.parseDouble(DeliverableWeight.getText().toString()) <= 100 && weightLimit <= 100){
            createDeliverable();
            Toast.makeText(getActivity(), "Deliverable Added!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            updatePage();

        } else {

            if(weightLimit > 100){
                Toast.makeText(getActivity(), "You have exceeded 100% weight limit for the course.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Please fill in all fields with correct values", Toast.LENGTH_SHORT).show();
            }
        }
        realm.commitTransaction();

    }
}

