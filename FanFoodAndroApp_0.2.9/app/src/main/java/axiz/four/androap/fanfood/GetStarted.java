package axiz.four.androap.fanfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import axiz.four.androap.fanfood.drawer_navigation.NavDrawerItem;
import axiz.four.androap.fanfood.drawer_navigation.NavDrawerListAdapter;
import axiz.four.androap.fanfood.drawerfeagmentclass.AccountInfo;
import axiz.four.androap.fanfood.drawerfeagmentclass.AllItemes;
import axiz.four.androap.fanfood.drawerfeagmentclass.ConcessionS;
import axiz.four.androap.fanfood.drawerfeagmentclass.ConcessionsFavorite;
import axiz.four.androap.fanfood.drawerfeagmentclass.FAQOrder;
import axiz.four.androap.fanfood.drawerfeagmentclass.NotificationOrder;
import axiz.four.androap.fanfood.drawerfeagmentclass.OrderHistory;
import axiz.four.androap.fanfood.drawerfeagmentclass.SelectVanue;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.test_tab.MainTab;

import android.app.AlertDialog.Builder;


/**
 * Created by emran on 4/7/15.
 */
public class GetStarted extends ActionBarActivity implements Communicator {

    private ActionBar actionBar;
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    // ---------- Declar Drawer instance ---------
    private static final String TAG = MainLoginActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private NavDrawerListAdapter adapter;
    // ------------- end Drawer instance -----------


    private int Frg_no = 0;
    private FragmentManager FRAGMENTmanager;
    String User_ID, UserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_get_started);

        // ****************************************************************
        // ******************** Initialize components *********************
        FRAGMENTmanager = getSupportFragmentManager();
        mContext = (Context) this;
        mActivity = (Activity) this;
        emrnAObj = new Emran_a(mContext, mActivity);


        User_ID = emrnAObj.getUseValue("User_ID");
        UserEmail = emrnAObj.getUseValue("UserEmail");

        actionBar = getSupportActionBar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // **************************************************
        // ********** Start Drawer initialization **********
        mTitle = mDrawerTitle = getTitle();
        // mDrawerItmes = getResources().getStringArray(R.array.drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        addDrawerItems();
        setupDrawer();
        displayView(1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


//        try{
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//            getActionBar().setHomeButtonEnabled(true);
//        }catch(Exception ex){
//            emrnAObj.alartBox("",""+ex.toString());
//        }
    }

    private void addDrawerItems() {
        navDrawerItems = new ArrayList<NavDrawerItem>();

        navMenuTitles = getResources().getStringArray(R.array.drawer_titles);
        // getting Navigation drawer icons from res
        navMenuIcons = getResources().obtainTypedArray(R.array.drawer_icons);

        // list item in slider at 1 Home Nasdaq details
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
//                .getResourceId(0, -1)));
        // list item in slider at 2 Facebook details
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
                .getResourceId(1, -1)));
        // list item in slider at 3 Google details
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
                .getResourceId(2, -1)));
        // list item in slider at 4 Apple details
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
                .getResourceId(3, -1)));
        // list item in slider at 5 Microsoft details
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
                .getResourceId(4, -1)));
        // list item in slider at 6 LinkedIn details
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
                .getResourceId(5, -1)));
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
//                .getResourceId(6, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
                .getResourceId(7, -1)));
        if (User_ID.equals("0")) {
            navDrawerItems.add(new NavDrawerItem("Good Bye", navMenuIcons
                    .getResourceId(8, -1)));
        } else {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
                    .getResourceId(8, -1)));
        }

        // Recycle array
        navMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting list adapter for Navigation Drawer
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                displayView(position);

            }
        });
//        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                //  getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // Setting, Refresh and Rate App
            }

            public void onDrawerOpened(View drawerView) {
//                 getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /**
     * Slider menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected item
            displayView(position);
        }
    }

    private void displayView(int position) {
        // update the main content with called Fragment
        Fragment fragment = null;
        switch (position) {
            case 0: {
                // fragment = new Fragment1Nasdaq();
                setTitle("FANFOOD");
                mTitle = mDrawerTitle = "FANFOOD";
                Frg_no = 0;

                fragment = new AccountInfo();
                break;
            }
            case 1: {
                setTitle("FANFOOD");
                mTitle = mDrawerTitle = "FANFOOD";
                actionBar.setTitle(mTitle);
                Frg_no = 1;
                fragment = new SelectVanue();
                break;
            }
            case 2: {
                // fragment = new Fragment3Google();
                mTitle = mDrawerTitle = "FANFOOD";
                actionBar.setTitle(mTitle);
//                emrnAObj.alartBox("","Hi vi");
                fragment = new OrderHistory();

                break;
            }
            case 3: {
                // fragment = new Fragment4Apple();
                setTitle("FANFOOD");
                mTitle = mDrawerTitle = "FANFOOD";
                actionBar.setTitle(mTitle);
                fragment = new NotificationOrder();
                Frg_no = 3;
                // fragment = new OrderHistory();
                break;
            }
            case 4: {
                // fragment = new Fragment5Microsoft();
                Frg_no = 4;
                setTitle("FANFOOD");
                mTitle = mDrawerTitle = "FANFOOD";
                actionBar.setTitle(mTitle);
                fragment = new FAQOrder();

                break;
            }
            case 5: {
                Frg_no = 5;
                setTitle("FANFOOD");
                mTitle = mDrawerTitle = "FANFOOD";
                btnRateAppOnClick();
//                fragment = new SelectVanueFvrt();
                //btnRateAppOnClick();
                break;
            }
            case 6: {
                Frg_no = 6;
                setTitle("FANFOOD");
                mTitle = mDrawerTitle = "FANFOOD";
                // fragment = new Fragment6LinkedIn();
//                btnRateAppOnClick();
                LogOut();
                break;
            }
            case 7: {
                Frg_no = 7;
                setTitle("FANFOOD");
                mTitle = mDrawerTitle = "FANFOOD";
                // fragment = new Fragment6LinkedIn();
//                btnRateAppOnClick();
//                LogOut();
                break;
            }

            default: {
                break;
            }
        }

        if (fragment != null) {
            FRAGMENTmanager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.content_frame, fragment).commit();
            FragmentTransaction transaction = FRAGMENTmanager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment);

            transaction.commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            Log.e("this is mainActivity", "Error in else case");
        }
    }

    // Google play store
    //On click event for rate this app button
