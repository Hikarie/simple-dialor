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

public class ContactsActivity extends AppCompatActivity {

    private ListView lv;
    private List<Map<String,?>> lists;
    private SimpleAdapter adapter;
    private String callee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

//        // 获取存储器中键值对
//        final Map<String, ?> data = ContactsSharedPreferences.getData(ContactsActivity.this);L
//        // 转换存入map
//        Collection<String> num_collect = data.keySet();
//        List<String> number_list = new ArrayList<String>(num_collect);
//        Collection<String> date_collect = (Collection<String>)data.values();
//        List<String> date_list = new ArrayList<String>(date_collect);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<String> number_list = (ArrayList<String>)bundle.get("name");
        ArrayList<String> date_list = (ArrayList<String>)bundle.get("number");
        lists = new ArrayList<>();
        for(int i=0;i<number_list.size();i++){
            Map<String, String> map = new HashMap<>();
            map.put("name", number_list.get(i));
            map.put("number", date_list.get(i));
            lists.add(map);
        }

        lv = (ListView)findViewById(R.id.lv);
        adapter = new SimpleAdapter(ContactsActivity.this, lists, R.layout.list_item_contacts,
                new String[]{"name","number"}, new int[]{R.id.item_name, R.id.item_number});
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callee = adapter.getItem(position).toString(); // "{date=... number=...}"
//                callee = (String) lv.getItemAtPosition(position);
                callee = callee.substring(callee.indexOf("=")+1,callee.indexOf(" "));
                Intent i = getIntent();
                i.putExtra("callee", callee);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
