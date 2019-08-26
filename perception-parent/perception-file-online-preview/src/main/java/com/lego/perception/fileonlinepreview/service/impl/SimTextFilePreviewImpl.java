package com.lego.perception.fileonlinepreview.service.impl;

import com.lego.perception.fileonlinepreview.model.FileAttribute;
import com.lego.perception.fileonlinepreview.model.ReturnResponse;
import com.lego.perception.fileonlinepreview.service.FilePreview;
import com.lego.perception.fileonlinepreview.utils.FileUtils;
import com.lego.perception.fileonlinepreview.utils.SimTextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Created by kl on 2018/1/17.
 * Content :处理文本文件
 */
@Service
public class SimTextFilePreviewImpl implements FilePreview{

    @Autowired
    SimTextUtil simTextUtil;

    @Autowired
    FileUtils fileUtils;

    @Override
    public String filePreviewHandle(String url, Model model, FileAttribute fileAttribute){
        String decodedUrl=fileAttribute.getDecodedUrl();
        String fileName=fileAttribute.getName();
        ReturnResponse<String> response = simTextUtil.readSimText(decodedUrl, fileName);
        if (0 != response.getCode()) {
            model.addAttribute("msg", response.getMsg());
            model.addAttribute("fileType",fileAttribute.getSuffix());
            return "fileNotSupported";
        }
        model.addAttribute("ordinaryUrl", response.getMsg());
        return "txt";
    }

}
