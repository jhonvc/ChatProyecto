package com.codigo.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    Button btn_login;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EnlazarViews();

        SocketesCodigo app = (SocketesCodigo)getApplication();
        socket = app.getMiSocket();
        socket.connect();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socket.emit("add user", usuario.getText().toString() );
                Intent i = new Intent(MainActivity.this, Chat.class);
                i.putExtra("usuario", usuario.getText().toString());
                startActivity(i);

            }
        });
        socket.on("login", onLogin);
    }

    void EnlazarViews(){
        usuario = findViewById(R.id.usuario);
        btn_login = findViewById(R.id.btn_login);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int cantidad = 0;
                    try {
                        cantidad = data.getInt("numUsers");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, cantidad+"", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}
