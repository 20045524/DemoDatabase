package sg.edu.rp.c346.id20045524.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    TextView tvData;
    ListView lv;
    ArrayAdapter<Task> aa;
    ArrayList<Task> alTask;
    EditText etTask;
    DatePicker dp;
    boolean asc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = findViewById(R.id.tvData);
        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        lv = findViewById(R.id.lv);
        etTask = findViewById(R.id.etTask);
        dp = findViewById(R.id.displayDate);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(MainActivity.this);
                int getYear = dp.getYear();
                int getDate = dp.getDayOfMonth();
                int getMonth = dp.getMonth() + 1;
                String getDateStr = getDate + "/" + getMonth + "/" + getYear;
                String getTaskStr = etTask.getText().toString();
                db.insertTask(getTaskStr, getDateStr);

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(MainActivity.this);

                //populate TextView
                ArrayList<String> al = db.getTaskContent();
                String data = "";
                for (int i = 0; i < al.size(); i++){
                    data += i+1 + ". " + al.get(i) + "\n";
                }
                tvData.setText(data);

                //populate ListView
                alTask = db.getTasks(asc);
                db.close();
                asc = !asc;
                aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, alTask);
                lv.setAdapter(aa);
            }
        });

    }
}