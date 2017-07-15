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
            final View rootView = inflater.inflate(R.layout.quick_grad_frag, container, false);

            currentGrade = (EditText)rootView.findViewById(R.id.CurrentGrade);
            examWeight = (EditText)rootView.findViewById(R.id.ExamWeight);
            goal  = (EditText)rootView.findViewById(R.id.Goal);
            quickGrade = (Button)rootView.findViewById(R.id.CalculateBtn);

            return rootView;
        }





}
