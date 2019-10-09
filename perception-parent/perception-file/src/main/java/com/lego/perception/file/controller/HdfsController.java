package com.lego.perception.file.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.perception.file.service.IHdfsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/16 15:35
 * @desc :
 */
@RestController
@RequestMapping("/hdfs/v1")
@Api(value = "HdfsController", description = "HDFS存储")
public class HdfsController {

    @Autowired
    private IHdfsService iHdfsService;


    @ApiOperation(value = "创建文件夹", httpMethod = "POST")
    @RequestMapping(value = "/mkdir", method = RequestMethod.POST)
    public RespVO mkdir(@RequestParam String path) {
        boolean result = iHdfsService.mkdir(path);
        if (result) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RespVO upload(@RequestParam String filePath,
                         @RequestParam String storeDir) {
        return iHdfsService.uploadFileToHdfs(filePath, storeDir);
    }


    @ApiOperation(value = "查询文件列表", httpMethod = "GET")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public RespVO<RespDataVO<Map<String, Object>>> list(@RequestParam String storeDir) {
        List<Map<String, Object>> result = iHdfsService.listFiles(storeDir, null);
        return RespVOBuilder.success(result);
    }


    @ApiOperation(value = "下载文件", httpMethod = "POST")
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public RespVO download(@RequestParam String storePath,
                           @RequestParam String savePath) {
        iHdfsService.downloadFileFromHdfs(storePath, savePath);
        return RespVOBuilder.success();
    }


    @ApiOperation(value = "multipartFile文件上传", httpMethod = "POST")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public RespVO<Map<String, String>> uploadFile(@RequestParam String storePath,
                                                  @RequestParam String savePath,
                                                  @RequestParam(value = "files") MultipartFile[] files) {
        Map<String,String> fileNamemap = new HashMap<>();
        if (files == null || files.length == 0) {
            return RespVOBuilder.failure("上传文件files不能为空");
        }
        Arrays.stream(files).forEach(f -> {
            String name = f.getOriginalFilename();
            String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            File file = new File(storePath);
            if (!file.exists()) {//目录不存在就创建
                file.mkdirs();
            }
            try {
                f.transferTo(new File(storePath + "/" + fileName + "." + subffix));//保存文件
                iHdfsService.uploadFileToHdfs(storePath + "/" + fileName + "." + subffix, savePath);
                fileNamemap.put(name,storePath + "/" + fileName + "." + subffix);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return RespVOBuilder.success(fileNamemap);
    }

}
