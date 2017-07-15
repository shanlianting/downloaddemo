package net.bwei.shan.download.model;

/**
 * Created by shanlianting on 2017/7/12.
 */

public class ModelItem {
    private long id;
    private String title;
    private boolean  isCheck;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
