package axiz.four.androap.fanfood.coustom_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import axiz.four.androap.fanfood.drawerfeagmentclass.AllItemes;
import axiz.four.androap.fanfood.drawerfeagmentclass.BeveragesItems;
import axiz.four.androap.fanfood.drawerfeagmentclass.HotFoodItemes;
import axiz.four.androap.fanfood.drawerfeagmentclass.SnacksItems;

/**
 * Created by emran on 4/18/15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                // Top Rated fragment activity
                try {
                    return new AllItemes();
                } catch (Exception ex) {
                    System.out.println("\n\t ### GridActivity ### ");
                    return null;
                }
            }
            case 1: {
                // Games fragment activity
                try {
                    return new HotFoodItemes();
                } catch (Exception ex) {
                    System.out.println("\n\t ### List exception ### ");
                    return null;
                }
            }
            case 2:{
                // Movies fragment activity
                try {
                    return new SnacksItems();
                } catch (Exception ex) {
                    System.out.println("\n\t ### DayActivity ### ");
                    return null;
                }
            }
            case 3:{
                // Movies fragment activity
                try {
                    return new BeveragesItems();
                } catch (Exception ex) {
                    System.out.println("\n\t ### DayActivity ### ");
                    return null;
                }
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
