package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.appointment.AppointmentActivity;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.impl.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


public class AppointmentRecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<JSONObject> schedule;


    public AppointmentRecyclerViewAdapter(Context context, List<JSONObject> schedule) {
        this.context = context;
        this.schedule = schedule;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AmRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_appointment_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sl_ami_swipelayout;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        try {
            /*设置侧滑显示模式*/
            ((AmRecyclerViewHolder) holder).swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            String id = schedule.get(position).getString("amiId");
            String amiDate = schedule.get(position).getString("date");
            String amiTime = schedule.get(position).getString("time");
            ((AmRecyclerViewHolder) holder).textView_am_date.setText("预约日期：" + amiDate);
            ((AmRecyclerViewHolder) holder).textView_am_time.setText("预约时间：" + amiTime);
            ((AmRecyclerViewHolder) holder).textView_am_symptoms.setText("疾病类型：" + schedule.get(position).getString("symptom"));
            ((AmRecyclerViewHolder) holder).textView_am_hospital.setText("预约医院：" + schedule.get(position).getString("hospitalName"));
            ((AmRecyclerViewHolder) holder).textView_am_remote.setText(schedule.get(position).getString("isRemote").equals("0") ? "是否远程：不是" : "是否远程：是");
            ((AmRecyclerViewHolder) holder).textView_am_arrive.setText(schedule.get(position).getString("isArrive").equals("0") ? "是否到达：不是" : "是否到达：是");

            ((AmRecyclerViewHolder) holder).textView_am_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("确定取消预约" + amiDate + " " + amiTime + "吗");
                    sweetAlertDialog.setConfirmText("确定");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        FormBody.Builder p = new FormBody.Builder();
                                        Request request = new Request.Builder()
                                                .url("http://10.200.129.8:8080/cancelAppointment")
                                                .post(p.add("amiId", id).build())
                                                .build();
                                        new OkHttpClient().newCall(request).execute();

                                    } catch (Exception e) {
                                        Log.d(null, "runError: " + e);
                                    }
                                }
                            }).start();
                            schedule.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, schedule.size());
                            notifyDataSetChanged();
                            ToastUtil.showMsg(context, "已取消" + amiDate + " " + amiTime + "的预约");
                            sweetAlertDialog.cancel();
                        }
                    });
                    sweetAlertDialog.setCancelText("取消");
                    sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                            ToastUtil.showMsg(context, "已取消");
                        }
                    }).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class AmRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_am_date;
        private TextView textView_am_time;
        private TextView textView_am_hospital;
        private TextView textView_am_symptoms;
        private TextView textView_am_delete;
        private TextView textView_am_remote;
        private TextView textView_am_arrive;
        private SwipeLayout swipeLayout;

        public AmRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_am_date = itemView.findViewById(R.id.tv_ami_date);
            textView_am_time = itemView.findViewById(R.id.tv_ami_time);
            textView_am_hospital = itemView.findViewById(R.id.tv_ami_hospital);
            textView_am_symptoms = itemView.findViewById(R.id.tv_ami_symptoms);
            textView_am_remote = itemView.findViewById(R.id.tv_ami_remote);
            textView_am_arrive = itemView.findViewById(R.id.tv_ami_arrive);
            textView_am_delete = itemView.findViewById(R.id.tv_ami_delete);
            swipeLayout = itemView.findViewById(R.id.sl_ami_swipelayout);
        }
    }
}
