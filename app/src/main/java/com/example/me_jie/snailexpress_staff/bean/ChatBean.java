package com.example.me_jie.snailexpress_staff.bean;

/**
 * Created by me-jie on 2017/4/8.
 */

public class ChatBean {
    public static final int TYPE_RECEIVE = 0;
    public static final int TYPE_SEND = 1;
    private String content;
    private int type;

    public ChatBean(String content, int type)
    {
        this.content = content;
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public int getType()
    {
        return type;
    }
}
