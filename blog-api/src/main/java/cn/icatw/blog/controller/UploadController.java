package cn.icatw.blog.controller;

import cn.icatw.blog.service.OssService;
import cn.icatw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote 图片上传接口
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        ////原始文件名称 比如 aa.png
        //String originalFilename = file.getOriginalFilename();
        ////唯一的文件名称
        //String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //上传文件
        String url = ossService.upload(file);
        System.out.println(url);
        if (url != null) {
            return Result.success(url);
            //https://blog-icatwms.oss-cn-beijing.aliyuncs.com/2022/03-04/f76bb842ce76406c8b14d15969778f951606576891910.jpeg
            //https://blog-icatwms.https//oss-cn-beijing.aliyuncs.com/2022/03-04/b222e52483ec446ca694b6fbb029bc6c1606576891910.jpeg
        }
        return Result.fail(20001, "上传失败");
    }
}
