package com.pocketcreations.bankapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.hanks.htextview.base.HTextView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;


public class MainActivity extends AppCompatActivity {

    private HTextView headerTextView;
    public ViewPager viewPager;
    private SmartTabLayout smartTabLayout;
    private SectionPageAdapter adapter;
    private HorizontalScrollView scrollView;
    private KenBurnsView viewPagerBg;
    SharedPreferences mPrefs;
    DatabaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.headerTextView = (HTextView) findViewById(R.id.headerTextView);
        this.smartTabLayout = (SmartTabLayout) findViewById(R.id.smartTabLayout);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.scrollView = (HorizontalScrollView) findViewById(R.id.scroll_view);
        this.viewPagerBg = (KenBurnsView) findViewById(R.id.paralaxImage);
        mPrefs = getSharedPreferences("Details", Context.MODE_PRIVATE);
        boolean firststart = mPrefs.getBoolean("firststart", true);
        if (firststart) {
            this.myDB = new DatabaseHelper(this);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean("firststart", false);
            editor.apply();
            addData(myDB);
        }
        MainActivity.this.headerTextView.animateText(MainActivity.this.getString(R.string.UserList));
        setUpViewPager(this.viewPager);
        this.smartTabLayout.setViewPager(this.viewPager);
    }

    public void addData(DatabaseHelper myDB){
        myDB.insertTable1("James Harper","james79@gmail.com",R.drawable.person1,1000);
        myDB.insertTable1("William Jackson","william52@gmail.com",R.drawable.person2,1000);
        myDB.insertTable1("Amelia","amelia@gmail.com",R.drawable.person3,1000);
        myDB.insertTable1("Ashly","ashly@gmail.com",R.drawable.person4,1000);
        myDB.insertTable1("Fiona Florence","fionaflo@gmail.com",R.drawable.person5,1000);
        myDB.insertTable1("Suraj Sikdhar","sjsikdhar@gmail.com",R.drawable.person6,1000);
        myDB.insertTable1("Dhanush Prabha","dhanush22@gmail.com",R.drawable.person7,1000);
        myDB.insertTable1("Samantha","samanthaaki@gmail.com",R.drawable.person8,1000);
        myDB.insertTable1("Tom Cruise","missionimpossible@gmail.com",R.drawable.person9,1000);
        myDB.insertTable1("Brad Pitt","thepitt@gmail.com",R.drawable.person10,1000);
        myDB.insertTable2("SENDER","RECEIVER",0);
    }

    public void setUpViewPager(final ViewPager viewPager){
        this.adapter = new SectionPageAdapter(getSupportFragmentManager());
        final LayoutInflater from = LayoutInflater.from(this);
        final Resources resources = getResources();
        this.adapter.addFragment(new UserList(), getString(R.string.UserList), 0);
        this.adapter.addFragment(new Transactions(), getString(R.string.Transactions), 1);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(this.adapter);
        this.smartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            public View createTabView(ViewGroup viewGroup, int i, PagerAdapter pagerAdapter) {
                View inflate = from.inflate(R.layout.custoom_tab_icon, viewGroup, false);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.custom_tab_icon);
                switch (i) {
                    case 0:
                        imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_users));
                        break;
                    case 1:
                        imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_trans));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + i);
                }
                return inflate;
            }
        });
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private float computeFactor() {
                return ((float) (MainActivity.this.viewPagerBg.getWidth() - viewPager.getWidth())) / ((float) (viewPager.getWidth() * (viewPager.getAdapter().getCount() - 1)));
            }

            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                MainActivity.this.scrollView.scrollTo((int) (((float) ((viewPager.getWidth() * i) + i2)) * computeFactor()), 0);
            }
            public void onPageSelected(int i) {
                if (i == 0) {
                    MainActivity.this.headerTextView.animateText(MainActivity.this.getString(R.string.UserList));
                } else if (i == 1) {
                    MainActivity.this.headerTextView.animateText(MainActivity.this.getString(R.string.Transactions));
                }
            }
        });
    }
}