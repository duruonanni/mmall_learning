package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        // 获取扩展名, +1 的作用是去掉 .
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 避免文件名重复,提供UUID做文件名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传路径:{},新文件名:{}", fileName, path, uploadFileName);
        // 创建文件夹路径目录
        File fileDir = new File(path);
        // 路径不存在,就创建它
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        // 创建空文件targetFile
        File targetFile = new File(path, uploadFileName);
        // 将文件传输到targetFile,暂存文件
        try {
            file.transferTo(targetFile);
            // 将targetFile文件上传到FTP服务器路径上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 上传完成后,删除path路径下的文件,避免应用过于臃肿
            targetFile.delete();
        } catch (IOException e) {
            logger.error("文件上传异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
