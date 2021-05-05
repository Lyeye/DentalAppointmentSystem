package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.lyeye.dentalappointmentsystem.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class FamilyNoticeRecyclerViewAdapter extends Adapter<ViewHolder> {

    private Context context;
    private List<JSONObject> noticeList;

    public FamilyNoticeRecyclerViewAdapter(Context context, List<JSONObject> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FamilyNoticeRecyclerViewAdapter.NoticeRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_notice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            ((FamilyNoticeRecyclerViewAdapter.NoticeRecyclerViewHolder) holder).textView_time.setText(noticeList.get(position).getString("date") + "  " + noticeList.get(position).getString("time"));
            ((FamilyNoticeRecyclerViewAdapter.NoticeRecyclerViewHolder) holder).textView_notice.setText(noticeList.get(position).getString("content"));
            ((FamilyNoticeRecyclerViewAdapter.NoticeRecyclerViewHolder) holder).textView_from.setText("来自：" + noticeList.get(position).getString("from"));
            String picture = noticeList.get(position).getString("picture");
            if (picture != null) {
                Glide.with(context).load("image/" + picture).into(((FamilyNoticeRecyclerViewAdapter.NoticeRecyclerViewHolder) holder).imageView_pic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    class NoticeRecyclerViewHolder extends ViewHolder {

        private TextView textView_notice;
        private TextView textView_time;
        private TextView textView_from;

        private ImageView imageView_pic;


        public NoticeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_notice = itemView.findViewById(R.id.tv_notice_content);
            textView_time = itemView.findViewById(R.id.tv_notice_time);
            textView_from = itemView.findViewById(R.id.tv_notice_from);
            imageView_pic = itemView.findViewById(R.id.tv_notice_pic);
        }
    }
}
