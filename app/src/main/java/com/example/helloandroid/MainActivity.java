package com.example.helloandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String callee;
    private EditText number_edit;
    final int RESULT_FOR_HISTORY = 1;
    final int RESULT_FOR_LIST = 2;

    Bundle get_History(Map<String,?> data){
        Collection<String> date_collect = data.keySet();
        ArrayList<String> date_list = new ArrayList<String>(date_collect);
        Collection<String> number_collect = (Collection<String>)data.values();
        ArrayList<String> number_list = new ArrayList<String>(number_collect);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("number", number_list);
        bundle.putStringArrayList("date", date_list);
        return bundle;
    }

    Bundle get_contracts(Map<String,?> data){
        Collection<String> name_collect = data.keySet();
        ArrayList<String> name_list = new ArrayList<String>(name_collect);
        Collection<String> number_collect = (Collection<String>)data.values();
        ArrayList<String> number_list = new ArrayList<String>(number_collect);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("name", name_list);
        bundle.putStringArrayList("number", number_list) ;
        return bundle;
    }


    // 创建联系人
    private void create_contacts(){
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        final EditText etName = view.findViewById(R.id.et_name);
        final EditText etNumber = view.findViewById(R.id.et_number);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view).setTitle("创建新联系人")//.setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("创建", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContactsSharedPreferences.setContacts(etName.getText().toString(), etNumber.getText().toString(),MainActivity.this);
                        Toast.makeText(MainActivity.this,   "创建成功！", Toast.LENGTH_LONG).show();
                    }
                });

        builder.create().show();
    }

    // 父窗口获取子窗口关闭后传值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == RESULT_FOR_HISTORY){
            if(resultCode == RESULT_OK){
//                Intent i = new Intent(MainActivity.this,HistoryActivity.class);
                Bundle bundle = data.getExtras();
                callee = (String)bundle.get("callee");
                number_edit.setText(callee);
            }
        }
        else if(requestCode == RESULT_FOR_LIST){
            if(resultCode == RESULT_OK){
//                Intent i = new Intent(MainActivity.this,ContactsActivity.class);
                Bundle bundle = data.getExtras();
                callee = (String)bundle.get("callee");
                number_edit.setText(callee);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number_edit = (EditText) findViewById(R.id.phonenumber_id);

        // 一堆按钮的初始化以及监听事件
        final Button []btn = new Button[10];
        btn[0] = (Button) findViewById(R.id.button0);
        btn[1] = (Button) findViewById(R.id.button1);
        btn[2] = (Button) findViewById(R.id.button2);
        btn[3] = (Button) findViewById(R.id.button3);
        btn[4] = (Button) findViewById(R.id.button4);
        btn[5] = (Button) findViewById(R.id.button5);
        btn[6] = (Button) findViewById(R.id.button6);
        btn[7] = (Button) findViewById(R.id.button7);
        btn[8] = (Button) findViewById(R.id.button8);
        btn[9] = (Button) findViewById(R.id.button9);
        Button btn_star = (Button) findViewById(R.id.button_star);
        Button btn_sharp = (Button) findViewById(R.id.button_sharp);
        Button button_call = (Button) findViewById(R.id.button_call);
        Button button_back = (Button) findViewById(R.id.button_back);
        Button button_del = (Button) findViewById(R.id.button_del);
        Button button_history = (Button) findViewById(R.id.button_history);
        Button button_list = (Button)findViewById(R.id.button_list);
        Button button_new = (Button)findViewById(R.id.button_new);



        button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, ?> data = HistorySharedPreferences.getData(MainActivity.this);
                // 为了在历史记录里显示已存储的号码
                final Map<String, ?> contracts = ContactsSharedPreferences.getData(MainActivity.this);
                // 转换存入map
                Bundle bundle = get_History(data);
                Bundle bundle_contracts = get_contracts(contracts);
                bundle.putBundle("contracts", bundle_contracts);

                Intent i = new Intent(MainActivity.this,HistoryActivity.class);
                i.putExtras(bundle);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                startActivityForResult(i, RESULT_FOR_HISTORY, transitionActivityOptions.toBundle());
            }
        });

        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, ?> data = ContactsSharedPreferences.getData(MainActivity.this);
                // 转换存入map
                Bundle bundle = get_contracts(data);
                Intent i = new Intent(MainActivity.this, ContactsActivity.class);
                i.putExtras(bundle);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                startActivityForResult(i, RESULT_FOR_LIST, transitionActivityOptions.toBundle());
            }
        });

        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_contacts();
            }
        });


        for(int i=0;i<10;i++){
            final int num = i ; // Variable 'i' is accessed from within inner class, needs to be declared final
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = number_edit.getSelectionStart();
                    Editable editable = number_edit.getText();
                    editable.insert(index, String.valueOf(num));
                }
            });
        }

        btn_star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int index = number_edit.getSelectionStart();
                Editable editable = number_edit.getText();
                editable.insert(index, "*");
            }
        });

        // 删除光标前字符
        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int index = number_edit.getSelectionStart();
                Editable editable = number_edit.getText();
                editable.delete(index-1, index);
            }
        });

        // 清空
        button_del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                number_edit.setText("");
            }
        });

        btn_sharp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int index = number_edit.getSelectionStart();
                Editable editable = number_edit.getText();
                editable.insert(index, "#");
            }
        });

        button_call.setOnClickListener(new Button.OnClickListener() {
                                      @Override
                                      public void onClick(View b) {
                                          callee = number_edit.getText().toString();
                                          if (PhoneNumberUtils.isGlobalPhoneNumber(callee)) {
                                              // 缓存拨号历史
                                              load_history();
                                              // 申请权限且拨号
                                              requestPermission();
                                          } else {
                                              Toast.makeText(MainActivity.this, R.string.notify_incorrect_phonenumber,
                                                      Toast.LENGTH_LONG).show();
                                          }
                                      }
                                  }
        );
    }

    private void load_history(){
        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean status = HistorySharedPreferences.setNumber(callee, df.format(new Date()),MainActivity.this);
    }
    private void requestPermission() {
        // 安卓8.0以上需要申请获取权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
//                int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
//
//                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
//                            RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
//                    return;
//                }
//                else
//                {
//                    callPhone(callee);
//                }
            // 获取权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, 0x11);
            callPhone(callee);
        }
        else
        {
            callPhone(callee);
        }
    }

    private void callPhone(String callee){
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + callee));
        startActivity(i);
    }

}
