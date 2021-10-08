package com.imagixit.higherstudies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    private EditText siemail,sipassword;
    private Button signinbtn;
    private TextView sifptv,signuptv;

    private FirebaseAuth imauth;
    private ProgressDialog lodingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        siemail = findViewById(R.id.signinemail);
        sipassword = findViewById(R.id.signinpassword);
        signinbtn= findViewById(R.id.signinbtn);
        sifptv = findViewById(R.id.signinfp);
        signuptv = findViewById(R.id.signuptv);

        imauth = FirebaseAuth.getInstance();
        lodingbar=new ProgressDialog(this);

        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this,SignupActivity.class));
            }
        });

        sifptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this,ResetpasswordActivity.class));
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });
    }

    private void loginuser() {

        final String email = siemail.getText().toString();
        final String password = sipassword.getText().toString();

        if(password.length()<8){

            Toast.makeText(this, "Password are are not valid length", Toast.LENGTH_SHORT).show();
        }

        else if(!email.equals("") && !password.equals("") ) {


            lodingbar.setTitle("User Login");
            lodingbar.setMessage("please wait,while we are checking");
            lodingbar.setCanceledOnTouchOutside(false);
            lodingbar.show();

            imauth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(SigninActivity.this,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                Toast.makeText(SigninActivity.this, "your login is Succesful", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                lodingbar.dismiss();
                                Toast.makeText(SigninActivity.this, "Please Write Valid Email and Password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
        else{
            Toast.makeText(this, "Please Complete the all details", Toast.LENGTH_SHORT).show();
        }

    }
}