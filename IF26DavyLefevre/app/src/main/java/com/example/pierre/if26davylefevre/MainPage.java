package com.example.pierre.if26davylefevre;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class MainPage extends Activity {


    ActionBar.Tab tab1, tab2, tab3;
    Fragment fragmentTab1 = new Tab1Fragment();
    Fragment fragmentTab2 = new Tab2Fragment();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tab1 = actionBar.newTab().setText("1");
        tab2 = actionBar.newTab().setText("2");


        tab1.setTabListener(new MyTabListener(fragmentTab1));
        tab2.setTabListener(new MyTabListener(fragmentTab2));


        actionBar.addTab(tab1);
        actionBar.addTab(tab2);

    }

    public class MyTabListener implements ActionBar.TabListener {
        Fragment fragment;

        public MyTabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            ft.replace(R.id.fragment_container, fragment);
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // nothing done here
        }
    }
}