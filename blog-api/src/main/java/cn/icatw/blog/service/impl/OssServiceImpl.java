package cn.icatw.blog.service.impl;

import cn.icatw.blog.service.OssService;
import cn.icatw.blog.utils.AliyunOssConstant;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
@Service
public class OssServiceImpl implements OssService {

    //上传头像，返回阿里云OSS的头像的url，具体实现
    @Override
    public String upload(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliyunOssConstant.END_POINT;
        String accessKeyId = AliyunOssConstant.KEY_ID;
        String accessKeySecret = AliyunOssConstant.KEY_SECRET;
        String bucketName = AliyunOssConstant.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件的输入流。
            //通过传过来的file的getInputStream方法获取inputStream
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            /**
             * 1.解决上传同一文件而被新上传的覆盖，使用uuid进行拼接文件名使文件名唯一
             * 2.解决上传文件分类问题，根据时间进行分类
             */
            // 将uuid 转为string 并且把-去除
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            /**
             * 使用的依赖是：
             * <dependency>
             *    <groupId>joda-time</groupId>
             *    <artifactId>joda-time</artifactId>
             *</dependency>
             * new DateTime().toString(),toString()传入日期规则
             * yyyy/MM-dd   形成 年为一个文件夹，月-日为一个文件夹
             */
            String date = new DateTime().toString("yyyy/MM-dd");

            //  2019/7-30/xdyguhn41601.jpg
            String filePath = date + "/" + fileName;

            /**
             * 第一个参数    bucket名字
             * 第二个参数    上传文件到阿里云OSS的文件路径和文件名称，filePath = path + fileName
             *              eg:/a/b/c.jpg
             * 第三个参数    是文件输入流
             */
            ossClient.putObject(bucketName, filePath, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            // https://six-teen.oss-cn-beijing.aliyuncs.com/favicon.png
            // 即https:// + bucketName +. +endpoint + / +文件名
            String url = "https://" + bucketName + "." + endpoint + "/" + filePath;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
