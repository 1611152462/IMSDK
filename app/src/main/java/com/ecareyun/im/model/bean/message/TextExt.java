package com.ecareyun.im.model.bean.message;

import java.util.Objects;

public class TextExt extends MessageExt {
    private String txt;

    public TextExt() { }

    public TextExt(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextExt textExt = (TextExt) o;
        return txt.equals(textExt.txt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(txt);
    }
}
