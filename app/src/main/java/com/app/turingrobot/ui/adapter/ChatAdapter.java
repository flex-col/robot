package com.app.turingrobot.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.turingrobot.R;
import com.app.turingrobot.entity.CoreEntity;
import com.app.turingrobot.utils.GlideUtils;
import com.app.turingrobot.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alpha on 2016/3/26 23:03.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;

    private List<CoreEntity> datas = new ArrayList<>();

    //接收到的消息
    private static final int TARGET = 0;
    //发送的消息
    private static final int MINE = 1;


    public ChatAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<CoreEntity> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }


    public void addData(CoreEntity entity, RecyclerView recyclerView) {
        datas.add(entity);
        notifyItemRangeChanged(getItemCount(), getItemCount() + 1);
        recyclerView.scrollToPosition(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position).isTarget()) {
            return TARGET;
        } else {
            return MINE;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TARGET) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chat_txt_target, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chat_txt, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CoreEntity coreEntity = datas.get(position);
        holder.tvContent.setText(coreEntity.getText());
        holder.tvTime.setText(TimeUtil.getHourMin(coreEntity.getTime()));
        GlideUtils.displayCircleHeader(holder.frescoAvatar, "http://bbs.umeng.com/uc_server/avatar.php?uid=34&size=small");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.fresco_avatar)
        ImageView frescoAvatar;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
