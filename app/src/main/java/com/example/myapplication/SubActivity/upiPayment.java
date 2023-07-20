package com.example.myapplication.SubActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class upiPayment extends AppCompatActivity {
Button paid,fail;
EditText amount;
    String clientphone;
    int clientdue;
    String uid;
     ImageView qrCodeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_payment2);
        paid=findViewById(R.id.paid2);
        fail=findViewById(R.id.fail);
        qrCodeImageView=findViewById(R.id.imageView2);
        amount=findViewById(R.id.editTextTextPersonName);
        if (getIntent() != null) {
            clientphone = getIntent().getStringExtra("clientPhone");
            clientdue = getIntent().getIntExtra("clientdue",0);
        }

        amount.setText(String.valueOf(clientdue));
        uid = FirebaseAuth.getInstance().getUid();
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        final String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        final FirebaseInitilization firebaseInitilization = new FirebaseInitilization();
        String paymentAmount = "100.00"; // Replace with your payment amount
        String upiAddress = "gauravraoj29@oksbi"; // Replace with your UPI address

        String paymentUrl = "upi://pay?pa=" + upiAddress + "&pn=Your%20Name&mc=123456&tid=1234567890&tr=XYZ123&tn=Payment%20Description&am=" + clientdue + "&cu=INR";

        Bitmap qrCodeBitmap = null;
        try {
            qrCodeBitmap = generateQRCode(paymentUrl, 300, 300);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        qrCodeImageView.setImageBitmap(qrCodeBitmap);


        fail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(upiPayment.this, "PAYMENT FAILED", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(upiPayment.this, MainActivity.class);
        startActivity(intent);

    }
});
paid.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (Integer.parseInt(amount.getText().toString()) <= clientdue) {
            firebaseInitilization.amountPaid.child(uid).child(clientphone).child(formattedDate).child("amount").setValue(Integer.parseInt(amount.getText().toString()));
            firebaseInitilization.amountPaid.child(uid).child(clientphone).child(formattedDate).child("date").setValue(formattedDate);
            firebaseInitilization.users.child(uid).child(clientphone).child("currentdue").setValue(clientdue - Integer.parseInt(amount.getText().toString()));
            Intent intent=new Intent(upiPayment.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(upiPayment.this, "Please check your input", Toast.LENGTH_SHORT).show();
        }
    }
});

    }

    private Bitmap generateQRCode(String data, int width, int height) throws WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
        int matrixWidth = bitMatrix.getWidth();
        int matrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[matrixWidth * matrixHeight];

        for (int y = 0; y < matrixHeight; y++) {
            int offset = y * matrixWidth;
            for (int x = 0; x < matrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(matrixWidth, matrixHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, matrixWidth, 0, 0, matrixWidth, matrixHeight);
        return bitmap;
    }
}


