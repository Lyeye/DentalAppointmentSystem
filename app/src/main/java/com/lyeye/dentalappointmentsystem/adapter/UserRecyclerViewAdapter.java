package com.lyeye.dentalappointmentsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.administration.UserAppointmentActivity;
import com.lyeye.dentalappointmentsystem.entity.User;
import com.lyeye.dentalappointmentsystem.mapper.AppointmentInfoImpl;
import com.lyeye.dentalappointmentsystem.mapper.UserImpl;
import com.lyeye.dentalappointmentsystem.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class UserRecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<User> userList;
    private UserImpl userImpl;

    public UserRecyclerViewAdapter(Context context, List<User> list) {
        this.context = context;
        this.userList = list;
    }

    public void setList(List<User> list) {
        this.userList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AmRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycleview_user_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sl_ami_swipelayout;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ((AmRecyclerViewHolder) holder).textView_user_name.setText("姓名：" + userList.get(position).getUserName());

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        ((AmRecyclerViewHolder) holder).textView_user_createAt.setText("创建时间：" + df.format(userList.get(position).getCreateAt()));

        ((AmRecyclerViewHolder) holder).textView_user_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserAppointmentActivity.class);
                intent.putExtra("userId", userList.get(position).getUserId());
                context.startActivity(intent);
            }
        });

        String userName = userList.get(position).getUserName();

        userImpl = new UserImpl(context);

        /*设置侧滑显示模式*/
        ((AmRecyclerViewHolder) holder).swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        ((AmRecyclerViewHolder) holder).textView_user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("亲(づ￣3￣)づ╭❤～");

                sweetAlertDialog.setContentText("确定删除" + userName + "吗");
                sweetAlertDialog.setConfirmText("确定");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        User userById = UserRecyclerViewAdapter.this.userImpl.findUserById(userList.get(position).getUserId());
                        UserRecyclerViewAdapter.this.userImpl.deleteUser(userById);
                        userList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, userList.size());
                        notifyDataSetChanged();
                        ToastUtil.showMsg(context, "已将" + userName + "从数据库中移除");
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

        private TextView textView_user_name;
        private TextView textView_user_createAt;
        private TextView textView_user_appointment;
        private TextView textView_user_delete;
        private SwipeLayout swipeLayout;

        public AmRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_user_name = itemView.findViewById(R.id.tv_ui_username);
            textView_user_createAt = itemView.findViewById(R.id.tv_ui_create_time);
            textView_user_appointment = itemView.findViewById(R.id.tv_ui_appointment);
            textView_user_delete = itemView.findViewById(R.id.tv_ui_delete);
            swipeLayout = itemView.findViewById(R.id.sl_ui_swipeLayout);
        }
    }
}
