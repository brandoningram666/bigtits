package ball.ingram.demo.provider;

import ball.ingram.demo.exception.CustomizeErrorCode;
import ball.ingram.demo.exception.CustomizeException;
import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;
    @Value("${ucloud.ufile.bucketName}")
    private String bucketName;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.proxySuffix}")
    private String proxySuffix;
    @Value("${ucloud.ufile.expiresDuration}")
    private Integer expiresDuration;


    public String upload(InputStream inputStream,String mimeType,String fileName){

        String[] filePaths = fileName.split("\\.");
        String generaotorName;
        if(filePaths.length > 1){
            generaotorName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        }else{
            throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
        }

            try {
                ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
                ObjectConfig config = new ObjectConfig(region, proxySuffix);
                PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                        .putObject(inputStream, mimeType)
                        .nameAs(generaotorName)
                        .toBucket(bucketName)
                        /**
                         * 是否上传校验MD5, Default = true
                         */
                        //  .withVerifyMd5(false)
                        /**
                         * 指定progress callback的间隔, Default = 每秒回调
                         */
                        //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                        /**
                         * 配置进度监听
                         */
                        .setOnProgressListener((bytesWritten, contentLength) -> {

                        })
                        .execute();
                        if(response != null && response.getRetCode() == 0){
                            String url = UfileClient.object(objectAuthorization, config)
                                    .getDownloadUrlFromPrivateBucket(generaotorName, bucketName, expiresDuration)
                                    /**
                                     * 使用Content-Disposition: attachment，并且默认文件名为KeyName
                                     */
//                    .withAttachment()
                                    /**
                                     * 使用Content-Disposition: attachment，并且配置文件名
                                     */
//                    .withAttachment("filename")
                                    /**
                                     * 图片处理服务
                                     * https://docs.ucloud.cn/ufile/service/pic
                                     */
//                    .withIopCmd("iopcmd=rotate&degree=90")
                                    .createUrl();
                                    return url;
                        }else{
                            throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
                        }
            } catch (UfileClientException e) {
                e.printStackTrace();
                throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
            } catch (UfileServerException e) {
                e.printStackTrace();
                throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
            }




    }
}
