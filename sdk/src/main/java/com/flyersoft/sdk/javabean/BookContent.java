package com.flyersoft.sdk.javabean;

/**
 * 正文内容
 * Created by 37399 on 2016/12/13.
 */
public class BookContent {

    private int amount;
    private String chapterNo;
    private String chapterTitle;
    private boolean compress;
    private String content;
    private String contentType;
    private int redBeans;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getRedBeans() {
        return redBeans;
    }

    public void setRedBeans(int redBeans) {
        this.redBeans = redBeans;
    }
}
