package com.donkeycode.boot.data;

import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageFilterHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.donkeycode.boot.BaseTest;
import com.donkeycode.boot.data.domain.OssFile;

import java.util.Collections;
import java.util.Date;

public class OssFileServiceTest extends BaseTest {

    @Autowired
    OssFileService ossFileService;

    @Test
    public void insertRecode() {
        OssFile ossFile = new OssFile();
        ossFile.setFileType("image");
        ossFile.setOssKey("/adfasdf/");
        ossFile.setName("adfasdf");
        ossFile.setStatus(OssFile.FAILED);
        ossFile.setMeta("{}");
        ossFile.setFileSize(1000l);
        ossFile.setCreateTime(new Date());
        ossFile.setFileMD5("adasdfsafasdfsdfasdf");
        ossFile.setLocalPath("asdfasdfasdfasdfasdfsadf");
        ossFileService.insertRecode(ossFile);
    }

    @Test
    public void getPageList() {

        PageFilter pageProvider = PageFilterHelper.builder()
            .pageNum(3)
            .pageSize(3)
            .orders(Collections.singletonList("createTime:desc"))
            .params(Collections.singletonMap("fileName", "微信")).build();

        ossFileService.getPageList(pageProvider);
    }

    @Test
    public void updateStatus() {
        ossFileService.updateStatus("1", OssFile.DELETING);
    }

    @Test
    public void getOssFile() {
        ossFileService.getOssFile("1");
    }

    @Test
    public void deleteById() {
        ossFileService.deleteById("6");
    }
}