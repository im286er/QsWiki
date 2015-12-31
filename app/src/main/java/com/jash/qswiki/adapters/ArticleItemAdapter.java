package com.jash.qswiki.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jash.qswiki.R;
import com.jash.qswiki.entities.ArticleItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jash
 * Date: 15-12-30
 * Time: 下午4:06
 */
public class ArticleItemAdapter extends BaseAdapter {
    private Context context;
    private List<ArticleItem> list;

    public ArticleItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_article_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ArticleItem item = list.get(position);
        if (item.getUser() == null) {
            holder.user_name.setText("匿名用户");
            holder.user_icon.setImageURI(Uri.parse("res:///" + R.mipmap.default_anonymous_users_avatar));
        } else {
            holder.user_name.setText(item.getUser().getLogin());
            holder.user_icon.setImageURI(Uri.parse(item.getIconUrl()));
        }
        holder.content.setText(item.getContent());
        if (item.getImage() != null) {
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setImageURI(Uri.parse(item.getImageUrl()));
            List<Integer> s = item.getImage_size().getS();
            holder.image.setAspectRatio(1.0f * s.get(0) / s.get(1));
        } else if (item.getPic() != null) {
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setImageURI(Uri.parse(item.getPic()));
            holder.image.setAspectRatio(1);
        } else {
            holder.image.setVisibility(View.GONE);
        }
        holder.group.setOnCheckedChangeListener(item);
        switch (item.getSupportSate()) {
            case 1:
                holder.group.check(R.id.support);
                break;
            case 2:
                holder.group.check(R.id.unsupport);
                break;
            default:
                holder.group.clearCheck();
                break;
        }
        holder.support.setText("好笑:" + String.valueOf(item.getVotes().getUp()));
        holder.unsupport.setText("不好笑:" + String.valueOf(item.getVotes().getDown()));
        holder.comment.setText("评论:" + String.valueOf(item.getComments_count()));
        return convertView;
    }

    public void addAll(Collection<? extends ArticleItem> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        private RadioGroup group;
        private TextView user_name;
        private TextView content;
        private TextView comment;
        private SimpleDraweeView user_icon;
        private SimpleDraweeView image;
        private RadioButton support;
        private RadioButton unsupport;

        public ViewHolder(View itemView) {
            user_name = ((TextView) itemView.findViewById(R.id.userName));
            content = ((TextView) itemView.findViewById(R.id.content));
            comment = ((TextView) itemView.findViewById(R.id.comment));
            user_icon = ((SimpleDraweeView) itemView.findViewById(R.id.userIcon));
            image = ((SimpleDraweeView) itemView.findViewById(R.id.image));
            support = ((RadioButton) itemView.findViewById(R.id.support));
            unsupport = ((RadioButton) itemView.findViewById(R.id.unsupport));
            image.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
            group = (RadioGroup) itemView.findViewById(R.id.is_support);
        }

    }
}
