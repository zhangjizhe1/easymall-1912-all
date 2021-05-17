package cn.tedu.pic.service;

import com.jt.common.utils.UploadUtil;
import com.jt.common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class PicService {
    public PicUploadResult picUpload(MultipartFile pic) {
        /*
            1.判断文件数据是否合法
                1.1引入第三方jar包,全套判断,木马,二进制格式
                1.2通过后缀名称判断是否是图片文件 jpg png gif等等
            2.生成一个目录的文件夹结构,存储路径和url地址路径公用字符串
                存储路径:D:/img/upload/3/d/5/e/d/2/h/1/uuid.jpg
                url地址:http://image.jt.com/upload/3/d/5/e/d/2/h/1/uuid.jpg
                为什么不在d:/img下直接存储图片?
                /upload/3/d/5/e/d/2/h/1/
            3.创建文件夹 d:/img//upload/3/d/5/e/d/2/h/1/ 重命名图片文件将二进制写入到这个地址
                d:/img//upload/3/d/5/e/d/2/h/1/uuid.jpg
            4.拼接url地址,封装到PicUploadResult里,返回
         */
        //准备一个PicUploadResult对象
        PicUploadResult result=new PicUploadResult();
        try{
            //拿到文件原名称,截取后缀.jpg判断是否合法
            //sdlajflasdjf.jpg
            String oName = pic.getOriginalFilename();
            String extName = oName.substring(oName.lastIndexOf("."));
            if(!extName.matches(".(png|jpg|gif)$")){
                //进入到if条件说明后缀不合法
                result.setError(1);
                return result;
            }
            //生成一个多级文件夹目录/upload/3/d/5/e/d/2/h/1/
            //功能 对源文件夹名称做hash字符串取值 23n29cn2 将每一个字符截取成一个目录
            // /upload/2/3/n/2/9/c/n/2/ 拼接上前缀字符串 upload
            String path = "/"+UploadUtil.getUploadPath(oName, "upload")+"/";
            //利用这个多级目录创建文件夹d:/img+path 没有就创建有则直接使用
            File dir=new File("d:/img"+path);
            if(!dir.exists()){
                //说明dir多级文件夹不存在,创建
                dir.mkdirs();
            }
            //生成一个唯一的重命名的图片名称,避免了上传同一个文件考虑覆盖的问题
            String newName= UUID.randomUUID().toString()+extName;//05e20c1a-0401-4c0a-82ab-6fb0f37db397.jpg
            //利用multipartFile的流输出到这个磁盘文件图片数据
            //生成了一个磁盘文件 d:/img/upload/2/3/n/2/9/c/n/2/05e20c1a-0401-4c0a-82ab-6fb0f37db397.jpg
            pic.transferTo(new File("d:/img"+path+newName));
            //InputStream inputStream = pic.getInputStream();
            //最后利用已有的一些变量拼接url地址放到result属性中返回
            String url="http://image.jt.com/"+path+newName;
            result.setUrl(url);
            return result;
        }catch(Exception e){
            //图片上传出现异常失败
            result.setError(1);
            return result;
        }
    }
}
