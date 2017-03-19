package com.wyf.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wyf.aspect.LogAspect;
import com.wyf.util.HeadlineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by w7397 on 2017/3/19.
 */
@Service
public class QiniuService {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);


    //构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Zone.zone0());
    UploadManager uploadManager = new UploadManager(cfg);

    //生成上传凭证，准备上传
    String accessKey = "Ut_KOLSv3CgtS75TE7uO2GPDffWMczNdUgeDj_89";
    String secretKey = "Dh1ZLVO-RsEw8MsXHh6JrTL9pmGBFiJvVPoSRUwG";
    //上传空间
    String bucket = "wyfimage";

    //密钥配置
    Auth auth = Auth.create(accessKey, secretKey);
    String upToken = auth.uploadToken(bucket);

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int doPos = file.getOriginalFilename().lastIndexOf(".");
            if (doPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(doPos + 1).toLowerCase();
            if (!HeadlineUtil.isFillAllowed(fileExt)) {
                return null;
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传
            Response response = uploadManager.put(file.getBytes(), fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
            if (response.isOK() && response.isJson()) {
                return HeadlineUtil.QINIU_DOMAIN_PREFIX + putRet.key;
            } else {
                logger.error("七牛异常:" + response.bodyString());
                return null;
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
                return null;
            } catch (QiniuException ex2) {
                //ignore
                logger.error("七牛异常" + ex.getMessage());
                return null;
            }
        }
    }

}
