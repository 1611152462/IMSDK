package com.ecareyun.im.model.bean.message;


public class TextMessage extends BaseMessage {

    @Override
    public int getbType() {
        return MSG_TYPE_TEXT;
    }

    private TextExt ext;

    public TextExt getExt() {
        return ext;
    }

    public void setExt(TextExt ext) {
        this.ext = ext;
    }

}
