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
     * �γ�id
     */
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    /**
     * ����
     */
    private String title;

    /**
     * ����
     */
    private String description;

    /**
     * ����
     */
    @Column(name = "cover_img_url")
    private String coverImgUrl;

    /**
     * ��ʱ
     */
    @Column(name = "class_hour")
    private String classHour;

    /**
     * ��ʦ
     */
    private String lecturer;

    /**
     * ֱ����ַ
     */
    @Column(name = "live_url")
    private String liveUrl;

    /**
     * ��˵��ַ
     */
    @Column(name = "commentary_url")
    private String commentaryUrl;

    /**
     * ������
     */
    @Column(name = "commentary_user")
    private String commentaryUser;

    /**
     * �ʶ���
     */
    @Column(name = "readaloud_user")
    private String readaloudUser;

    /**
     * ����ʱ��
     */
    @Column(name = "create_time")
    private Date createTime;
}