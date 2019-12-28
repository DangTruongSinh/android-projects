package com.example.doan2;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.TimePickerDialog;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.util.Log;
        import android.view.View;
        import android.widget.CompoundButton;
        import android.widget.ImageView;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.TimePicker;


        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;

        import java.io.IOException;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;


        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;
        import okhttp3.WebSocket;
        import okhttp3.WebSocketListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView txtNhietDo;
    private Switch switchQuat;
    private ImageView imgDen1;
    private Switch switchDen1;
    private ImageView imgDen2;
    private Switch switchDen2;
    private TextView txtCheDo;
    private Switch switchMode;
    private OkHttpClient client;
    private boolean flagMode;
    private TextView txtQuat;
    private boolean den1_status;
    private boolean den2_status;
    WebSocket ws;
    private String thoigianmo;
    private String thoigiantat;
    private TextView txtThoigianMo;
    private TextView txtThoigianTat;
    private boolean isloaded = false;
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void onClick(View view) {
        if(!flagMode)
        {
            if(view.getId() == R.id.imgDen1 || view.getId() == R.id.imgDen2) {
                ThietBi thietBi = null;
                if (view.getId() == R.id.imgDen1) {
                    den1_status = !den1_status;
                    thietBi = new ThietBi((long) 1, "den1", null, den1_status, flagMode, thoigianmo, thoigiantat);
                } else if (view.getId() == R.id.imgDen2) {
                    den2_status = !den2_status;
                    thietBi = new ThietBi((long) 2, "den2", null, den2_status, flagMode, thoigianmo, thoigiantat);
                }
                String value = null;
                try {
                    value = mapper.writeValueAsString(thietBi);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                ws.send(value);
            }
        }
        else
        {
            if(view.getId() == R.id.txtThoigianMo)
            {
                chonGio(thoigianmo,"thoigianmo",txtThoigianMo);
            }
            else if(view.getId() == R.id.txtThoigianTat)
            {
                chonGio(thoigiantat,"thoigiantat",txtThoigianTat);
            }

        }


    }
    private void chonGio(String time, final String typeTime, final TextView view)
    {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            final Date date = format.parse(time);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int gio = calendar.get(Calendar.HOUR_OF_DAY);
            int phut = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    calendar.set(0,0,0,i,i1);
                    String timeResult = format.format(calendar.getTime());
                    view.setText(timeResult);
                    if(typeTime.equals("thoigianmo"))
                        thoigianmo = timeResult;
                    else
                        thoigiantat = timeResult;
                    String value = null;
                    ThietBi thietBi;
                    try {
                        thietBi = new ThietBi(null, "chedo", null, null, flagMode, thoigianmo, thoigiantat);
                        Log.d("tag",thoigianmo);
                        value = mapper.writeValueAsString(thietBi);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    ws.send(value);
                }
            },gio,phut,true);
            timePickerDialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d("isload",isloaded+"");
        if(isloaded)
        {
            Log.d("tag","change:"+flagMode);
            ThietBi thietBi = null;

            if (compoundButton.getId() == R.id.switchQuat)
                thietBi = new ThietBi((long) 3, "quat", null, b, flagMode, thoigianmo, thoigiantat);
            else if(compoundButton.getId() == R.id.switchMode)
                thietBi = new ThietBi(null, "chedo", null, null, b, thoigianmo, thoigiantat);
            String value = null;
            try {
                value = mapper.writeValueAsString(thietBi);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ws.send(value);
        }

    }




    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output(text);
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ThietBi thietBi = (ThietBi) msg.obj;
            if(thietBi.getTen().equals("den1"))
            {
                if(thietBi.getTrangthai())
                {
                    imgDen1.setImageResource(R.drawable.ledsang);
                    switchDen1.setChecked(true);
                    den1_status = true;
                }
                else
                {
                    imgDen1.setImageResource(R.drawable.ledtat);
                    switchDen1.setChecked(false);
                    den1_status = false;
                }
            }
            else if(thietBi.getTen().equals("den2"))
            {
                if(thietBi.getTrangthai())
                {
                    imgDen2.setImageResource(R.drawable.ledsang);
                    switchDen2.setChecked(true);
                    den2_status = true;
                }
                else
                {
                    imgDen2.setImageResource(R.drawable.ledtat);
                    switchDen2.setChecked(false);
                    den2_status = false;
                }
            }
            else if(thietBi.getTen().equals("quat")) {

                if (thietBi.getTrangthai())
                {
                    switchQuat.setChecked(true);
                    txtQuat.setText("ON");
                }
                else
                {
                    switchQuat.setChecked(false);
                    txtQuat.setText("OFF");
                }
            }
            if(thietBi.getChedo() != null )
            {
                if(thietBi.getChedo())
                {
                    txtCheDo.setText("AUTO");
                    switchMode.setChecked(true);
                    switchQuat.setVisibility(View.INVISIBLE);
                    switchDen1.setVisibility(View.INVISIBLE);
                    switchDen2.setVisibility(View.INVISIBLE);
                    txtQuat.setVisibility(View.VISIBLE);
                    flagMode = true;
                }
                else{
                    Log.d("message","chay ne");
                    txtCheDo.setText("MANUAL");
                    switchMode.setChecked(false);
                    switchQuat.setVisibility(View.VISIBLE);
                    txtQuat.setVisibility(View.INVISIBLE);
                    switchDen1.setVisibility(View.VISIBLE);
                    switchDen2.setVisibility(View.VISIBLE);
                    flagMode = false;
                }
            }
            if(thietBi.getGiatri()!=null)
                txtNhietDo.setText(thietBi.getGiatri()+"");
            if(thietBi.getThoigianmo()!=null)
            {
                thoigianmo = thietBi.getThoigianmo();
                txtThoigianMo.setText(thoigianmo);
            }
            if(thietBi.getThoigiantat()!= null)
            {
                thoigiantat = thietBi.getThoigiantat();
                txtThoigianTat.setText(thoigiantat);
            }
            isloaded = true;
        }
    };
    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.d("message",txt);

                    if(txt.startsWith("["))
                    {
                        ThietBi[] list = mapper.readValue(txt,ThietBi[].class);
                        for(ThietBi x: list)
                        {
                            Message message = new Message();
                            message.obj = x;
                            handler.sendMessage(message);
                        }
                    }
                    else
                    {
                        ThietBi thietBi = mapper.readValue(txt,ThietBi.class);
                        Message message = new Message();
                        message.obj = thietBi;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCheDo = findViewById(R.id.txtCheDo);
        txtNhietDo = findViewById(R.id.txtNhietDo);
        switchQuat = findViewById(R.id.switchQuat);
        switchDen1 = findViewById(R.id.switchDen1);
        switchDen2 = findViewById(R.id.switchDen2);
        imgDen1 = findViewById(R.id.imgDen1);
        imgDen2 = findViewById(R.id.imgDen2);
        switchMode = findViewById(R.id.switchMode);
        txtQuat = findViewById(R.id.txtQuat);
        txtThoigianMo = findViewById(R.id.txtThoigianMo);
        txtThoigianTat = findViewById(R.id.txtThoigianTat);
        client = new OkHttpClient();
        initConnect();
        imgDen1.setOnClickListener(this);
        imgDen2.setOnClickListener(this);
        switchQuat.setOnCheckedChangeListener(this);
        switchMode.setOnCheckedChangeListener(this);
        txtThoigianMo.setOnClickListener(this);
        txtThoigianTat.setOnClickListener(this);
    }

    private void initConnect() {
        //ws://doan21.j.layershift.co.uk/realtime-data
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Request request = new Request.Builder().url("ws://smarthome.j.layershift.co.uk/realtime-data?username="
                +username+"&password="+password).build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

}
