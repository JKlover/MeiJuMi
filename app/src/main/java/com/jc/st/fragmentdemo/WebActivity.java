package com.jc.st.fragmentdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jc.st.fragmentdemo.utils.ApkUtils;
import com.jc.st.fragmentdemo.utils.ClipboardUtil;
import com.jc.st.fragmentdemo.utils.HtmlUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

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



public class WebActivity extends AppCompatActivity {

    private static String HTTPHOST="http://www.meijuck.com";
    private WebView mwView;
    private Toolbar mTbar;
    private String mUrl;
    private LinearLayout mLy;
    private LinearLayout mPBLoad;
    private Uri downLoadUri=Uri.parse("https://mobile.xunlei.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("DETAILS_URL");
        setContentView(R.layout.activity_web);
        mTbar=findViewById(R.id.toolbar);
        mLy=findViewById(R.id.web_ly);
        mPBLoad=findViewById(R.id.pb_loading_view);
        mwView=new WebView(this);
        //添加webview到LinearLayout布局中解决不同的手机滑动卡顿问题
        mLy.addView(mwView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setSupportActionBar(mTbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbar.setNavigationOnClickListener(v -> onBackPressed());
        WebSettings webSettings = mwView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAppCacheEnabled(false);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);

        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                  webView.loadUrl(url);
                    Log.e("这个是什么",url);
                    if (url.contains("meijuck")){


                    }else if (url.contains("pan.baidu")){
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //判断是否有程序接应才跳转
                        PackageManager packageManager = getPackageManager();
                        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

                        boolean isIntentSafe = activities.size() > 0;
                        if (isIntentSafe){
                            startActivity(intent);
                        }


                    }else {
                        if (ApkUtils.getXLIntent() != null){
                            Uri uri = Uri.parse(url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }else {
                            new AlertDialog.Builder(WebActivity.this)
                                    .setTitle("注意")
                                    .setMessage("下载美剧需要迅雷，请先安装迅雷或者其他BT下载软件,是否前往下载迅雷")
                                    .setPositiveButton("是", (dialogInterface, i) -> {
                                        if (i==-1){
                                            Intent intent = new Intent(Intent.ACTION_VIEW, downLoadUri);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("否", null)
                                    .show();


                        }
                    }

                    return true;//false浏览器直接处理


            }

            /**
             * 开始加载回调
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mPBLoad.setVisibility(View.VISIBLE);
            }

            /**
             * 加载完成回调
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPBLoad.setVisibility(View.GONE);
            }
        };
        mwView.setWebViewClient(wvc);
        getDetailsData(mUrl);
    }

    private void getDetailsData(String url) {
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseHtml(result);
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
        Document doc= Jsoup.parse(result);
        String title=doc.select("h1.article-title").text();
        mTbar.setTitle(title);
        String bodyHtml=doc.select("article.article-content").html();
        String data=getMyHtml(bodyHtml);
        final String showData=data.replaceAll("<img","<img style='max-width:100%;height:auto;'");//让图片为屏幕做适应除去恶心人的横向滑轮
       runOnUiThread(() -> mwView.loadDataWithBaseURL(null,HtmlUtils.getHtml(showData), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null));


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mwView.canGoBack()) {
//            mwView.goBack();
//            return true;
//        } else {
//            finish();
//        }
        finish();
        return false;
    }

    /**
     * 移除控件释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mwView.removeAllViews();
        if(mwView!=null){

            mwView.destroy();
        }

    }

    /**
     * 1、先剔除不要的内容
     * 2、把图片路径换成可以显示的路径
     * 3、让图片为屏幕做适应除去恶心人的横向滑轮
     * @param htmltext
     * @return
     */
    private String getMyHtml(String htmltext){
        Document doc = Jsoup.parse(htmltext);
        //1、剔除不要的内容
        doc.select("strong").remove();
        doc.select("div.pagination").remove();
        doc.select("p.article-tags").remove();
        doc.select("p.post-copyright").remove();
        //2、把图片路径换成可以显示的绝对路径
        Elements pngs = doc.select("img[src]");
        for (Element element : pngs) {
            String imgUrl = element.attr("src");
            if (imgUrl.trim().startsWith("/")) {
                imgUrl =HTTPHOST + imgUrl;
                element.attr("src", imgUrl);//替换静态页面的img地址属性再加载
            }
        }
        return doc.toString();
    }


}
