package com.lyeye.dentalappointmentsystem.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.table.TableData;
import com.lyeye.dentalappointmentsystem.R;
import com.lyeye.dentalappointmentsystem.entity.BmobAppointmentInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TimeTableActivity extends AppCompatActivity {

    private SmartTable smartTable;

    private String userName;
    private List<BmobAppointmentInfo> bmobAppointmentInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        Bmob.initialize(this, "0d7ffa7b53c0d9e51d06084302c5368c");
        smartTable = findViewById(R.id.st_tt_table);
        userName = "Lyeye";
        BmobQuery<BmobAppointmentInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userName", userName).findObjects(new FindListener<BmobAppointmentInfo>() {
            @Override
            public void done(List<BmobAppointmentInfo> list, BmobException e) {
                Column<String> c1 = new Column<String>("date", "amiDate");
                Column<String> c2 = new Column<String>("time", "amiTime");
                TableData<BmobAppointmentInfo> tableData = new TableData<BmobAppointmentInfo>("预约表", list, c1, c2);
                smartTable.setTableData(tableData);
            }
        });


    }
}