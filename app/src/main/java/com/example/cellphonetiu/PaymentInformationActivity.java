package com.example.cellphonetiu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentInformationActivity extends AppCompatActivity {

    TextView txtEmail, txtAdress, txtDate, txtTime, txtTotal, txtProductname;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_information);

        txtEmail = findViewById(R.id.txt_email);
        txtAdress = findViewById(R.id.txt_address);
        txtDate = findViewById(R.id.date);
        txtTime = findViewById(R.id.time);
        txtTotal = findViewById(R.id.txt_total);
        txtProductname = findViewById(R.id.txt_productname);

        showInfoBill();

    }

    private void showInfoBill(){
        SharedPreferences preferences = getSharedPreferences("UserEmail", MODE_PRIVATE);
        String userEmail = preferences.getString("User_Email", "");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionUser = db.collection("Bill");

        collectionUser.whereEqualTo("Email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);

                        // Kiểm tra xem trường "Email" có tồn tại trong tài liệu hay không
                        if (document.contains("Email")) {
                            String date = document.getString("Date");
                            String address = document.getString("Address");
                            String time = document.getString("Time");
                            String total = document.getString("Total");
                            String productinfo = document.getString("ProductInfo");

                            txtEmail.setText(userEmail);
                            txtAdress.setText(address);
                            txtDate.setText(date);
                            txtTime.setText(time);
                            txtTotal.setText(total);
                            txtProductname.setText(productinfo);
                        }
                    }
                });
    }

}