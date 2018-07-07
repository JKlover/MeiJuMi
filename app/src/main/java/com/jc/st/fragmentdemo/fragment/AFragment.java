package com.jc.st.fragmentdemo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;


import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jc.st.fragmentdemo.R;
import com.jc.st.fragmentdemo.WebActivity;
import com.jc.st.fragmentdemo.activity.ContentMeiju;
import com.jc.st.fragmentdemo.adapter.MeiJuAdapter;
import com.jc.st.fragmentdemo.base.BaseFragment;
import com.jc.st.fragmentdemo.model.MeijuBean;

import com.jc.st.fragmentdemo.utils.ApkUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;


import static android.content.ContentValues.TAG;
import static com.jc.st.fragmentdemo.Api.A_URL;
import static com.jc.st.fragmentdemo.Api.B_URL;
import static com.jc.st.fragmentdemo.Api.C_URL;
import static com.jc.st.fragmentdemo.Api.D_URL;
import static com.jc.st.fragmentdemo.Api.E_URL;
import static com.jc.st.fragmentdemo.Api.F_URL;
import static com.jc.st.fragmentdemo.Api.G_URL;
import static com.jc.st.fragmentdemo.Api.H_URL;

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
 * Created by st on 2018/1/17.
 */

public class AFragment extends BaseFragment {
    private String mTitle;
    private static String ARG_TITLE="title";
    private MeiJuAdapter meiJuAdapter;
    private RecyclerView mrecyclerView;
    private AVLoadingIndicatorView avi;
    private  RefreshLayout refreshLayout;
    private ArrayList<MeijuBean>meijuBeans;
    private  String Pager;
    Uri uri = Uri.parse("https://mobile.xunlei.com/");
// String url="http://www.meijuck.com/mhkh/";

    public static  AFragment getInstance(String title) {
        AFragment fra = new  AFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    private  Handler handler=new Handler(){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          meiJuAdapter.setNewData(meijuBeans);
          avi.setVisibility(View.GONE);
          Log.e("title",mTitle);
      }
  };

    /**
     * 根据tab的Title跳转到相应的fragment
     */
    @Override
    protected void initTitleFrom() {
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);
    }

    @Override
    protected View initView() {
       View view=View.inflate(mcontext, R.layout.fragment_a,null);

        mrecyclerView=view.findViewById(R.id.rv_meiju);
        avi=view.findViewById(R.id.avi);
        meiJuAdapter=new MeiJuAdapter(mcontext);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        mrecyclerView.setAdapter(meiJuAdapter);
         refreshLayout = view.findViewById(R.id.srl_meiju);
        meijuBeans=new ArrayList<>();
        meiJuAdapter.setOnItemClickListener((adapter, view1, position) -> {
            final MeijuBean meijuBean=meijuBeans.get(position);
            Intent intent = new Intent(mcontext, WebActivity.class);
            intent.putExtra("DETAILS_URL",meijuBean.getMeiju_href());
            startActivity(intent);

        });
       refreshData();

       return view;
    }

    @Override
    protected void getData() {
        if(mTitle=="魔幻科幻"){
            getDataFromSpider(A_URL);
        }else if(mTitle=="枪战动作"){
            getDataFromSpider(B_URL);

        }else if(mTitle=="灵异惊悚"){
            getDataFromSpider(C_URL);

        }else if (mTitle=="都市情感"){
            getDataFromSpider(D_URL);

        }else if (mTitle=="谍战罪案"){
            getDataFromSpider(E_URL);
        }else if (mTitle=="生活喜剧"){

            getDataFromSpider(F_URL);
        }else if (mTitle=="综艺选秀"){

            getDataFromSpider(G_URL);
        }else {

            getDataFromSpider(H_URL);
        }

        Log.e(TAG,"A数据被初始化了");
    }

    private void refreshData() {
        refreshLayout.setOnRefreshListener(refreshlayout -> {
//                Bundle bundle = getArguments();
//                mTitle = bundle.getString(ARG_TITLE);
            meiJuAdapter.getData().clear();
            if(mTitle=="魔幻科幻"){
                getDataFromSpider(A_URL);
            }else if(mTitle=="枪战动作"){
                getDataFromSpider(B_URL);

            }else if(mTitle=="灵异惊悚"){
                getDataFromSpider(C_URL);

            }else if (mTitle=="都市情感"){
                getDataFromSpider(D_URL);

            }else if (mTitle=="谍战罪案"){
                getDataFromSpider(E_URL);
            }else if (mTitle=="生活喜剧"){

                getDataFromSpider(F_URL);
            }else if (mTitle=="综艺选秀"){

                getDataFromSpider(G_URL);
            }else {

                getDataFromSpider(H_URL);
            }
            meiJuAdapter.notifyDataSetChanged();
            refreshlayout.finishRefresh();
        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            getDataFromSpider(Pager);
            Log.e("刷新",Pager);
            refreshlayout.finishLoadmore();
        });
    }

//    @Override
//    protected void initData() {
//        super.initData();
//
//
//    }

    private void getDataFromSpider(String url) {
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//            System.out.println(result);
            parseHtml(result);
            handler.sendEmptyMessage(10);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        }) ;

    }

    private void parseHtml(String result) {

        Document document= Jsoup.parse(result);
        Elements getData=document.getElementsByClass("excerpt");

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

            meijuBeans.add(meijuBean);
        }

         String pager=document.getElementsByClass("next-page").select("a").attr("href");
         Pager=pager;
         Log.e("下一页",pager);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
