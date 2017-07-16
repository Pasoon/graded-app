package tempo.graded;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * Created by Pasoon on 2017-07-12.
 */

public class SummaryFragment extends Fragment {

    double pStatus = 0;
    double pSuccess = 0;
    private Handler handler = new Handler();
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.summary_frag, container, false);

        Double gradeNeeded = getArguments().getDouble("Grade Needed");
        Double goal = getArguments().getDouble("Goal");


        System.out.println(goal);
        System.out.println(gradeNeeded);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circularprogress);
        final ProgressBar mProgress = (ProgressBar) rootView.findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        pSuccess = gradeNeeded;


        tv = (TextView) rootView.findViewById(R.id.tv);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < pSuccess) {
                    pStatus += 1.1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress((int)pStatus);
                            tv.setText(new DecimalFormat("##.##").format(pStatus)+"%");

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(20); //thread will take approx 5 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return rootView;
    }

}