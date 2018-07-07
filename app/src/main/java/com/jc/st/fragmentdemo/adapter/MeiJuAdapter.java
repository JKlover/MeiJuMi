package com.jc.st.fragmentdemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jc.st.fragmentdemo.R;
import com.jc.st.fragmentdemo.model.MeijuBean;

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

public class MeiJuAdapter extends BaseQuickAdapter<MeijuBean,BaseViewHolder> {
    private Context context;


    public MeiJuAdapter(Context context) {
        super(R.layout.item_meiju);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MeijuBean item) {
        Glide.with(context).load(item.getMeiju_imgurl()).into((ImageView) helper.getView(R.id.iv_meiju_icon));
        helper.setText(R.id.tv_meiju_name,item.getMeiju_name())
                .setText(R.id.tv_meiju_content,item.getMeiju_content())
                .setText(R.id.tv_meiju_time,item.getMeijiu_time())
                .setText(R.id.tv_meiju_jishu,item.getMeiju_jishu());

    }
}
