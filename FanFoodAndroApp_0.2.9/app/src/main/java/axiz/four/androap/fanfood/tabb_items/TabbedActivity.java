package axiz.four.androap.fanfood.tabb_items;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Locale;
import axiz.four.androap.fanfood.R;

public class TabbedActivity extends Fragment {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String TAG = TabbedActivity.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public static TabbedActivity newInstance() {
        return new TabbedActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getActivity(),"TabActivity executed",Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_item_one, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Log.e("Tab","invoked");
        Toast.makeText(getActivity(),"TabActivity executed",Toast.LENGTH_LONG).show();
        return v;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                    case 0:
                    return "All".toUpperCase(l);
                    case 1:
            return "Hot Food".toUpperCase(l);
    case 2:
            return "Snacks".toUpperCase(l);
    case 3:
            return "Beverages".toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_tabbed_dummy,
//                    container, false);
            View rootView = inflater.inflate(R.layout.fragment_item_tabs, container, false);
//            TextView dummyTextView = (TextView) rootView
//                    .findViewById(R.id.section_label);
//            dummyTextView.setText(Integer.toString(getArguments().getInt(
//                    ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}




//    case 0:
//            return "All".toUpperCase(l);
//    case 1:
//            return "Hot Food".toUpperCase(l);
//    case 2:
//            return "Snacks".toUpperCase(l);
//    case 3:
//            return "Beverages".toUpperCase(l);
//}