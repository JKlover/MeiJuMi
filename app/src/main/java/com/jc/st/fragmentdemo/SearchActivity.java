package com.jc.st.fragmentdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jc.st.fragmentdemo.adapter.MeiJuAdapter;
import com.jc.st.fragmentdemo.callback.StringDialogCallback;
import com.jc.st.fragmentdemo.model.MeijuBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                           O\  =  /O
 * //                        ____/`---'\____
 * //                      .'  \\|     |//  `.
 * //                     /  \\|||  :  |||//  \
 * //                    /  _||||| -:- |||||-  \
 * //                    |   | \\\  -  /// |   |
 * //                    | \_|  ''\---/''  |   |
 * //                    \  .-\__  `-`  ___/-. /
 * //                  ___`. .'  /--.--\  `. . __
 * //               ."" '<  `.___\_<|>_/___.'  >'"".
 * //              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //              \  \ `-.   \_ __\ /__ _/   .-` /  /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * //                      Buddha Bless, No Bug !
 * /**
 * Created by st on 2018/6/30
 */
public  class SearchActivity extends Activity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private MeiJuAdapter adapter;
    private List<MeijuBean> lists;
    private boolean otherSearchStatus;
    private RelativeLayout relative_no_result;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //更新UI
                    relative_no_result.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    relative_no_result.setVisibility(View.GONE);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        customSearchView();
    }

    private void initView() {
    searchView=findViewById(R.id.search_view);
    recyclerView=findViewById(R.id.recyclerView);
    relative_no_result=findViewById(R.id.no_result_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter=new MeiJuAdapter(this);
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener((adapter, view, position) -> {
        MeijuBean meijuBean= (MeijuBean) adapter.getItem(position);
        Intent intent = new Intent(SearchActivity.this, WebActivity.class);
        intent.putExtra("DETAILS_URL",meijuBean.getMeiju_href());
        startActivity(intent);
    });
    }

    private void customSearchView() {
        searchView.setIconified(false);
        searchView.onActionViewExpanded();
        if (searchView!=null){
            try{
                Class<?> argClass = searchView.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(searchView);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //获取ImageView的id
        int imgId = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon",null,null);
        //获取ImageView
        ImageView searchButton = (ImageView)searchView.findViewById(imgId);
        //设置图片
        searchButton.setImageResource(R.mipmap.search);
        //不使用默认
        searchView.setIconifiedByDefault(false);


        //获取到TextView的ID
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        //获取到TextView的控件
        TextView textView = (TextView) searchView.findViewById(id);
        //设置字体大小为14sp
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//14sp
        //设置提示字体颜色
        textView.setTextColor(this.getResources().getColor(R.color.gray_color));
        //设置输入文字颜色
        textView.setHintTextColor(this.getResources().getColor(R.color.black_overlay));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                otherSearchStatus=true;
                System.out.println("我收到了" + query);
                getResponse(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handler.sendEmptyMessage(1);
                adapter.getData().clear();
                return false;
            }
        });
    }

    /**
     * 注意网站的前台提交给后台的处理的一般是POST要抓取网站的搜索引擎最好用POST模拟网站前台提交数据，有的网站用GET模拟请求也可以的
     * @param keyWord
     */
    private void getResponse(String keyWord) {
        OkGo.<String>post("http://www.meijuck.com/e/search/index.php")//
                .tag(this)//
                .headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")//
                .headers("Referer","http://www.meijuck.com/")
                .params("show","title")
                .params("tempid","1")
                .params("tbname","news")
                .params("mid","1")
                .params("dopost","search")
                .params("keyboard",keyWord)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String html= response.body();
                        System.out.println(html);
                        lists=spiderHtml(html);
                        if (lists.size()>=0&&lists!=null){
                            if (otherSearchStatus){
                                //第二次搜索清空原来的的集合重新加载
                                adapter.getData().clear();
                                recyclerView.setVisibility(View.VISIBLE);
                                relative_no_result.setVisibility(View.GONE);
                                adapter.setNewData(lists);
                            }else  {
                                recyclerView.setVisibility(View.VISIBLE);
                                relative_no_result.setVisibility(View.GONE);
                                adapter.setNewData(lists);
                            }

                        }


                    }
                });

    }

    private List<MeijuBean> spiderHtml(String html) {
        List<MeijuBean>beans=new ArrayList<>();
        Document document= Jsoup.parse(html);
        Elements getData=document.getElementsByClass("excerpt");
        if(getData.isEmpty()){
           handler.sendEmptyMessage(0);
        }else {
            for (Element e:getData){
                MeijuBean meijuBean=new MeijuBean();
                String href=e.select("a").attr("href");
                meijuBean.setMeiju_href(href);
                Log.e("链接地址",href);
                String img=e.getElementsByTag("img").attr("abs:data-src");
                meijuBean.setMeiju_imgurl(img);
                Log.e("图片链接",img);
                String name=e.select("h2").select("a").text();
                meijuBean.setMeiju_name(name);
                Log.e("片名",name);
                String content=e.getElementsByClass("note").text();
                meijuBean.setMeiju_content(content);
                Log.e("详情内容描述",content);
                String time=e.getElementsByClass("new").text();
                meijuBean.setMeijiu_time(time);
                Log.e("更新时间",time);
                String jishu=e.getElementsByClass("post-zhuangtai").text();
                meijuBean.setMeiju_jishu(jishu);
                Log.e("更新集数",jishu);

                beans.add(meijuBean);
            }
        }

        return beans;
    }

    /**
     * 解决跳转点击返回时键盘自动唤出
     */
    @Override
    protected void onStart() {
        super.onStart();
        searchView.clearFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