//    public void btnRateAppOnClick(View v) {
    public void btnRateAppOnClick() {
        String GooglePlay_store_Url_id = "https://play.google.com/store/apps/details?id=axiz.four.androap.fanfood";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Try Google play
        intent.setData(Uri.parse("market://details?id=axiz.four.androap.fanfood"));
        if (!MyStartActivity(intent)) {
            //Market (Google play) app seems not installed, let's try to open a webbrowser
            intent.setData(Uri.parse(GooglePlay_store_Url_id));
            if (!MyStartActivity(intent)) {
                //Well if this also fails, we have run out of options, inform the user.
                Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean MyStartActivity(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }


    //---------------------- fragment part----------------------
    //-------------------- Load Concession --------------------
    public void LoadConcession() {

        ConcessionS fragConcession = new ConcessionS();
        FRAGMENTmanager = getSupportFragmentManager();
        FragmentTransaction transaction = FRAGMENTmanager.beginTransaction();
        Frg_no = 100;//100 for concession
        transaction.add(R.id.content_frame, fragConcession);
        transaction.addToBackStack("ConcessionS");
//        actionBar.setTitle("SELECT CONCESSION");
        transaction.commit();
    }

    //-------------------- Load Concession --------------------
    public void LoadTabItems() {

        //TabbedActivity fragConcession = new TabbedActivity();
        MainTab fragConcession = new MainTab();
        FRAGMENTmanager = getSupportFragmentManager();
        FragmentTransaction transaction = FRAGMENTmanager.beginTransaction();
        Frg_no = 102;//100 for concession
        transaction.add(R.id.content_frame, fragConcession);
        transaction.addToBackStack("ConcessionS");
//        actionBar.setTitle("SELECT CONCESSION");
        transaction.commit();
    }

    //-------------------- Load AllItems --------------------
    public void LoadAllItemes() {
        AllItemes fragConcession = new AllItemes();
        FRAGMENTmanager = getSupportFragmentManager();
        FragmentTransaction transaction = FRAGMENTmanager.beginTransaction();
        Frg_no = 101;//101 for AllItemes
        transaction.add(R.id.content_frame, fragConcession);
        transaction.addToBackStack("AllItemes");
//        actionBar.setTitle("SELECT MENU");
        transaction.commit();
    }
    //

    public void LoadFavoriteCocessionItem() {
//        Favorite fragConcession = new Favorite();
        ConcessionsFavorite fragConcession = new ConcessionsFavorite();
        FRAGMENTmanager = getSupportFragmentManager();
        FragmentTransaction transaction = FRAGMENTmanager.beginTransaction();
        Frg_no = 104;//101 for AllItemes
        transaction.add(R.id.content_frame, fragConcession);
        transaction.addToBackStack("ConcessionFavorite");
//        actionBar.setTitle("SELECT MENU");
        transaction.commit();
    }

    //-------------------- Load Favorite --------------------
    public void LoadFavoriteCocession() {
//        Favorite fragConcession = new Favorite();
        ConcessionsFavorite fragConcession = new ConcessionsFavorite();
        FRAGMENTmanager = getSupportFragmentManager();
        FragmentTransaction transaction = FRAGMENTmanager.beginTransaction();
        Frg_no = 103;//101 for AllItemes
        transaction.add(R.id.content_frame, fragConcession);
        transaction.addToBackStack("SelectFavorite");
//        actionBar.setTitle("SELECT MENU");
        transaction.commit();
    }

    //Fregment selecter
    @Override
    public void frgSwitcher(String emr) {
        if (emr.equals("SelectVenue")) {
            LoadConcession();
        } else if (emr.equals("ConcessionS")) {
//            LoadAllItemes();
            LoadTabItems();
        } else if (emr.equals("SelectFavorite")) {
            LoadFavoriteCocession();
        } else if (emr.equals("ConcessionFavorite")) {
            LoadFavoriteCocessionItem();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    // When back

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                if (Frg_no == 2) {
                    String ItemShow = emrnAObj.getUseValue("ItemShow");
//            if(ItemShow==null){
//                emrnAObj.alartBox("","Hi");
//            }
                    if (emrnAObj.getUseValue("ItemShow").equals("NO")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
                //  return false;
            } catch (Exception ex) {
                return false;
            }

        } else
            return false;
    }

    private void LogOut() {
        Builder ad = new AlertDialog.Builder(GetStarted.this);
        ad.setTitle("Log out");
        ad.setMessage("Are you sure want to logout?");
        // set Button: PositiveButton
        ad.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //startActivity(emrAObj.Start_Activity2(MainActivity.this, StartActivity.class));
                finish();
            }
        });

        // set Button: setNegativeButton
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.show();

    }
}
