package com.cms.template.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.cms.template.common.kaptcha.KaptchaConfig;
import com.cms.template.common.result.CodeMsg;
import com.cms.template.common.result.Result;
import com.cms.template.common.util.AliUtil;
import com.cms.template.common.util.FileUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("//common")
public class CommonController {
    @Value("${ali.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${ali.sms.accessSecret}")
    private String accessKeySecret;
    @Value("${ali.oss.endpoint}")
    private String endpoint;
    @Value("${ali.oss.bucketName}")
    private String bucketName;
    @Value("${ali.oss.bucketUrl}")
    private String bucketUrl;

    @Value("${ali.sms.accessKeyId}")
    private String serveAccessKeyId;
    @Value("${ali.sms.accessSecret}")
    private String serveAccessSecret;


    @RequestMapping(value = "/code/verify", method = RequestMethod.GET)
    public Result getVerifyCode() {
        DefaultKaptcha kaptcha = KaptchaConfig.getDefaultKaptcha();
        String kaptchaText = kaptcha.createText();
        BufferedImage kaptchaImage = kaptcha.createImage(kaptchaText);
        String kaptchaBase64 = FileUtil.BufferedImageToBase64(kaptchaImage);
        String code_id = IdUtil.simpleUUID();
        Map<String, Object> map = new HashMap<>();
        map.put("codeId", code_id);
        map.put("code", kaptchaText);
        map.put("codeImg", kaptchaBase64);

        return Result.success(map);
    }


    @RequestMapping(value = "/ali/oss/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String objectName = "";
        assert fileName != null;
        if (fileName.length() > 0 && fileName.lastIndexOf(".") > -1) {
            objectName = DateUtil.format(new Date(), "yyyy/MM/dd/") + IdUtil.simpleUUID() + fileName.substring(fileName.lastIndexOf("."));
        } else {
            return Result.error(CodeMsg.ALI_OSS_UPLOAD_ERROR);
        }
        try {
            AliUtil.upload(AliUtil.getOssClient(endpoint, accessKeyId, accessKeySecret), bucketName, objectName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(bucketUrl + "/" + objectName);
    }
}
