package com.ecareyun.im.model.bean;

import java.io.File;

public class UpLoadImgInfo {
    private File file;
    private String fileName;

    public UpLoadImgInfo() {
    }

    public UpLoadImgInfo(File file, String fileName) {
        this.file = file;
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "UpLoadImgInfo{" +
                "file=" + file +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
