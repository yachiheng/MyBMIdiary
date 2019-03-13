package com.example.user.myBMIdiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myBMIdiary.controller.PersonControllerImpl;
import com.example.user.myBMIdiary.model.MySharedPreference;
import com.example.user.myBMIdiary.model.Person;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listView = null;
    private ArrayList<Person> datas = null;
    private ListViewAdapter listViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int themeId = MySharedPreference.getTheme(this);
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.listview);

        final PersonControllerImpl personController = new PersonControllerImpl();
        datas = personController.findAll(this);
        listViewAdapter = new ListViewAdapter();
        listView.setAdapter(listViewAdapter);
        //添加事件承擔
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println(datas.get(position).getMesDate());
                Intent intent = new Intent();
                intent.setClass(ListActivity.this, EntryActivity.class);
                intent.putExtra("mesDate", datas.get(position).getMesDate());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //System.out.println("onItemLong");
                //final int themeId = MySharedPreference.getTheme(ListActivity.this);
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //確認是否點擊了「是」的按鈕
                        if (DialogInterface.BUTTON_NEUTRAL == which) {
                            //先找出要刪除的物件
                            Person person = datas.get(position);
                            //呼叫Person Controller將該測量日期的記錄刪除
                            int rows = personController.delete(ListActivity.this, person.getMesDate());
                            //同時移除 datas 所有記錄集合中的 該筆記錄
                            datas.remove(position);
                            if (rows >= 1) {
                                Toast.makeText(ListActivity.this, "刪除成功!", Toast.LENGTH_SHORT).show();
                                //要求listViewAdapter重新整理
                                listViewAdapter.notifyDataSetChanged();
                            }
                        }
//                        else{
//                            dialog.dismiss();
//                        }
                    }
                };

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ListActivity.this)
                                .setIcon(R.mipmap.ic_launcher)
                                .setMessage("是否要刪除此筆記錄?")
                                .setTitle(R.string.app_name)
//                                .setNegativeButton("左按鈕", null)
//                                .setNeutralButton("中按鈕", null)
//                                .setPositiveButton("右按鈕", null)
                                .setNeutralButton(android.R.string.ok, listener)
                                .setPositiveButton(android.R.string.cancel, listener)
                                //將對話框設定成強制回應
                                .setCancelable(false);

                //顯示對話框
                builder.show();
                return true;
            }
        });

        //處理 FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,
                        EntryActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    /**
     * 標準內部類別，提供資料展示物件的 內容提供者
     */
    private class ListViewAdapter extends BaseAdapter {

        /**
         * 記錄總筆數
         *
         * @return
         */
        @Override
        public int getCount() {
            return datas != null ? datas.size() : 0;
        }

        /**
         * 依據position取得記錄的項目
         *
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return null;
        }

        /**
         * 依據position取得對應的item id(差別在有沒有 header)
         *
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * 資料顯示的主要結合區，將資料內容及外觀整合在一個View的元件上
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.listview_item, null
                );
            }

            //依據position決定當前的筆數，取得對應的Person物件
            Person data = datas.get(position);

            //尋找在ListView欲顯示的 記錄 外觀物件
            TextView tv6 = convertView.findViewById(R.id.textView6);
            TextView tv7 = convertView.findViewById(R.id.textView7);
            TextView tv8 = convertView.findViewById(R.id.textView8);
            TextView tv9 = convertView.findViewById(R.id.textView9);
            ImageView iv2 = convertView.findViewById(R.id.imageView2);
            //...

            //將Person物件的資料，填寫至ListView 的 Item當中
            tv6.setText(data.getHeight() + "");
            tv7.setText(data.getWeight() + "");
            tv8.setText(data.getMesDate() + "");
            tv9.setText(data.getBmi() + "");
            iv2.setImageResource(getBMILevel(data.isGender(), data.getBmi()));

            return convertView;
        }
    }

    public int getBMILevel(boolean gender, double bmi) {

        if (gender) {

            if (bmi < 18.5) {
                return R.drawable.newbmim1;
            } else if (bmi < 24) {
                return R.drawable.newbmim2;
            } else if (bmi < 27) {
                return R.drawable.newbmim3;
            } else if (bmi < 30) {
                return R.drawable.newbmim4;
            } else if (bmi < 35) {
                return R.drawable.newbmim5;
            } else {
                return R.drawable.newbmim6;
            }

        } else {
            if (bmi < 18.5) {
                return R.drawable.newbmif1;
            } else if (bmi < 24) {
                return R.drawable.newbmif2;
            } else if (bmi < 27) {
                return R.drawable.newbmif3;
            } else if (bmi < 30) {
                return R.drawable.newbmif4;
            } else if (bmi < 35) {
                return R.drawable.newbmif5;
            } else {
                return R.drawable.newbmif6;
            }
        }
    }
}
