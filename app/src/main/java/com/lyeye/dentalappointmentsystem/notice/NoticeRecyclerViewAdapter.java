package com.lyeye.dentalappointmentsystem.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.mapper.UserImpl;

import java.text.SimpleDateFormat;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class NoticeRecyclerViewAdapter extends Adapter<ViewHolder> {

    private Context context;
    private List<AppointmentInfo> noticeList;
    private UserImpl userImpl;

    public NoticeRecyclerViewAdapter(Context context, List<AppointmentInfo> noticeList) {
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
        userImpl = new UserImpl(context);
        User userById = userImpl.findUserById(noticeList.get(position).getUserId());
        ((NoticeRecyclerViewHolder) holder).textView_notice.setText("       尊敬的" + userById.getUserName() + "，请于" + noticeList.get(position).getAmiDate() + " " + noticeList.get(position).getAmiTime() + "前往" + noticeList.get(position).getAffiliatedHospital() + "就诊。");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createAt = df.format(noticeList.get(position).getCreateAt());
        ((NoticeRecyclerViewHolder) holder).textView_time.setText(createAt);
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    class NoticeRecyclerViewHolder extends ViewHolder {

        private TextView textView_notice;
        private TextView textView_time;


        public NoticeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_notice = itemView.findViewById(R.id.tv_notice_notice);
            textView_time = itemView.findViewById(R.id.tv_notice_time);
        }
    }
}
