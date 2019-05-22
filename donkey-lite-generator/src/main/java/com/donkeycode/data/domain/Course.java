package com.donkeycode.data.domain;

import com.donkeycode.core.BaseEntity;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "course")
public class Course extends BaseEntity {
    /**
     * 课程id
     */
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 封面
     */
    @Column(name = "cover_img_url")
    private String coverImgUrl;

    /**
     * 课时
     */
    @Column(name = "class_hour")
    private String classHour;

    /**
     * 讲师
     */
    private String lecturer;

    /**
     * 直播地址
     */
    @Column(name = "live_url")
    private String liveUrl;

    /**
     * 解说地址
     */
    @Column(name = "commentary_url")
    private String commentaryUrl;

    /**
     * 讲解人
     */
    @Column(name = "commentary_user")
    private String commentaryUser;

    /**
     * 朗读人
     */
    @Column(name = "readaloud_user")
    private String readaloudUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}