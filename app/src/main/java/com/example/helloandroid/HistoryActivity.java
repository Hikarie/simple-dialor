package com.example.helloandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private ListView lv;
    private List<Map<String,?>> lists;
    private SimpleAdapter adapter;
    private String callee;
    private ArrayList<String> name_list;
    private ArrayList<String> num_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

//        // 获取存储器中键值对
//        final Map<String, ?> data = HistorySharedPreferences.getData(HistoryActivity.this);
//        // 转换存入map
//        Collection<String> num_collect = data.keySet();
//        List<String> number_list = new ArrayList<String>(num_collect);
//        Collection<String> date_collect = (Collection<String>)data.values();
//        List<String> date_list = new ArrayList<String>(date_collect);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<String> number_list = (ArrayList<String>)bundle.get("number");
        ArrayList<String> date_list = (ArrayList<String>)bundle.get("date");

        Bundle contracts = (Bundle) bundle.get("contracts");
        name_list = (ArrayList<String>)contracts.get("name");
        num_list = (ArrayList<String>)contracts.get("number");

        lists = new ArrayList<>();
        for(int i=0;i<number_list.size();i++){
            Map<String, String> map = new HashMap<>();
            String key = number_list.get(i);
            // 如果已存储在通讯录，显示名称
            if(num_list.contains(key))
                map.put("number", name_list.get(num_list.indexOf(key)));
            else
                map.put("number", number_list.get(i));
            map.put("date", date_list.get(i));
            lists.add(map);
        }



        lv = (ListView)findViewById(R.id.lv);
        adapter = new SimpleAdapter(HistoryActivity.this, lists, R.layout.list_item_history, new String[]{"number","date"}, new int[]{R.id.item_number, R.id.item_date});
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callee = adapter.getItem(position).toString(); // "{date=... number=...}"
//                callee = (String) lv.getItemAtPosition(position);
                callee = callee.substring(callee.lastIndexOf("=")+1,callee.length()-2);
                if(name_list.contains(callee))
                    callee = num_list.get(name_list.indexOf(callee));
                Intent i = getIntent();
                i.putExtra("callee", callee);
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }
}
