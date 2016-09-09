package com.jdjt.exchange.model;

import com.android.pc.ioc.db.annotation.Id;
import com.android.pc.ioc.db.annotation.NotNull;

import java.util.List;
import java.util.Map;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:TagDao
 * @Package com.jdjt.exchange.model
 * @Date 2016/9/12 15:47
 */
public class TagDao {
    @Id
    private int id;
    //标签组重要标签
    @NotNull
    private String tag0;
    @NotNull
    private String tag1;
    @NotNull
    private String tag2;
    @NotNull
    private String tag3;

    List<Map<String, String>> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag0() {
        return tag0;
    }

    public void setTag0(String tag0) {
        this.tag0 = tag0;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public List<Map<String, String>> getTags() {
        return tags;
    }

    public void setTags(List<Map<String, String>> tags) {
        this.tags = tags;
    }
}
