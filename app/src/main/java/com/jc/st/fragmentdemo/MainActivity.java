package com.jc.st.fragmentdemo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jc.st.fragmentdemo.base.BaseFragment;
import com.jc.st.fragmentdemo.fragment.AFragment;

import java.util.ArrayList;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

import static com.jc.st.fragmentdemo.utils.DataCleanManager.clearAllCache;
import static com.jc.st.fragmentdemo.utils.DataCleanManager.getTotalCacheSize;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　       	██ ━██  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * Created by st on 2018/1/18.
 */


public class MainActivity extends AppCompatActivity {
    private int[] mImgArray, mColorArray;

     private final String[] mTitles = {"魔幻科幻", "枪战动作", "灵异惊悚","都市情感","谍战罪案","生活喜剧","综艺选秀","卡通动漫"};
     private CoordinatorTabLayout mcoordinatorTabLayout;
     private ViewPager mviewPager;
     private ArrayList<BaseFragment> mFragments= new ArrayList<>();;
     private MyPagerAdapter myPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initViewPager();

        mImgArray =new int[]{
                R.mipmap.bg_01,
                R.mipmap.bg_02,
                R.mipmap.bg_03,
                R.mipmap.bg_04,

                R.mipmap.bg_01,
                R.mipmap.bg_02,
                R.mipmap.bg_03,
                R.mipmap.bg_04,
        };//滑动背景
        mColorArray=new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_orange_light,

                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_orange_light,

        };//滑动背景
        mcoordinatorTabLayout=findViewById(R.id.main_coordinator_tab_layout);
        mcoordinatorTabLayout
                .setTranslucentStatusBar(this)
                .setBackEnable(false)
                .setTabMode(TabLayout.MODE_SCROLLABLE)
                .setTitle("美剧迷")
                .setImageArray(mImgArray,mColorArray)
                .setupWithViewPager(mviewPager);


    }
    private void initFragments() {
        mFragments.add(AFragment.getInstance(mTitles[0]));
        mFragments.add(AFragment.getInstance(mTitles[1]));
        mFragments.add(AFragment.getInstance(mTitles[2]));
        mFragments.add(AFragment.getInstance(mTitles[3]));
        mFragments.add(AFragment.getInstance(mTitles[4]));
        mFragments.add(AFragment.getInstance(mTitles[5]));
        mFragments.add(AFragment.getInstance(mTitles[6]));
        mFragments.add(AFragment.getInstance(mTitles[7]));

//        for (String title : mTitles) {
//            mFragments.add(AFragment.getInstance(title));
//        }
    }
    private void initViewPager() {
        mviewPager=findViewById(R.id.main_view_pager);
//        mviewPager.setOffscreenPageLimit(4);//设置了缓存4页所以加载一起加载4页,滑动时4页的数据都没重新加载
//        mviewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager(),mFragments,mTitles));
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(myPagerAdapter);
    }

   class MyPagerAdapter extends FragmentPagerAdapter{


       public MyPagerAdapter(FragmentManager fm) {
           super(fm);
       }

       @Override
       public Fragment getItem(int position) {
           return mFragments.get(position);
       }

       @Override
       public CharSequence getPageTitle(int position) {
           return mTitles[position];
       }

       @Override
       public int getCount() {
           return mFragments.size();
       }

       /**
        * 让tab可以多加载,解决加载三个fragment重新初始化数据的问题
        * @param container
        * @param position
        * @param object
        */
       @Override
       public void destroyItem(ViewGroup container, int position, Object object) {
//           super.destroyItem(container, position, object);
       }
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.search_meuju){
            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
//            try {
//                Toast.makeText(this,"缓存大小为"+getTotalCacheSize(this),Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            clearAllCache(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
