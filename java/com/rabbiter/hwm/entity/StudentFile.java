package com.rabbiter.hwm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2024-03-17
 */
@TableName("student_file")
@ApiModel(value = "StudentFile对象", description = "")
public class StudentFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生姓名")
    private String student;

    @TableField("student_id")
    @ApiModelProperty("学生id")
    private Integer studentId;

    @ApiModelProperty("老师姓名")
    private String dwc;

    @ApiModelProperty("作业id")
    @TableField("user_file_id")
    private Integer userFileId;

    @ApiModelProperty("作业标题")
    private String title;

    @ApiModelProperty("作答内容")
    private String an1;

    @ApiModelProperty("0未审核，1通过，2未通过")
    private Integer flag;

    @ApiModelProperty("分数")
    private Integer score;

    @ApiModelProperty("文件链接")
    private String url;

    @ApiModelProperty("md5")
    private String md5;

    @ApiModelProperty("文件名称")
    private String name;
    @ApiModelProperty("文件内容")
    private String content;

    public StudentFile() {
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getDwc() {
        return dwc;
    }

    public void setDwc(String dwc) {
        this.dwc = dwc;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAn1() {
        return an1;
    }

    public void setAn1(String an1) {
        this.an1 = an1;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getUserFileId() {
        return userFileId;
    }

    public void setUserFileId(Integer userFileId) {
        this.userFileId = userFileId;
    }

    public StudentFile(Integer id, String student, Integer studentId, String dwc, Integer userFileId, String title, String an1, Integer flag, Integer score, String url, String md5, String name, String content) {
        this.id = id;
        this.student = student;
        this.studentId = studentId;
        this.dwc = dwc;
        this.userFileId = userFileId;
        this.title = title;
        this.an1 = an1;
        this.flag = flag;
        this.score = score;
        this.url = url;
        this.md5 = md5;
        this.name = name;
        this.content = content;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "StudentFile{" +
                "id=" + id +
                ", student='" + student + '\'' +
                ", dwc='" + dwc + '\'' +
                ", title='" + title + '\'' +
                ", an1='" + an1 + '\'' +
                ", flag=" + flag +
                ", url='" + url + '\'' +
                ", md5='" + md5 + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
