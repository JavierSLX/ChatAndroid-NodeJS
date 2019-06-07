package com.morpheus.chatnodejs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    private EditText edtNickname;
    public static final String NICKNAME = "usernickname";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Llama las instancias de las vistas
        Button btn = findViewById(R.id.enterchat);
        edtNickname = findViewById(R.id.nickname);

        //Evento al presionar el boton
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Si hay texto en la caja
                if(!edtNickname.getText().toString().isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this, ChatBoxActivity.class);

                    //Manda el nickname a la siguiente actividad
                    intent.putExtra(NICKNAME, edtNickname.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
    }
}
