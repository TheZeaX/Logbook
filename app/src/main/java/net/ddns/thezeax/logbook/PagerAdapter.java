package net.ddns.thezeax.logbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import net.ddns.thezeax.logbook.Tabs.Tab1;
import net.ddns.thezeax.logbook.Tabs.Tab2;
import net.ddns.thezeax.logbook.Tabs.Tab3;

public class PagerAdapter extends FragmentPagerAdapter {

    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.numberOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
