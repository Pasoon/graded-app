package tempo.graded;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    private TextView titleText;
    private ImageButton add;
    //private AdView mAdView;
    //private InterstitialAd mInterstitialAd;

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


        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        //mAdView = (AdView) findViewById(R.id.adView);
        //mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-8182480327717122/5079384832");

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        //AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        //mAdView.loadAd(adRequest);
        //mInterstitialAd.loadAd(adRequest);
        //mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded(){
//                displayInterstitial();
//            }
//
//
//        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        add = (ImageButton) toolbar.findViewById(R.id.addItem);
        titleText.setText("course hub");
        add.setVisibility(View.VISIBLE);
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, CourseHubFrag.newInstance());
        transaction.commit();
    }

     //Overwrite default back press to also close drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    //Minimizes keyboard when touch on screen
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    // Handle navigation view item clicks here.
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.course_hub_layout:
                titleText.setText("course hub");
                add.setVisibility(View.VISIBLE);
                selectedFragment = CourseHubFrag.newInstance();
                break;
            case R.id.quick_grade_layout:
                titleText.setText("quick grade");
                add.setVisibility(View.GONE);
                selectedFragment = QuickGradeFrag.newInstance();
                break;
            case R.id.help_layout:
                titleText.setText("help");
                add.setVisibility(View.GONE);
                selectedFragment = HelpFrag.newInstance();
                break;
            case R.id.about_layout:
                titleText.setText("about");
                add.setVisibility(View.GONE);
                selectedFragment = AboutFrag.newInstance();
                break;
        }
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, selectedFragment);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    public void displayInterstitial(){
//        if(mInterstitialAd.isLoaded()){
//            mInterstitialAd.show();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//
//        }
//        super.onPause();
//
//    }
//
//    /** Called when returning to the activity */
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mAdView != null) {
//            mAdView.resume();
//
//        }
//    }
//
//    /** Called before the activity is destroyed */
//    @Override
//    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//        super.onDestroy();
//    }

}
