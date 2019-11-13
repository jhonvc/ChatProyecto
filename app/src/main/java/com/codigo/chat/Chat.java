package com.codigo.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Chat extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> datos;
    ChatAdapter chatAdapter;
    Button btnEnviar;
    EditText editMensaje;
    Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EnlazarViews();

        SocketesCodigo app = (SocketesCodigo)getApplication();
        socket = app.getMiSocket();
        socket.connect();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnviarMensaje();
            }
        });
        datos = new ArrayList<>();
        socket.on("new message", onMessage);
        chatAdapter = new ChatAdapter(this, R.layout.item_mensaje,datos);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void EnviarMensaje(){
        String mensaje = editMensaje.getText().toString();
        editMensaje.setText("");
        socket.emit("new message", mensaje);
    }

    void EnlazarViews(){
        btnEnviar = findViewById(R.id.btnMensaje);
        editMensaje = findViewById(R.id.editMensaje);
        recyclerView = findViewById(R.id.recyclerMensajes);
    }

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message = "";
                    String username="";
                    try {
                        username=data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    datos.add(message);
                    chatAdapter.notifyDataSetChanged();

                }
            });
        }
    };
}
