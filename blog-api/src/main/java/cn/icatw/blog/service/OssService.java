package cn.icatw.blog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
public interface OssService {
    //上传头像，返回阿里云OSS的头像的url，具体实现
    String upload(MultipartFile file);
}
