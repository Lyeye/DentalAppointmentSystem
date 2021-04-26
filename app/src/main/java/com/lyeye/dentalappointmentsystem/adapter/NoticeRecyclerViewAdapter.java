package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyeye.dentalappointmentsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class NoticeRecyclerViewAdapter extends Adapter<ViewHolder> {

    private Context context;
    private List<JSONObject> noticeList;

    public NoticeRecyclerViewAdapter(Context context, List<JSONObject> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_notice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            ((NoticeRecyclerViewHolder) holder).textView_time.setText(noticeList.get(position).getString("date") + "  " + noticeList.get(position).getString("time"));
            ((NoticeRecyclerViewHolder) holder).textView_notice.setText(noticeList.get(position).getString("content"));
            ((NoticeRecyclerViewHolder) holder).textView_from.setText("来自：" + noticeList.get(position).getString("from"));
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


        public NoticeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_notice = itemView.findViewById(R.id.tv_notice_content);
            textView_time = itemView.findViewById(R.id.tv_notice_time);
            textView_from = itemView.findViewById(R.id.tv_notice_from);
        }
    }
}
