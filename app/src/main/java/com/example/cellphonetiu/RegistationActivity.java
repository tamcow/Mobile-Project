package com.example.cellphonetiu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegistationActivity extends AppCompatActivity {

    public FloatingActionButton btnBack;
    public Button btnSignUp;
    public TextView txtSignIn;
    public EditText inputRegisUserName, inputRegisEmail, inputRegisPass, inputRegisConfirmPass, inputRegisPhone, inputRegisAddress;
    FirebaseFirestore db ;

    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }    // check password
    public boolean isPasswordConfirmed(String password, String confirmpassword){
        return password.equals(confirmpassword);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        db = FirebaseFirestore.getInstance();

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        btnBack = findViewById(R.id.floatingActionButton_Back);
        btnSignUp = findViewById(R.id.button_SignUp);
        inputRegisEmail = findViewById(R.id.input_email);
        inputRegisUserName = findViewById(R.id.input_regis_username);
        inputRegisAddress = findViewById(R.id.input_address);
        inputRegisPhone = findViewById(R.id.input_phone_number);
        inputRegisPass = findViewById(R.id.input_regis_PW);
        inputRegisConfirmPass = findViewById(R.id.input_confirmPW);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistationActivity.this, LoginActivity.class));
            }
        });

        txtSignIn = findViewById(R.id.txt_regis_signin);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistationActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputRegisUserName.getText().toString();
                String phone = inputRegisPhone.getText().toString();
                String pass = inputRegisPass.getText().toString();
                String confirmPW = inputRegisConfirmPass.getText().toString();
                String address = inputRegisAddress.getText().toString();
                String email = inputRegisEmail.getText().toString();

                if (isValidEmail(email)){
                    if (isPasswordConfirmed(pass, confirmPW)){
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference UsersCollection = db.collection("Users");
                        Query query = UsersCollection.whereEqualTo("Email", email).limit(1);

                        // thuc hien truy van
                        query.get().addOnCompleteListener(task -> {
                           if (task.isSuccessful()){
                               boolean userExist = !task.getResult().isEmpty();

                               if (userExist){
                                   // Neu user da ton tai -> thong bao dang nhap that bai
                                   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                                   TextView errormessage = dialog.findViewById(R.id.text_notify);
                                   errormessage.setText("User đã tồn tại");
                                   dialog.show();

                                   // Dong dialog sau 5s
                                   new Handler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           dialog.dismiss();
                                       }
                                   }, 5000);
                               }else {
                                   // Ma hoa pass
                                   String hashedPW = BCrypt.hashpw(pass, BCrypt.gensalt(6));

                                   // Them user
                                   Map<String, Object> user = new HashMap<>();
                                   user.put("UserName", username);
                                   user.put("Email",email);
                                   user.put("Phone", phone);
                                   user.put("Address", address);
                                   user.put("HashedPW", hashedPW);

                                   //Them user vao db
                                   db.collection("Users").add(user);

                                   //Thong bao dang ky thanh cong
                                   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 255, 128)));
                                   TextView sucessmessage = dialog.findViewById(R.id.text_notify);
                                   sucessmessage.setText("Đăng ký thành công");
                                   dialog.show();

                                   // Dong dialog sau 5s
                                   new Handler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           dialog.dismiss();
                                       }
                                   }, 5000);

                                   startActivity(new Intent(RegistationActivity.this, LoginActivity.class));
                               }
                           }else {
                               // Thong bao dang ky that bai
                               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                               TextView errormessage = dialog.findViewById(R.id.text_notify);
                               errormessage.setText("Đăng ký thất bại");
                               dialog.show();

                               // Dong dialog sau 5s
                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       dialog.dismiss();
                                   }
                               }, 5000);
                           }
                        });
                    }else {
                        // Thong bao dang ky that bai do password
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                        TextView errormessage = dialog.findViewById(R.id.text_notify);
                        errormessage.setText("Password khong chinh xac");
                        dialog.show();

                        // Dong dialog sau 5s
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 5000);
                    }
                }else {
                    // Thong bao dang ky that bai do nguoi dung da ton tai
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                    TextView errormessage = dialog.findViewById(R.id.text_notify);
                    errormessage.setText("Người dùng đã tồn tại");
                    dialog.show();

                    // Dong dialog sau 5s
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 5000);
                }
            }
        });
    }
}