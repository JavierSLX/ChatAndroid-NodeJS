package com.morpheus.chatnodejs.Model;

public class Message
{
    private String nickname;
    private String message;

    public Message()
    {
    }

    public Message(String nickname, String message)
    {
        this.nickname = nickname;
        this.message = message;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return String.format("Nickname: %s, Mensaje: %s", getNickname(), getMessage());
    }
}
