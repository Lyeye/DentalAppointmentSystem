package com.lyeye.dentalappointmentsystem.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class FamilyNoticeRecyclerViewAdapter extends Adapter<ViewHolder> {

    private Context context;
    private List<BmobAppointmentInfo> noticeList;


    public FamilyNoticeRecyclerViewAdapter(Context context, List<BmobAppointmentInfo> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_family_notice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((NoticeRecyclerViewHolder) holder).textView_notice.setText("       尊敬的" + noticeList.get(position).getUserName() + "先生/女士，请于" + noticeList.get(position).getAmiDate() + " " + noticeList.get(position).getAmiTime() + "前往所在医院就诊。");
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    class NoticeRecyclerViewHolder extends ViewHolder {

        private TextView textView_notice;

        public NoticeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_notice = itemView.findViewById(R.id.tv_fni_notice);
        }
    }
}
