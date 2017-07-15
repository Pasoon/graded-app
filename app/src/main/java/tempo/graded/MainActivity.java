package tempo.graded;


import android.app.Fragment;
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
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private RealmResults<Course> courseResults;
    private ListView mListView;
    private Realm realm;
    CourseAdapter adapter;


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

}
