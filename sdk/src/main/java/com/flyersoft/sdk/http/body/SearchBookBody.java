package com.flyersoft.sdk.http.body;

import com.flyersoft.sdk.http.base.BaseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe: search图书请求体
 * Created by ${zheng.hu} on 2016/9/27.
 */
public class SearchBookBody extends BaseBody {

    private String bookId;
    private String keyword;
    private String categoryId;
    private String author;
    private String from;
    private String limit;

    public SearchBookBody(String bookId, String keyword, String categoryId, String author, int from, int limit) {
        this.bookId = bookId;
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.author = author;
        this.from = String.valueOf(from);
        this.limit = String.valueOf(limit);
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String ,String>(10);
        map.put("bookId",bookId);
        map.put("keyword",keyword);
        map.put("categoryId",categoryId);
        map.put("author",author);
        map.put("from",from);
        map.put("limit",limit);
        return map;
    }
}
