package com.morpheus.chatnodejs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.morpheus.chatnodejs.Controller.ChatBoxAdapter;
import com.morpheus.chatnodejs.Model.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatBoxActivity extends AppCompatActivity
{
    private Socket socket;
    public String nickname;
    public ListView rcConversacion;
    public List<Message> messageList;
    public EditText edtMessage;
    public Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        //Instancia las vistas
        edtMessage = (EditText)findViewById(R.id.edtMessage);
        btSend = (Button)findViewById(R.id.btSend);
        rcConversacion = (ListView) findViewById(R.id.messageList);

        //RecyclerView
        messageList = new ArrayList<>();
        final ArrayAdapter<Message> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        rcConversacion.setAdapter(adapter);

        //Obtiene el nickname del usuario
        nickname = (String)getIntent().getExtras().getString(MainActivity.NICKNAME);

        //Conecta al socket del servidor
        try
        {
            socket = IO.socket("http://192.168.1.69:3000");
            socket.connect();

            //Emite el evento "join" para el uso del nickname
            socket.emit("join", nickname);
        }catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        //Evento del botón Send
        btSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Envia el mensaje por medio del socket
                if(!edtMessage.getText().toString().isEmpty())
                {
                    socket.emit("messagedetection", nickname, edtMessage.getText().toString().trim());
                    edtMessage.setText("");
                }
            }
        });

        //Implementa los listeners de los sockets
        //Cuando se une una persona nueva al chat
        socket.on("userjoinedthechat", new Emitter.Listener()
        {
            @Override
            public void call(final Object... args)
            {
                //Corre en el hilo princial
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String data = (String)args[0];

                        //Dispara un toast con la info informando que entro un usuario nuevo
                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Cuando un usuario se desconecta
        socket.on("userdisconnect", new Emitter.Listener()
        {
            @Override
            public void call(final Object... args)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String data = (String)args[0];
                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Cuando le llega los mensajes de los demás usuarios
        socket.on("message", new Emitter.Listener()
        {

            @Override
            public void call(final Object... args)
            {
                //Corre sobre el hilo principal
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        JSONObject data = (JSONObject)args[0];

                        try
                        {
                            String nickname = data.getString("senderNickname");
                            String message = data.getString("message");

                            //Crea una instancia de mensaje
                            Message messageTxt = new Message(nickname, message);

                            //Agrega el mensaje a la lista
                            messageList.add(messageTxt);

                            //Notifica al adaptador de que cambiará la listView
                            adapter.notifyDataSetChanged();

                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        socket.disconnect();
    }
}
