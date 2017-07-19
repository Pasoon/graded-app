package tempo.graded;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Zarif on 2017-07-12.
 */

public class CourseAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private RealmResults<Course> mDataSource;
    private Realm realm;
    private AlertDialog dialog;
    private Course course;

    public CourseAdapter(Context context, RealmResults<Course> items) {
        mContext = context;
        mDataSource = items;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Course getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, final ViewGroup parent) {
        course = getItem(position);
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_courses, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.delete));
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callConfirmationDialog();
            }
        });
        v.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEditDialog();
            }
        });
        return v;
    }

    private void callEditDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_course_frag, null);
        final EditText name = (EditText) view.findViewById(R.id.CourseNameInput);
        final EditText code = (EditText) view.findViewById(R.id.CourseCodeInput);
        name.setText(course.getName());
        code.setText(course.getCourseCode());
        Button okBtn = (Button) view.findViewById(R.id.done);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCourse(name.getText().toString(), code.getText().toString());
            }
        });
        alertBuilder.setView(view);
        dialog = alertBuilder.create();
        dialog.show();
    }

    private void editCourse(String name, String code) {
        if(name.isEmpty() || code.isEmpty()){
            Toast.makeText(mContext, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            realm.beginTransaction();
            course.setName(name);
            course.setCourseCode(code);
            realm.commitTransaction();
            dialog.dismiss();

        }


    }

    private void callConfirmationDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.confirmation_dialog, null);
        Button okBtn = (Button) view.findViewById(R.id.delete_dialog);
        Button cancelBtn = (Button) view.findViewById(R.id.Cancel_dialog);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        alertBuilder.setView(view);
        dialog = alertBuilder.create();
        dialog.show();
    }

    private void deleteCourse() {
        dialog.dismiss();
        realm.beginTransaction();
        Log.i("DeleteCourse:", "Deleting " + course.getCourseCode());
        course.deleteFromRealm();
        Log.i("DeleteCourse:", "Deleted course");
        realm.commitTransaction();
        Toast.makeText(mContext, "Course Deleted.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillValues(int position, View convertView) {
        ViewHolder holder;
        if(convertView.getTag()==null){
            holder = new ViewHolder();
            holder.courseName= (TextView) convertView.findViewById(R.id.courseList_name);
            holder.gradeColor = (RelativeLayout) convertView.findViewById(R.id.courseList_gradeColor);
            holder.courseGrade = (TextView) convertView.findViewById(R.id.courseList_grade);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        }
        else {
            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        TextView courseName = holder.courseName;
        RelativeLayout gradeColor = holder.gradeColor;
        TextView courseGrade = holder.courseGrade;


        //Get corresponding habit for row
        courseName.setText(course.getCourseCode());

        courseGrade.setText(course.getLetterGrade()); //Need to implement course.getGrade() and course.getColor().
        //Based on grade, we would set the color.
        if(course.getGradeColor().equals("grade_a")){
            gradeColor.setBackgroundResource(R.color.grade_a);
        }
        else if(course.getGradeColor().equals("grade_b")){
            gradeColor.setBackgroundResource(R.color.grade_b);
        }
        else if(course.getGradeColor().equals("grade_c")){
            gradeColor.setBackgroundResource(R.color.grade_c);
        }
        else if(course.getGradeColor().equals("grade_d")){
            gradeColor.setBackgroundResource(R.color.grade_d);
        }
        else if(course.getGradeColor().equals("grade_f")){
            gradeColor.setBackgroundResource(R.color.grade_f);
        }
        else{
            gradeColor.setBackgroundResource(R.color.colorAccent);
        }
    }

    private static class ViewHolder {
        public TextView courseName;
        public RelativeLayout gradeColor;
        public TextView courseGrade;
    }

}
