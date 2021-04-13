package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;


public class UserAppointmentInfoRecyclerViewAdapter extends Adapter<ViewHolder> {

    private Context context;
    private List<AppointmentInfo> appointmentInfoList;

    public UserAppointmentInfoRecyclerViewAdapter(Context context, List<AppointmentInfo> appointmentInfoList) {
        this.context = context;
        this.appointmentInfoList = appointmentInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentInfoViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_user_appointment_info_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((AppointmentInfoViewHolder) holder).textView_date.setText("预约日期：" + appointmentInfoList.get(position).getAmiDate());
        ((AppointmentInfoViewHolder) holder).textView_hospital.setText("预约医院：" + appointmentInfoList.get(position).getAffiliatedHospital());
        ((AppointmentInfoViewHolder) holder).textView_time.setText("预约时间：" + appointmentInfoList.get(position).getAmiTime());
        ((AppointmentInfoViewHolder) holder).textView_symptom.setText("问诊服务：" + appointmentInfoList.get(position).getAmiSymptoms());
    }

    @Override
    public int getItemCount() {
        return appointmentInfoList.size();
    }

    class AppointmentInfoViewHolder extends ViewHolder {

        private TextView textView_date;
        private TextView textView_hospital;
        private TextView textView_time;
        private TextView textView_symptom;


        public AppointmentInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_date = itemView.findViewById(R.id.tv_uai_date);
            textView_hospital = itemView.findViewById(R.id.tv_uai_hospital);
            textView_time = itemView.findViewById(R.id.tv_uai_time);
            textView_symptom = itemView.findViewById(R.id.tv_uai_symptom);
        }
    }
}
