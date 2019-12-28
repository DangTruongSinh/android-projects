package com.truongsinh.test;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ValueEventListener, ChildEventListener, Transaction.Handler {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ArrayList<User> arrayList = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomAdapter(arrayList);
        recyclerView.setAdapter(customAdapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user");
        databaseReference.addChildEventListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword("sinh123@gmail.com","123456").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Create a new account success!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "thất bại", Toast.LENGTH_SHORT).show();
            }
        });


    }
    // listen data change
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
        for(DataSnapshot x : dataSnapshots)
        {
            User user = x.getValue(User.class);
            arrayList.add(user);
        }
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        User user = dataSnapshot.getValue(User.class);
        arrayList.add(user);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        arrayList.clear();
        customAdapter.notifyDataSetChanged();
        databaseReference.removeEventListener((ChildEventListener) this);
        databaseReference.addChildEventListener(this);
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        arrayList.clear();
        customAdapter.notifyDataSetChanged();
        databaseReference.removeEventListener((ChildEventListener) this);
        databaseReference.addChildEventListener(this);
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(this, "Don't connect internet", Toast.LENGTH_SHORT).show();
    }

    // example update data user1 from firebase
    public void update(View view) {

       // databaseReference.child("user2").runTransaction(this);
   /*     HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("hoten","nguyen thi");
        hashMap.put("gioitinh",true);
        hashMap.put("tuoi","32");
        HashMap<String,Object> hashMap1 = new HashMap<>();
        hashMap1.put(key,hashMap);
        databaseReference.updateChildren(hashMap1);*/

    }

    @NonNull
    @Override
    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
        User user = mutableData.getValue(User.class);
        if(user ==  null)
        {
            Log.d("tag","doTransaction");
            return Transaction.success(mutableData);
        }
        databaseReference.child("user2").child("hoten").setValue("sinh vo cuc");

        return Transaction.success(mutableData);
    }

    @Override
    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
            Log.d("tag","complete");
    }
}
