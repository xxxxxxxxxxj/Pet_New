package com.haotang.deving.mvp.model.entity.res;

import android.net.Uri;

import java.io.File;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/20 13:50
 */
public class ImgInfo {
    private Uri uri;
    private String path;
    private File file;
    private File pressFile;

    public ImgInfo(Uri uri) {
        this.uri = uri;
    }

    public ImgInfo(Uri uri, String path, File file) {
        this.uri = uri;
        this.path = path;
        this.file = file;
    }

    public ImgInfo(Uri uri, String path, File file, File pressFile) {
        this.uri = uri;
        this.path = path;
        this.file = file;
        this.pressFile = pressFile;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getPressFile() {
        return pressFile;
    }

    public void setPressFile(File pressFile) {
        this.pressFile = pressFile;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
