package com.jdjt.exchange.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdjt.exchange.R;

import java.util.List;
import java.util.Map;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:TagsAdapter
 * @Package com.jdjt.exchange.adapter
 * @Date 2016/9/8 17:46
 */
public class TagsAdapter extends AppBaseAdapter<Map<String,String>,AppBaseAdapter.BaseViewHolder> {

    public TagsAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        return new BaseViewHolder(View.inflate(getContext(), R.layout.layout_tags_item, null));
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position, Map<String, String> data) {
        String map = data.get("title");

        TextView txt = holder.getView(R.id.tags_name);

        txt.setText(map);

    }

//    @Override
//    protected void bindViewHolder(BaseViewHolder holder, int position, String data) {
//        TextView txt = holder.getView(R.id.tags_name);
//        txt.setText(data+"");
//    }
}