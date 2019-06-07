package com.morpheus.chatnodejs.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.morpheus.chatnodejs.Model.Message;
import com.morpheus.chatnodejs.R;

import java.util.List;

public class ChatAdapter extends BaseAdapter
{
    private List<Message> lista;

    public ChatAdapter(List<Message> lista)
    {
        this.lista = lista;
    }

    @Override
    public int getCount()
    {
        return lista.size();
    }

    @Override
    public Message getItem(int i)
    {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View vista = view;
        if(vista == null)
            vista = inflater.inflate(R.layout.item, viewGroup, false);

        //Asigna los valores
        ((TextView)(vista.findViewById(R.id.txtNickname))).setText(getItem(i).getNickname());
        ((TextView)(vista.findViewById(R.id.txtMessage))).setText(getItem(i).getMessage());

        return vista;
    }
}
