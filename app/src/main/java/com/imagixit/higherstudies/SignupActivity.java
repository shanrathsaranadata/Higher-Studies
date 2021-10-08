package com.imagixit.higherstudies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private EditText suname,suemail,sumobileno,suaddress,supassword,sucpassword;
    private Button  subtn;
    private TextView logintv;
    private ProgressDialog lodingbar;
    private FirebaseAuth imauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        suname = findViewById(R.id.signupname);
        suemail = findViewById(R.id.signupemail);
        sumobileno = findViewById(R.id.signupmobileno);
        suaddress = findViewById(R.id.signupaddress);
        supassword = findViewById(R.id.signuppass);
        sucpassword = findViewById(R.id.signupcpass);
        subtn = findViewById(R.id.signupbtn);
        logintv = findViewById(R.id.logintv);

        imauth = FirebaseAuth.getInstance();
        lodingbar=new ProgressDialog(this);

        logintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,SigninActivity.class));
            }
        });
        subtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {

        String regname = suname.getText().toString();
        String regemail = suemail.getText().toString();
        String regnumber = sumobileno.getText().toString();
        String regaddress = suaddress.getText().toString();
        String regpassword = supassword.getText().toString();
        String regcpassword = sucpassword.getText().toString();


        if(TextUtils.isEmpty(regname)){
            Toast.makeText(this, "Please write your name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regemail)){
            Toast.makeText(this, "Please write your email..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regnumber)){
            Toast.makeText(this, "Please write your mobile number..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regaddress)){
            Toast.makeText(this, "Please write your address..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regpassword)){
            Toast.makeText(this, "Please write your password..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regcpassword)){
            Toast.makeText(this, "Please write your confirmed password..", Toast.LENGTH_SHORT).show();
        }
        else if(!regpassword.equals(regcpassword)){
            Toast.makeText(this, "Password are not Match", Toast.LENGTH_SHORT).show();
        }
        else if(regpassword.length()<8){
            Toast.makeText(this, "Password are are not valid length", Toast.LENGTH_SHORT).show();
        }
        else {

            lodingbar.setTitle("Create Account");
            lodingbar.setMessage("please wait,while we are checking");
            lodingbar.setCanceledOnTouchOutside(false);
            lodingbar.show();

            Valitadephonenumber(regname,regemail,regnumber,regaddress,regpassword);

        }

    }

    private void Valitadephonenumber(String regname, String regemail, String regnumber, String regaddress, String regpassword) {

        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();


        imauth.createUserWithEmailAndPassword(regemail,regpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){


                            String sid = imauth.getCurrentUser().getUid();

                            HashMap<String,Object> sellermap = new HashMap<>();

                            sellermap.put("sid",sid);
                            sellermap.put("name",regname);
                            sellermap.put("email",regemail);
                            sellermap.put("phone",regnumber);
                            sellermap.put("address",regaddress);
                            sellermap.put("password",regpassword);

                            rootref.child("Users").child(sid).updateChildren(sellermap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            lodingbar.dismiss();
                                            Toast.makeText(SignupActivity.this, "Your are register Higher Studies APP", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignupActivity.this,SigninActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });

                        }
                        else{
                            lodingbar.dismiss();
                            Toast.makeText(SignupActivity.this, "Please Write Valid Email and Password " +task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}