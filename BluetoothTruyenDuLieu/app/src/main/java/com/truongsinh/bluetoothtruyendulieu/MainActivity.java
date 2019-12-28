package com.truongsinh.bluetoothtruyendulieu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    // khai báo các biến global
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//hc05 nó quy định kết nối trên cổng có số hiệu như dị.
    Button btnDen1,btnDen2;// dùng để điều khiển đèn
    ListView listView;// dùng để hiển thị danh sách
    ArrayAdapter<String> adapter; // dùng để làm cầu nối để hiển thị dữ liệu lên listview
    ArrayList<String> arrayList = new ArrayList<>();// dùng để chứa danh sách dữ liệu
    BluetoothAdapter bluetoothAdapter; // dùng để chứa thiết bị bluetooth của máy ta
    String macAddressSelected;// địa chỉ Mac được chọn
    BluetoothSocket bluetoothSocket;// dùng để tạo kênh kết nối
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ánh xạ view
        btnDen1 = findViewById(R.id.buttonDen1);
        btnDen2 = findViewById(R.id.buttonDen2);
        listView = findViewById(R.id.listView);
        //
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// lấy bluetooth của máy mình
        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();// lấy danh sách các thiết bị đã paired vs di động mình
        for(BluetoothDevice bluetoothDevice : deviceSet)
        {
            String ten = bluetoothDevice.getName();// lấy tên thiết bị
            String macAddress = bluetoothDevice.getAddress();// lấy địa chỉ
            String chuoi = ten+"-"+macAddress;
            arrayList.add(chuoi);
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);//làm cầu nối để gắn dữ liệu tới listview
        listView.setAdapter(adapter);// gắn adapter tới listview để hiển thị
        // xét sự kiện cho listview. kiểm tra người dùng nhấn vị trí nào, để lấy địa chỉ macAddress và kết nối đến thiết bị bluetooth đó
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chuoi = arrayList.get(position); // lấy dữ liệu dòng hiển thị listview
                macAddressSelected = chuoi.substring(chuoi.length()-17);// vì địa chỉ MAC có chiều dài 17 ký tự
                BluetoothDevice bluetoothDevice =
                        bluetoothAdapter.getRemoteDevice(macAddressSelected);//để tạo kết nối đến thiết bị có địa chỉ đc chọn

                try {
                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(myUUID);// tạo kênh giao tiếp 2 thiết bị trên cổng myUUID
                    bluetoothSocket.connect();// bắt đầu connect
                    listView.setVisibility(View.INVISIBLE);// nếu kết nối thành công sẽ ẩn listview đi
                } catch (IOException e) {
                    // nếu xảy ra lỗi kết nói nó sẽ nhảy xuống đây làm
                    Toast.makeText(MainActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();//Nếu kết nối thất bại thong báo người dùng
                    e.printStackTrace();
                }
            }
        });
        // thiết lập sự kiện btnDen1
        btnDen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGuiData("1");// đèn 1 gửi số 1
            }
        });
        btnDen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGuiData("2"); // đèn 2 gửi số 2
            }
        });
    }
    private  void xuLyGuiData(String data)
    {
        try {
            data =data +"."; // thêm ký tự kết thúc chuỗi, do ở dưới phần cứng quy định ký tự '.' làm ký tự kết thúc chuỗi
            OutputStream outputStream = bluetoothSocket.getOutputStream();// mở cổng để ghi dữ liệu xuống
            outputStream.write(data.getBytes());// gửi dữ liệu đi
            Toast.makeText(this, "Gửi thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gửi thất bại", Toast.LENGTH_SHORT).show();
        }

    }
}
