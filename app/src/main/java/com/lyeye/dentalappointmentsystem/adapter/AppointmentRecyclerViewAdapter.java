package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.AppointmentInfo;
import com.lyeye.dentalappointmentsystem.mapper.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AppointmentRecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AppointmentInfo> scheduleList;
    private AppointmentInfoImpl appointmentInfoImpl;


    public AppointmentRecyclerViewAdapter(Context context, List<AppointmentInfo> list) {
        this.context = context;
        this.scheduleList = list;
    }

    public void setList(List<AppointmentInfo> list) {
        this.scheduleList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AmRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_appointment_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sl_ami_swipelayout;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ((AmRecyclerViewHolder) holder).textView_am_date.setText(scheduleList.get(position).getAmiDate());

        ((AmRecyclerViewHolder) holder).textView_am_time.setText(scheduleList.get(position).getAmiTime());

        ((AmRecyclerViewHolder) holder).textView_am_symptoms.setText(scheduleList.get(position).getAmiSymptoms());

        String amiDate = scheduleList.get(position).getAmiDate();
        String amiTime = scheduleList.get(position).getAmiTime();
        appointmentInfoImpl = new AppointmentInfoImpl(context);
        /*设置侧滑显示模式*/
        ((AmRecyclerViewHolder) holder).swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        ((AmRecyclerViewHolder) holder).textView_am_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("亲(づ￣3￣)づ╭❤～");

                sweetAlertDialog.setContentText("确定取消预约" + amiDate + " " + amiTime + "吗");
                sweetAlertDialog.setConfirmText("确定");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        AppointmentInfo appointmentInfo = appointmentInfoImpl.findAppointmentInfo(scheduleList.get(position).getAmiId());
                        appointmentInfoImpl.deleteAppointmentInfo(appointmentInfo);
                        scheduleList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, scheduleList.size());
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
    }

    class AmRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_am_date;
        private TextView textView_am_time;
        private TextView textView_am_symptoms;
        private TextView textView_am_delete;
        private SwipeLayout swipeLayout;

        public AmRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_am_date = itemView.findViewById(R.id.tv_ami_date);
            textView_am_time = itemView.findViewById(R.id.tv_ami_time);
            textView_am_symptoms = itemView.findViewById(R.id.tv_ami_symptoms);
            textView_am_delete = itemView.findViewById(R.id.tv_ami_delete);
            swipeLayout = itemView.findViewById(R.id.sl_ami_swipelayout);
        }
    }
}
