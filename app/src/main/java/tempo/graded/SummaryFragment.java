package tempo.graded;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * Created by Pasoon on 2017-07-12.
 */

public class SummaryFragment extends DialogFragment {

    double pStatus = 0;
    double pSuccess = 0;
    private Handler handler = new Handler();
    TextView tv;
    TextView goalTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.summary_frag, container, false);

        Double gradeNeeded = getArguments().getDouble("Grade Needed");
        Double goal = getArguments().getDouble("Goal");


        System.out.println(goal);
        System.out.println(gradeNeeded);

        goalTextView = (TextView)rootView.findViewById(R.id.zarif);

        goalTextView.setText("To get a overall mark of "+new DecimalFormat("##.##").format(Double.parseDouble(goal.toString()))+"% you need to score at least:");

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circularprogress);
        final ProgressBar mProgress = (ProgressBar) rootView.findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        pSuccess = gradeNeeded;


      /*  ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/

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
                        Thread.sleep(3); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return rootView;
    }

}