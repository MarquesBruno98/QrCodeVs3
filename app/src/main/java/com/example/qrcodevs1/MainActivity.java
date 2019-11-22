package com.example.qrcodevs1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    Button bttScan;
    Button bttGera;
    EditText  editText;
    ImageView imageQRCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final Activity activity = this;

        initComponents();
        clickBtns();
    }

    private void clickBtns(){
        final Activity activity = this;
        bttScan.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES );
                integrator.setPrompt("Aguardando QR-CODE");
                integrator.setCameraId(0);
                integrator.initiateScan();

            }
        });

        bttGera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarQRCODE();

            }
        });

    }


    //Gera QR-CODE
    private void gerarQRCODE(){
        String texto = editText.getText().toString();

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageQRCODE.setImageBitmap(bitmap);

        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    // Init dos elementos que tenho em tela
    private void initComponents(){
        bttScan = (Button) findViewById(R.id.bttScan);
        bttGera = (Button) findViewById(R.id.bttGera);
        editText = (EditText) findViewById(R.id.editText);
        imageQRCODE = (ImageView) findViewById(R.id.imageQRCODE);
    }

    // Nesse metodo tenho o retorno do que o Scanner leu
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents()!= null) {
                alert(result.getContents());

            }else{
                alert("Scan Cancelado");

            }

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void alert(String mensagem){
        Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_LONG).show();
    }

}
