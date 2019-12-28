package com.truongsinh.loginfbvsfirebase;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.Authenticator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    StorageReference storageReference;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        imageView = findViewById(R.id.imageView);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
  /*      callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        imageView = findViewById(R.id.imageView);
        loginButton.setReadPermissions("email", "public_profile");

    /*    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               String token = loginResult.getAccessToken().getToken();
               Log.d("TAG","token:"+ token);
                AuthCredential authCredential = FacebookAuthProvider.getCredential(token);
                mAuth.signInWithCredential(authCredential).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String result = "";
                            Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            result += firebaseUser.getDisplayName();

                            Log.d("TAG","Name:"+result);
                        }
                        else
                            Toast.makeText(MainActivity.this, "Failse!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });
        printKeyHash();*/

       // Log.d("TAG",storageReference.toString());
    }

    private void printKeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.truongsinh.loginfbvsfirebase",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature:packageInfo.signatures)
            {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("TAG", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void upload(View view) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] data=  byteArrayOutputStream.toByteArray();
        storageReference.child("image").child("image.jpeg");
        storageReference.putBytes(data);
     //   UploadTask uploadTask = new UploadTask().
    }
}
