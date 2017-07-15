package net.bwei.shan.download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import net.bwei.shan.download.R;
import net.bwei.shan.download.model.ModelItem;

import java.util.List;

/**
 * Created by shanlianting on 2017/7/12.
 */

public class ModelItemAdapter extends BaseAdapter {
    private List<ModelItem> list;
    private Context context;

    public ModelItemAdapter(List<ModelItem> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoder holder =null;
        if (view==null){
            holder = new ViewHoder();
            view =  LayoutInflater.from(context).inflate(R.layout.model_item,null);
            holder.textView = view.findViewById(R.id.textView);
            holder.box =view.findViewById(R.id.checkbox);
            view.setTag(holder);

        }else {
            holder= (ViewHoder) view.getTag();
        }
        holder.textView.setText(list.get(i).getTitle());
        holder.box.setChecked(list.get(i).isCheck());
        final ViewHoder finalHolder = holder;
        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck =  list.get(i).isCheck();
                list.get(i).setCheck(!isCheck);
                finalHolder.box.setChecked(!isCheck);
            }
        });

        return view;
    }

    public class  ViewHoder{
        public CheckBox box;
        public TextView textView;

    }
}
