package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyeye.dentalappointmentsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class MyFamilyAppointmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<JSONObject> schedule;


    public MyFamilyAppointmentRecyclerViewAdapter(Context context, List<JSONObject> schedule) {
        this.context = context;
        this.schedule = schedule;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFamilyAppointmentRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_my_family_appointment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String amiDate = schedule.get(position).getString("date");
            String amiTime = schedule.get(position).getString("time");
            ((MyFamilyAppointmentRecyclerViewAdapter.MyFamilyAppointmentRecyclerViewHolder) holder).textView_fami_date.setText("预约日期：" + amiDate);
            ((MyFamilyAppointmentRecyclerViewAdapter.MyFamilyAppointmentRecyclerViewHolder) holder).textView_fami_time.setText("预约时间：" + amiTime);
            ((MyFamilyAppointmentRecyclerViewAdapter.MyFamilyAppointmentRecyclerViewHolder) holder).textView_fami_symptom.setText("疾病类型：" + schedule.get(position).getString("symptom"));
            ((MyFamilyAppointmentRecyclerViewAdapter.MyFamilyAppointmentRecyclerViewHolder) holder).textView_fami_hospital.setText("预约医院：" + schedule.get(position).getString("hospitalName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

    class MyFamilyAppointmentRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_fami_date;
        private TextView textView_fami_time;
        private TextView textView_fami_symptom;
        private TextView textView_fami_hospital;

        public MyFamilyAppointmentRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_fami_date = itemView.findViewById(R.id.tv_fami_date);
            textView_fami_time = itemView.findViewById(R.id.tv_fami_time);
            textView_fami_symptom = itemView.findViewById(R.id.tv_fami_symptom);
            textView_fami_hospital = itemView.findViewById(R.id.tv_fami_hospital);
        }
    }
}
