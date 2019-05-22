package com.donkeycode.boot.data.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.donkeycode.core.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yanjun.xue
 * @since 2019年5月12日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssFile extends BaseEntity {

    public static final String UPLOADING = "uploading";
    public static final String UPLOADED = "uploaded";
    public static final String UPLOAD = "upload";
    public static final String FAILED = "failed";
    public static final String DELETING = "deleting";

    private String fileId;
    private String name;
    @JsonIgnore
    private String ossKey;
    private String fileType;
    @JsonIgnore
    private String fileMD5;
    private Long fileSize;
    @JsonIgnore
    private String meta;
    @JsonIgnore
    private String status;
    @JsonIgnore
    private String localPath;
    private String cdnPath;
    private Date createTime;
}
