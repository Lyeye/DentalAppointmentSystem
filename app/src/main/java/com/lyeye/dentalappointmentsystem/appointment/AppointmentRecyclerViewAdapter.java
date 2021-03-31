package com.lyeye.dentalappointmentsystem.appointment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.pedant.SweetAlert.SweetAlertDialog;



public class AppointmentRecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BmobAppointmentInfo> scheduleList;


    public AppointmentRecyclerViewAdapter(Context context, List<BmobAppointmentInfo> list) {
        this.context = context;
        this.scheduleList = list;
    }

    public void setList(List<BmobAppointmentInfo> list) {
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

        String sdate = scheduleList.get(position).getAmiDate();
        String stime = scheduleList.get(position).getAmiTime();
        /*设置侧滑显示模式*/
        ((AmRecyclerViewHolder) holder).swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        ((AmRecyclerViewHolder) holder).textView_am_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("亲(づ￣3￣)づ╭❤～");

                sweetAlertDialog.setContentText("确定取消预约" + sdate + " " + stime +  "吗");
                sweetAlertDialog.setConfirmText("确定");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        BmobQuery<BmobAppointmentInfo> bmobAppointmentInfoBmobQuery = new BmobQuery<>();
                        bmobAppointmentInfoBmobQuery
                                .addWhereEqualTo("amiDate", sdate)
                                .addWhereEqualTo("amiTime", stime);
                        bmobAppointmentInfoBmobQuery.findObjects(new FindListener<BmobAppointmentInfo>() {
                            @Override
                            public void done(List<BmobAppointmentInfo> list, BmobException e) {
                                list.get(0).delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        scheduleList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(0, scheduleList.size());
                                        notifyDataSetChanged();
                                        ToastUtil.showMsg(context, "已取消" + sdate + " " + stime + "的预约");
                                    }
                                });
                            }
                        });
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
