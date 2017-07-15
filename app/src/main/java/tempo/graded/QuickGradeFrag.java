package tempo.graded;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Zarif on 2017-07-12.
 */

public class QuickGradeFrag extends Fragment {

    private EditText currentGrade;
    private EditText examWeight;
    private EditText goal;
    private Button quickGrade;

    public static QuickGradeFrag newInstance() {
        QuickGradeFrag fragment = new QuickGradeFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quick_grad_frag, container, false);

        currentGrade = (EditText) rootView.findViewById(R.id.CurrentGrade);
        examWeight = (EditText) rootView.findViewById(R.id.ExamWeight);
        goal  = (EditText) rootView.findViewById(R.id.Goal);
        quickGrade = (Button) rootView.findViewById(R.id.CalculateBtn);
        return rootView;
    }

    private double calculateMark(double currentGrade, double examWeight, double goal){

        double gradeNeeded;

        currentGrade = currentGrade/100;
        examWeight = examWeight/100;
        goal = goal/100;


        gradeNeeded = (goal - (1.0 - examWeight)*(currentGrade))/examWeight; //ExamScore = Goal - (100 - ExamWeight) x (CurrentGrade) / Exam Weight
        gradeNeeded = gradeNeeded*100;

        return gradeNeeded;
    }

//    public void quickGradeButtonClicked(View v){
//
//        String currentGradeString = currentGrade.getText().toString();
//        String examWeightString = examWeight.getText().toString();
//        String goalString = goal.getText().toString();
//
//        if((currentGradeString.matches("")) || (examWeightString.matches("")) || (goalString.matches(""))) {
//            Context context = getApplicationContext();
//            CharSequence text = "Please fill in all required values.";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//        }
//
//        else {
//
//            double currentGradeDouble = Double.parseDouble(currentGradeString);
//            double examWeightDouble = Double.parseDouble(examWeightString);
//            double goalDouble = Double.parseDouble(goalString);
//
//            if( (currentGradeDouble > 100.0) || (examWeightDouble > 100.0) || (goalDouble > 100.0)){
//                Context context = getApplicationContext();
//                CharSequence text = "Please ensure you have entered a valid percentage";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//            }
//
//            else {
//                double gradeNeeded = calculateMark(currentGradeDouble, examWeightDouble, goalDouble);
//                SummaryFragment frag = new SummaryFragment();
//
//                Bundle args = new Bundle();
//                args.putDouble("Goal", goalDouble);
//                args.putDouble("Grade Needed", gradeNeeded);
//                frag.setCancelable(true);
//                frag.setArguments(args);
//                frag.show(getSupportFragmentManager(), frag.getTag());
//            }
//        }
//    }


}
