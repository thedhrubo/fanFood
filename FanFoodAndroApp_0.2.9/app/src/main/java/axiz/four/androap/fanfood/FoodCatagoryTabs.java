package axiz.four.androap.fanfood;


//import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import axiz.four.androap.fanfood.coustom_adapter.TabsPagerAdapter;
import axiz.four.androap.fanfood.emran_method.Emran_a;

/**
 * Created by emran on 4/18/15.
 */
public class FoodCatagoryTabs extends FragmentActivity implements ActionBar.TabListener{
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = { "All", "Hot Food", "Snacks", "Beverages" };

    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_itemtabe);
        Log.e("Page","invokedss");
        try{
         //   requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        }catch(Exception ex){}
        setContentView(R.layout.activity_itemtabe);
        mActivity = (Activity) this;
        mContext = (Context) this;
        emrnAObj = new Emran_a(mContext, mActivity);
//        Intent it = getIntent();
//        Bundle extras = it.getExtras();
//        int startTabPosition = 0;
//        if (extras != null) {
//            try {
////                startTabPosition = Integer.parseInt(extras
////                        .getString("ViewMode"));
//
//            } catch (Exception ex) {
//
//            }
//        }
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = (android.app.ActionBar)mActivity.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        if(actionBar==null){
            emrnAObj.alartBox("","is null");
        }else{
            emrnAObj.alartBox("","is not null");
        }
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setLogo(R.drawable.ic_launcher);

//        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
//        String title_txt = "Picture View";
//        viewPager.setAdapter(mAdapter);

//        ActionBar.Tab tb1 = actionBar.newTab();
//        tb1.setText(tabs[0]);
//        tb1.setTabListener(this);
//
//        ActionBar.Tab tb2 = actionBar.newTab();
//        tb2.setText(tabs[1]);
//        tb2.setTabListener(this);
//
//        ActionBar.Tab tb3 = actionBar.newTab();
//        tb3.setText(tabs[2]);
//        tb3.setTabListener(this);
//
//        ActionBar.Tab tb4 = actionBar.newTab();
//        tb4.setText(tabs[3]);
//        tb4.setTabListener(this);
//
//        actionBar.addTab(tb1);
//        actionBar.addTab(tb2);
//        actionBar.addTab(tb3);
//        actionBar.addTab(tb4);

//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

   @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
       Log.e("Page","invoked");
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
