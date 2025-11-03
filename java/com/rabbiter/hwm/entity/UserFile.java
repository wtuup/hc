package com.rabbiter.hwm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2024-03-15
 */
@TableName("user_file")
@ApiModel(value = "UserFile对象", description = "")
public class UserFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String dwc;

    private String type;
    private Long size;
    private String url;
    private String md5;
    private String title;

    private String content;

    @Override
    public String toString() {
        return "UserFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dwc='" + dwc + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", md5='" + md5 + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDwc() {
        return dwc;
    }

    public void setDwc(String dwc) {
        this.dwc = dwc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
