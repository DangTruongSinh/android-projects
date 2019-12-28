package com.truongsinh.foodyapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.truongsinh.foodyapp.R;


public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail;
    EditText edtMK;
    EditText edtNhapLaiMK;
    Button btnDangKy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky_activity);
        edtEmail = findViewById(R.id.edtDKEmail);
        edtMK = findViewById(R.id.edtDKMK);
        edtNhapLaiMK = findViewById(R.id.edtNhapLaiMK);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangKy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String email = edtEmail.getText().toString().trim();
        final String mk = edtMK.getText().toString().trim();
        String nhaplaiMK = edtNhapLaiMK.getText().toString();

        if(mk.equals(nhaplaiMK))
        {
            final ProgressDialog progressDialog = ProgressDialog.show(this,getString(R.string.load),getString(R.string.messageLogin));
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email,mk).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DangKyActivity.this, "Create account success! Please verify email to activate account", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        }
                        else
                        {
                            String kq = task.getException().toString();
                            if(kq.contains("FirebaseAuthInvalidCredentialsException"))
                                Toast.makeText(DangKyActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                            else if(kq.contains("FirebaseNetworkException"))
                                Toast.makeText(DangKyActivity.this, "Don't connect internet!", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(DangKyActivity.this, "Email have registered! Please register other email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        else
            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();

    }
}
