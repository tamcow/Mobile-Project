package com.example.cellphonetiu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cellphonetiu.CartActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.mindrot.jbcrypt.BCrypt;


public class LoginActivity extends AppCompatActivity {

    public TextView txtSignUp;
    public Button btnsignin;
    public EditText inputuseremail, inputpass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        btnsignin = (Button) findViewById(R.id.btnsignin);
        inputuseremail = (EditText) findViewById(R.id.Input_username);
        inputpass = (EditText) findViewById(R.id.Input_password);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String useremail = inputuseremail.getText().toString();
                String pass = inputpass.getText().toString();


                // Tham chiếu đến collection "Users"
                CollectionReference usersCollection = db.collection("Users");

                usersCollection.whereEqualTo("Email", useremail).limit(1)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                DocumentSnapshot document = task.getResult().getDocuments().get(0);

                                // Kiểm tra xem trường "password" có tồn tại trong tài liệu hay không
                                if (document.contains("HashedPW")) {
                                    // Lấy mật khẩu từ trường "password"
                                    String password = document.getString("HashedPW");

                                    boolean isPasswordMatched = BCrypt.checkpw(pass, password);
                                    if(isPasswordMatched){
                                        // thông báo dăng nhập thành công
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 255, 128)));
                                        TextView textMessage = dialog.findViewById(R.id.text_notify);
                                        String id = document.getId();
                                        textMessage.setText("Đăng nhập thành công");
                                        dialog.show();

                                        // Đóng Dialog sau vài giây
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        }, 5000);

                                        //direct to homepage

                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.putExtra("user_id", id);
                                        startActivity(intent);


                                        String EMAIL= document.getString("Email");

                                        SharedPreferences preferences = getSharedPreferences("UserEmail", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("User_Email", EMAIL);
                                        editor.apply();
                                    } else{
                                        // thông báo sai mật khẩu
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                                        TextView textMessage = dialog.findViewById(R.id.text_notify);
                                        textMessage.setText("Sai mật khẩu");
                                        dialog.show();

                                        // Đóng Dialog sau vài giây
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        }, 5000);
                                    }
                                } else {
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                                    TextView textMessage = dialog.findViewById(R.id.text_notify);
                                    textMessage.setText("Không tồn tại");
                                    dialog.show();

                                    // Đóng Dialog sau vài giây
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                        }
                                    }, 5000);
                                }
                            } else {
                                // thông báo lỗi bất ngờ
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                                TextView textMessage = dialog.findViewById(R.id.text_notify);
                                textMessage.setText("Lỗi");
                                dialog.show();

                                // Đóng Dialog sau vài giây
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                }, 5000);
                            }
                        });
                //check password

            }
        });
        /*btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });*/

        txtSignUp = (TextView) findViewById(R.id.textview_signupnow);
        txtSignUp.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent_Login = new Intent(LoginActivity.this, RegistationActivity.class);
                        startActivity(intent_Login);
                    }
                }
        );
    }
}