package com.truongsinh.firebasethemdulieu;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ValueEventListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<User> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(this);

        recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        for(int i =0;i<10;i++)
        {
            User user = new User("sinh",true,"22");
            arrayList.add(user);
        }
        CustomAdapter customAdapter = new CustomAdapter(arrayList);
        recyclerView.setAdapter(customAdapter);
    }

    public void xuly(View view) {
        User user = new User("dang truong sinh",true,"22");
        databaseReference.child("user").child("user2").setValue(user);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
class User
{
    private String hoten;
    private boolean gioitinh;
    private String tuoi;
    public User() {
    }

    public User(String hoten, boolean gioitinh, String tuoi) {
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.tuoi = tuoi;
    }


    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }
}
