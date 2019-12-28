package com.truongsinh.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.truongsinh.sqlite.DAO.NhanVienDAO;
import com.truongsinh.sqlite.DoiTuong.NhanVien;
import com.truongsinh.sqlite.SQLiteHelper.DataBaseNhanVien;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd;
    Button btnXoa;
    ListView listView;
    EditText editText;
    NhanVienDAO nhanVienDAO;
    ArrayList<NhanVien> arrNhanVien;
    ArrayAdapter<NhanVien> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnXoa = findViewById(R.id.btnXoa);
        listView = findViewById(R.id.lvList);
        editText = findViewById(R.id.edt);
        btnAdd.setOnClickListener(this);
        btnXoa.setOnClickListener(this);
        arrNhanVien = new ArrayList<NhanVien>();
        nhanVienDAO = new NhanVienDAO(this);
        arrNhanVien = nhanVienDAO.getAllData();
        adapter = new ArrayAdapter<NhanVien>(MainActivity.this,android.R.layout.simple_list_item_1,arrNhanVien);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.xoa) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            NhanVien nhanVien = arrNhanVien.get(info.position);
            nhanVienDAO.xoaDuLieu(nhanVien);
            arrNhanVien.remove(nhanVien);
            adapter.notifyDataSetChanged();
        }
        else
        {

        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAdd) {
            String name = editText.getText().toString();
            NhanVien nhanVien = new NhanVien(name, 0);
            if (nhanVienDAO.themNhanVien(nhanVien)) {
                arrNhanVien.add(nhanVien);
                adapter.notifyDataSetChanged();
            }
        }
        else
        {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nhanVienDAO.close();
    }
}
