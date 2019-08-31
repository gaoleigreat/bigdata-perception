package com.lego.perception.file.controller;

import com.framework.common.consts.HttpConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.feign.DataFileClient;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.perception.file.model.UploadFile;
import com.lego.perception.file.service.IFdfsFileService;
import com.lego.perception.file.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@Api(value = "FileController", description = "规则和不规则文件上传")
@RequestMapping("/datefile/v1")
@Slf4j
public class DataFileController {

    @Autowired
    private IFdfsFileService fdfsFileService;
    private DataFileClient dataFileClient;

    @ApiOperation(value = "web文件上传", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/web/upload", method = RequestMethod.POST)
    public RespVO webUpload(HttpServletRequest req, Long projectId) {
        List<MultipartFile> fileList = new ArrayList<>();
        if (req instanceof MultipartHttpServletRequest) {
            fileList = ((MultipartHttpServletRequest) req).getFiles("file");
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            List<Map<String, Object>> returnList = new ArrayList<>();
            List<DataFile> dataFiles = new ArrayList<>();
            for (MultipartFile file : fileList) {
                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setContent(file.getBytes());
                if (!StringUtils.isEmpty(file.getOriginalFilename())) {
                    int pos = file.getOriginalFilename().lastIndexOf(".");
                    if (pos > -1 && pos + 1 < file.getOriginalFilename().length()) {
                        uploadFile.setExt(file.getOriginalFilename().substring(pos + 1));
                    }
                }
                RespVO<Map<String, Object>> upload = fdfsFileService.upload(uploadFile);
                if (1 == upload.getRetCode()) {
                    Map<String, Object> f = new HashMap<>(2);
                    f.put("fileName", file.getOriginalFilename());
                    f.put("url", upload.getInfo().get("data"));


                    DataFile dataFile = new DataFile();
                    //设置名称
                    dataFile.setName(file.getOriginalFilename());
                    //设置url
                    dataFile.setName(upload.getInfo().get("data").toString());
                    //设置projectId
                    dataFile.setProjectId(projectId);
                    if (!StringUtils.isEmpty(file.getName())) {
                        int pos = file.getOriginalFilename().lastIndexOf(".");
                        if (pos > -1 && pos + 1 < file.getOriginalFilename().length()) {
                            //设置类型
                            dataFile.setFileType(file.getOriginalFilename().substring(pos + 1));
                        }
                    }
                    if (file.getOriginalFilename().endsWith("dwg") || file.getOriginalFilename().endsWith("dxf")) {
                        File cadFile = FileUtil.downloadFile(upload.getInfo().get("data").toString(), "/opt/file/", file.getOriginalFilename());
                        FileUtil.changemeCadToPDf("/opt/file/", file.getOriginalFilename(), "/opt/file/pdf/", file.getOriginalFilename() + ".pdf");
                        UploadFile cadUploadFile = new UploadFile();
                        cadUploadFile.setFileName(cadFile.getName());
                        cadUploadFile.setContent(FileUtil.fileToBytes(cadFile));
                        if (!StringUtils.isEmpty(file.getName())) {
                            int pos = cadFile.getName().lastIndexOf(".");
                            if (pos > -1 && pos + 1 < cadFile.getName().length()) {
                                cadUploadFile.setExt(cadFile.getName().substring(pos + 1));
                            }
                        }
                        RespVO<Map<String, Object>> cadUpload = fdfsFileService.upload(cadUploadFile);
                        if (1 == cadUpload.getRetCode()) {
                            dataFile.setPreviewUrl(cadUpload.getInfo().get("data").toString());
                        }
                    }else{
                        dataFile.setPreviewUrl("");
                    }
                    Long userId = Long.parseLong(req.getHeader(HttpConsts.USER_ID));
                    dataFile.setCreatedBy(userId);
                    dataFile.setLastUpdatedBy(userId);
                    dataFile.setCreationDate(new Date());
                    dataFile.setLastUpdateDate(new Date());
                    dataFile.setDeleteFlag(2);
                    returnList.add(f);
                    dataFiles.add(dataFile);
                }
            }

            resultMap.put("datas", returnList);
            return dataFileClient.insertList(dataFiles);

        } catch (IOException e) {
            log.error("upload file error", e);
        }
        if (!resultMap.isEmpty()) {
            return RespVOBuilder.success(resultMap);
        }
        return RespVOBuilder.failure("参数错误");
    }


    //将pwg文件转化为pdf文件


}
