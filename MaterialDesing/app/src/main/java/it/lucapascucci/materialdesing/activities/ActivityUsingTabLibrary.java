package it.lucapascucci.materialdesing.activities;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.lucapascucci.materialdesing.R;
import it.lucapascucci.materialdesing.extras.SortListener;
import it.lucapascucci.materialdesing.fragments.FragmentBoxOffice;
import it.lucapascucci.materialdesing.fragments.FragmentSearch;
import it.lucapascucci.materialdesing.fragments.FragmentUpcoming;
import it.lucapascucci.materialdesing.services.MyService;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.os.PersistableBundle;


public class ActivityUsingTabLibrary extends ActionBarActivity implements MaterialTabListener ,View.OnClickListener{

    public static final int MOVIES_SEARCH_RESULT = 0;
    public static final int MOVIES_HITS = 1;
    public static final int MOVIES_UPCOMING = 2;
    public static final int TAB_COUNT = 3;

    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_RATINGS = "sortRatings";
    private static final int JOB_ID =  100;


    private JobScheduler mJobScheduler;
    private MaterialTabHost materialTabHost;
    private ViewPager viewPager;
    private MyViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_using_tab_library);

        mJobScheduler = JobScheduler.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                constructJob();
            }
        },30000);
        constructJob();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        materialTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                materialTabHost.setSelectedNavigationItem(position);
            }
        });
        for (int pos = 0; pos < viewPagerAdapter.getCount(); pos++){
            materialTabHost.addTab(
                    materialTabHost.newTab()
                            .setIcon(viewPagerAdapter.getIcon(pos))
                            .setTabListener(this));
        }

        buildFAB();

    }

    private void buildFAB(){
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_action_important);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));

        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();
        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortName.setOnClickListener(this);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortDate.setOnClickListener(this);
        buttonSortRatings.setTag(TAG_SORT_RATINGS);
        buttonSortRatings.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(actionButton)
                .build();
    }

    private void constructJob(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,new ComponentName(this, MyService.class));
        builder.setPeriodic(2000)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED )
        .setPersisted(true);

        mJobScheduler.schedule(builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_using_tab_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.home){
            //da utilizzare solo se le due activity sono imparentate
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) viewPagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (fragment instanceof SortListener){
            if (v.getTag().equals(TAG_SORT_NAME)){
                ((SortListener) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_SORT_DATE)){
                ((SortListener) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATINGS)){
                ((SortListener) fragment).onSortByRating ();
            }
        }

    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.home,R.drawable.personal,R.drawable.article};
        public MyViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position){
            Fragment fragment = null;
            switch (position){
                case MOVIES_SEARCH_RESULT:
                    fragment = FragmentSearch.newInstance("","");
                    break;

                case MOVIES_HITS:
                    fragment = FragmentBoxOffice.newInstance("","");
                    break;

                case MOVIES_UPCOMING:
                    fragment = FragmentUpcoming.newInstance("","");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        private Drawable getIcon(int position){
           return getResources().getDrawable(icons[position]);
        }
    }
}
