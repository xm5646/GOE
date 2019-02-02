package com.project.goe.projectgeodbserver.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeId;

    @Column(nullable = false)
    private boolean showStatus = false;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column
    private String title;

    @Column
    private String content;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public boolean isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(boolean showStatus) {
        this.showStatus = showStatus;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
