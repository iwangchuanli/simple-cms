package com.cms.template.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class AliUtil {
    private static final Logger logger = LoggerFactory.getLogger(AliUtil.class);


    public static void sendSms(String serveAccessKeyId, String serveAccessSecret,
                               String phoneNumber,
                               String signName, String templateCode, String templateParam) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", serveAccessKeyId, serveAccessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        //短信API产品域名（接口地址固定，无需修改）
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        CommonResponse response = new CommonResponse();
        try {
            response = client.getCommonResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String data = response.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        String code = jsonObject.getString("Code");
        if (!"OK".equals(code)) {
            logger.info("发送短信错误：" + data);
        }
    }

    public static OSS getOssClient(String endpoint, String accessKeyId, String accessKeySecret) {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    public static void upload(OSS ossClient, String bucketName, String objectName, String localFilePath) {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFilePath));
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
    }

    public static void upload(OSS ossClient, String bucketName, String objectName, byte[] bytes) {
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        ossClient.shutdown();
    }

    public static void upload(OSS ossClient, String bucketName, String objectName, InputStream inputStream) {
        ossClient.putObject(bucketName, objectName, inputStream);
        ossClient.shutdown();
    }

    public static void download(OSS ossClient, String bucketName, String objectName) {
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                System.out.println("\n" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ossClient.shutdown();
    }

    public static void getFileList(OSS ossClient, String bucketName) {
        final int maxKeys = 200;
        String nextMarker = null;
        ObjectListing objectListing;
        do {
            objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName).withMarker(nextMarker).withMaxKeys(maxKeys));
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey());
            }
            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());
        ossClient.shutdown();
    }

    public static void deleteFile(OSS ossClient, String bucketName, String objectName) {
        //  删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();

    }
}
