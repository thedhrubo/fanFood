package axiz.four.androap.fanfood.test_tab;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import axiz.four.androap.fanfood.Communicator;
import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.emran_method.Emran_a;

/**
 * Created by emran on 5/14/15.
 */
public class MainTab  extends Fragment {

    private Activity mActivity;
    private Context mContext;
    private ActionBar actionBar;
    private Emran_a emrnAObj;
    private Communicator TransferCom;

    //----------------------------------
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"All", "Hot Food", "Snacks", "Drinks"};
    int Numboftabs =Titles.length;
    TextView allItems_sltvnu_txtTopTitle;

    public MainTab(){}

//    public MainTab(Context mContext, Activity mActivity){
//
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TransferCom = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        mContext = rootView.getContext();
        mActivity = getActivity();
        actionBar = (android.app.ActionBar)mActivity.getActionBar();
        emrnAObj = new Emran_a(mContext, mActivity);

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "font/impact.ttf");
        allItems_sltvnu_txtTopTitle = (TextView) rootView.findViewById(R.id.tab_sltvnu_txtTopTitle);
        allItems_sltvnu_txtTopTitle.setTypeface(tf);

        toolbar = (Toolbar) rootView.findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
//        getChildFragmentManager();
        adapter =  new ViewPagerAdapter(getChildFragmentManager(),Titles, Numboftabs, mContext);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) rootView.findViewById(R.id.tabs);

//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                Toast.makeText(mContext,">: "+position, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {

                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        return rootView;
    }


}
