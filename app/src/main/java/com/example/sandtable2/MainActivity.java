package com.example.sandtable2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    WebSocketConnection wsc;
    private String TAG = "---------->";
    private String url = "ws://39.105.232.155:8099/productWebsocket/001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tittle = findViewById(R.id.titleBar);
        tittle.setText("登陆页面");

        Button btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogIn:
                connect();
                Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCancel:
                finish();
                break;
        }

    }

    private void connect() {
        try {
            wsc.connect(url,new WebSocketHandler(){
                @Override
                public void onOpen() {
                    Log.d(TAG,"onOpen");
                    showtext("连接成功");
                    wsc.sendTextMessage("Hello!");
//                     wsc.disconnect();
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG,"onClose reason=" + reason);
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG,"onTextMessage" + payload);
                    showtext(payload);
                }

                @Override
                public void onRawTextMessage(byte[] payload) {
                    Log.d(TAG,"onRawTextMessage size="
                            + payload.length);
                }


                @Override
                public void onBinaryMessage(byte[] payload) {
                    Log.d(TAG,"onBinaryMessage size="
                            + payload.length);
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    private void showtext(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
