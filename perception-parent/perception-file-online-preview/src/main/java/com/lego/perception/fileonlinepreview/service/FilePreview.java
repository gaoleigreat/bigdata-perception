package com.lego.perception.fileonlinepreview.service;

import com.lego.perception.fileonlinepreview.model.FileAttribute;
import org.springframework.ui.Model;

/**
 * Created by kl on 2018/1/17.
 * Content :
 */
public interface FilePreview {
    String filePreviewHandle(String url, Model model, FileAttribute fileAttribute);
}
