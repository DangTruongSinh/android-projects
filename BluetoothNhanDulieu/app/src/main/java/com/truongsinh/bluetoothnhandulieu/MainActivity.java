package com.truongsinh.bluetoothnhandulieu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements Runnable {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//hc05 nó quy định kết nối trên cổng có số hiệu như dị.
    TextView txtNhietDo,txtDoAm; // dùng để hiển thị nhiệt độ và độ ẩm
    ListView listView;// dùng để hiển thị danh sách bluetooth
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList = new ArrayList<>();
    BluetoothAdapter bluetoothAdapter; // dùng để chứa thiết bị bluetooth của máy ta
    String macAddressSelected;// địa chỉ Mac được chọn
    BluetoothSocket bluetoothSocket;// dùng để tạo kênh kết nối
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ view
        txtNhietDo = findViewById(R.id.txtNhietDo);
        txtDoAm = findViewById(R.id.txtDoAm);
        listView = findViewById(R.id.listview);
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
                    // cho worker thread chạy
                    Thread thread = new Thread(MainActivity.this);
                    thread.start();
                } catch (IOException e) {
                    // nếu xảy ra lỗi kết nói nó sẽ nhảy xuống đây làm
                    Toast.makeText(MainActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();//Nếu kết nối thất bại thong báo người dùng
                    e.printStackTrace();
                }
            }
        });

    }
    // khai báo 1 handler để nhận message
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = (String) msg.obj;// lấy nội dung của message ra
            float nhietdo, doam;
            // thực hiện quá trình tách chuỗi để phân thành nhiệt độ , độ ẩm
            String s1 = s.substring(0, 2) + "." + s.substring(2, 3);
            String s2 = s.substring(3, 5) + "." + s.substring(5, 6);
            // convert nhiệt độ và độ ẩm sang float
            nhietdo = Float.parseFloat(s1);
            doam = Float.parseFloat(s2);
            // thiết lập giá trị hiển thị lên textview
            txtNhietDo.setText(nhietdo+"");
            txtDoAm.setText(doam+"");
        }
    };
    @Override
    public void run() {
        InputStream inputStream;// khai báo 1 đối tượng inputstream để nhận dữ liệu gửi lên
        byte mangByte[] = new byte[6]; // khai báo mảng để nhận dữ liệu(dữ liệu gửi lên 6 ký tự
        int soluongByte;
        while (true)
        {
            try {
                inputStream = bluetoothSocket.getInputStream();// khởi gán giá trị cho inputStream
                soluongByte = inputStream.available();// kiểm tra tại cổng vào có bao nhiêu byte
                // nếu đủ 6 byte thì xử lý( do dữ liệu gửi lên  6 ký tự
                if(soluongByte>=6)
                {
                    inputStream.read(mangByte);// lưu vào mảng
                    String kq = new String(mangByte, "US-ASCII"); // chuyển kiểu dữ liệu từ byte sang String
                    Log.d("tag", kq);
                    Message message = new Message();// tạo 1 message để gửi cho handler
                    message.obj =kq;// đặt giá trị vào đó message
                    handler.sendMessage(message);// gửi message tới handler
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
