package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class MyFamilyAppointmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AppointmentInfo> scheduleList;


    public MyFamilyAppointmentRecyclerViewAdapter(Context context, List<AppointmentInfo> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFamilyAppointmentRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_my_family_appointment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((MyFamilyAppointmentRecyclerViewHolder) holder).textView_fami_date.setText("预约日期：" + scheduleList.get(position).getAmiDate());
        ((MyFamilyAppointmentRecyclerViewHolder) holder).textView_fami_time.setText("预约时间：" + scheduleList.get(position).getAmiTime());
        ((MyFamilyAppointmentRecyclerViewHolder) holder).textView_fami_symptoms.setText("预约类型：" + scheduleList.get(position).getAmiSymptoms());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    class MyFamilyAppointmentRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_fami_date;
        private TextView textView_fami_time;
        private TextView textView_fami_symptoms;

        public MyFamilyAppointmentRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_fami_date = itemView.findViewById(R.id.tv_fami_date);
            textView_fami_time = itemView.findViewById(R.id.tv_fami_time);
            textView_fami_symptoms = itemView.findViewById(R.id.tv_fami_symptoms);
        }
    }
}
