package com.app.turingrobot.entity;

/**
 * Created by Alpha on 2016/3/26 22:56.
 */
public class CoreEntity {
    protected int code;
    protected String text;
    //是否是来信
    protected boolean isTarget = true;

    protected long time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
