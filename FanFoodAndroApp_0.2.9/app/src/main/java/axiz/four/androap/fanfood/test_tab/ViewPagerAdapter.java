package axiz.four.androap.fanfood.test_tab;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import axiz.four.androap.fanfood.drawerfeagmentclass.AllItemes;
import axiz.four.androap.fanfood.drawerfeagmentclass.BeveragesItems;
import axiz.four.androap.fanfood.drawerfeagmentclass.HotFoodItemes;
import axiz.four.androap.fanfood.drawerfeagmentclass.SnacksItems;
import axiz.four.androap.fanfood.sqlitedb.DB_SQLiteDB_Adapter;

/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Context mContext;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Context mContext) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.mContext = mContext;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

//        Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();

        if (position == 0) // if the position is 0 we are returning the First tab
        {
//            HotFoodItemes tab1 = new HotFoodItemes();
            DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext);
            AllItemes.currentItemQuantity = sqlObj.getCountist();

//            AllItemes.basket_count.setText(nuofitm);
//            AllItemes.basket_count.setText("123");
            AllItemes tab1 = new AllItemes();

            return tab1;
        }
         else if (position == 1) // if the position is 0 we are returning the First tab
        {
            // AllItemes tab1 = new AllItemes();
            DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext);
            HotFoodItemes.currentItemQuantity = sqlObj.getCountist();
            HotFoodItemes tab1 = new HotFoodItemes();
            return tab1;
        } else if (position == 2) // if the position is 0 we are returning the First tab
        {
            DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext);
            SnacksItems.currentItemQuantity = sqlObj.getCountist();
            SnacksItems tab1 = new SnacksItems();
            return tab1;
        } else if (position == 3) // if the position is 0 we are returning the First tab
        {
            DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext);
            BeveragesItems.currentItemQuantity = sqlObj.getCountist();
            BeveragesItems tab1 = new BeveragesItems();
            return tab1;
        }
//        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
//        {
//            Tab2 tab2 = new Tab2();
//            return tab2;
//        }
    return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}