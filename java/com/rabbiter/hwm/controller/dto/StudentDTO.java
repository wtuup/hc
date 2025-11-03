package com.rabbiter.hwm.controller.dto;


/**
 * @author 
 * @date : 2024/3/20
 */
public class StudentDTO {
    private Integer id;
    private String student;
    private String dwc;
    private Integer userFileId;
    private String title;
    private String an1;
    private String content;
    private Integer flag;
    private Integer score;

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

    public StudentDTO(Integer id, String student, String dwc, Integer userFileId, String title, String an1, String content, Integer flag, Integer score) {
        this.id = id;
        this.student = student;
        this.dwc = dwc;
        this.userFileId = userFileId;
        this.title = title;
        this.an1 = an1;
        this.content = content;
        this.flag = flag;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public StudentDTO() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", student='" + student + '\'' +
                ", dwc='" + dwc + '\'' +
                ", title='" + title + '\'' +
                ", an1='" + an1 + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
