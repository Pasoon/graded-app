package tempo.graded;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private RealmResults<Course> courseResults;
    private ListView mListView;
    private Realm realm;
    CourseAdapter adapter;

    //quickgrade variables
    private EditText currentGrade;
    private EditText examWeight;
    private EditText goal;
    private Button quickGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, CourseHubFrag.newInstance());
        transaction.commit();
    }

    private void initListView() {
        adapter = new CourseAdapter(this,courseResults);
        mListView = (ListView) findViewById(R.id.courseList);
        mListView.setAdapter(adapter);
    }

    private void getCourses(){
        courseResults = realm.where(Course.class).findAll();;
        Log.i("Get Courses", "Got all courses.");
    }

     //Overwrite default back press to also close drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Handle navigation view item clicks here.
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment selectedFragment = null;
        TextView text = (TextView) toolbar.findViewById(R.id.toolbar_title);
        switch (item.getItemId()) {
            case R.id.course_hub_layout:
                text.setText("course hub");
                selectedFragment = CourseHubFrag.newInstance();
                break;
            case R.id.quick_grade_layout:
                selectedFragment = QuickGradeFrag.newInstance();
                text.setText("quick grade");
                break;
            case R.id.add_course_layout:
                selectedFragment= AddCourseFrag.newInstance();
                text.setText("add course");
                break;
        }
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, selectedFragment);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


    public void quickGradeButtonClicked(View v){

        currentGrade = (EditText)findViewById(R.id.CurrentGrade);
        examWeight = (EditText)findViewById(R.id.ExamWeight);
        goal  = (EditText)findViewById(R.id.Goal);
        quickGrade = (Button)findViewById(R.id.CalculateBtn);

        String currentGradeString = currentGrade.getText().toString();
        String examWeightString = examWeight.getText().toString();
        String goalString = goal.getText().toString();

        if((currentGradeString.matches("")) || (examWeightString.matches("")) || (goalString.matches(""))) {
            Context context = getApplicationContext();
            CharSequence text = "Please fill in all required values.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        else {

            double currentGradeDouble = Double.parseDouble(currentGradeString);
            double examWeightDouble = Double.parseDouble(examWeightString);
            double goalDouble = Double.parseDouble(goalString);

            if( (currentGradeDouble > 100.0) || (examWeightDouble > 100.0) || (goalDouble > 100.0)){
                Context context = getApplicationContext();
                CharSequence text = "Please ensure you have entered a valid percentage";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            else {
                double gradeNeeded = calculateMark(currentGradeDouble, examWeightDouble, goalDouble);
                SummaryFragment frag = new SummaryFragment();

                Bundle args = new Bundle();
                args.putDouble("Goal", goalDouble);
                args.putDouble("Grade Needed", gradeNeeded);
                frag.setCancelable(true);
                frag.setArguments(args);
                frag.show(getSupportFragmentManager(), frag.getTag());
            }
        }
    }
}
