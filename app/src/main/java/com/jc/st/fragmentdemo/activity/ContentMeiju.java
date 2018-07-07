package com.jc.st.fragmentdemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.st.fragmentdemo.R;
import com.jc.st.fragmentdemo.utils.HtmlUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


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
 * Created by st on 2018/5/18
 */
public class ContentMeiju extends AppCompatActivity {
    private static String HTTPHOST="http://www.meijuck.com";
    private TextView mTitleTextView;
    private AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private String mUrl;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("DETAILS_URL");
        LinearLayout mLinearLayout = findViewById(R.id.container);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mTitleTextView = findViewById(R.id.toolbar_title);
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(v -> ContentMeiju.this.finish());
        getDetailsData(mUrl);
        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator(-1, 3)
                .createAgentWeb()//
                .ready()
                .go("");//这个在调用系统的webview后会被覆盖

//	    Log.e("dsdsd",HtmlUtils.getHtml(showData));

    }

    private void getDetailsData(String url) {
        OkGo.<String>get(url)//
                .tag(this)//
                .headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4620.400 QQBrowser/9.7.13014.400")//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        parseHtml(response.body());
                    }
                });
    }

    private void parseHtml(String result) {
        Document doc= Jsoup.parse(result);
        String title=doc.select("h1.article-title").text();
        mToolbar.setTitle(title);
        String bodyHtml=doc.select("article.article-content").html();
        String data=getMyHtml(bodyHtml);
        String showData=data.replaceAll("<img","<img style='max-width:100%;height:auto;'");//让图片为屏幕做适应除去恶心人的横向滑轮
        mAgentWeb.getWebCreator().getWebView().loadDataWithBaseURL(null, HtmlUtils.getHtml(showData), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null);

    }

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
//        //让图片为屏幕做适应除去恶心人的横向滑轮
//        Elements elements=doc.getElementsByTag("img");
//        for (Element element : elements) {
////            element.attr("width","100%").attr("height","auto");
//            element.attr("width","auto").attr("height","100%");
//        }
        return doc.toString();
    }
}