package com.wizinno.jas.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/15.
 */
public class FileUtil {

    public static List<String> fileUpload(MultipartFile[] files, HttpServletRequest request,String path) throws IOException {
        List<String> lists = new ArrayList<>();
        if (files.length >0){
            for (MultipartFile file:files){
                String realName=null;
                String realPath=null;

                String type = null;// 文件类型
                String fileName = file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:" + fileName);
                type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
                if (type != null) {
                    // 判断文件类型是否为空
                    if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                        // 项目在容器中实际发布运行的根路径
//                    String realPath = request.getSession().getServletContext().getRealPath("/");
                        // 自定义的文件名称
                        // 设置存放图片文件的路径

                        realName=System.currentTimeMillis()+fileName;
                        File f = new File(path + realName);
                        //realPath = path +realName;
                        //获取真实路径
                        realPath=f.getAbsolutePath();

                        System.out.println("存放图片文件的路径:" + path);
                        // 转存文件到指定的路径
                        file.transferTo(new java.io.File(realPath));
                        System.out.println("文件成功上传到指定目录下");
                    } else {
                        System.out.println("不是我们想要的文件类型,请按要求重新上传");
                        return null;
                    }

                 }
                lists.add(realName);
            }
            return lists;
        }
            return null;
    }

    public static String fileUploads(MultipartFile  fileso, HttpServletRequest request,String path) throws IOException {
        if (null !=fileso){
                String realName=null;
                String realPath=null;
                String type = null;// 文件类型
                String fileName = fileso.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:" + fileName);
                type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
                if (type != null) {
                    // 判断文件类型是否为空
                    if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                        // 项目在容器中实际发布运行的根路径
//                    String realPath = request.getSession().getServletContext().getRealPath("/");
                        // 自定义的文件名称
                        // 设置存放图片文件的路径

                        realName=System.currentTimeMillis()+fileName;
                        File f = new File(path + realName);
                        //realPath = path +realName;
                        //获取真实路径
                        realPath=f.getAbsolutePath();

                        System.out.println("存放图片文件的路径:" + path);
                        // 转存文件到指定的路径
                        fileso.transferTo(new java.io.File(realPath));
                        System.out.println("文件成功上传到指定目录下");
                    } else {
                        System.out.println("不是我们想要的文件类型,请按要求重新上传");
                        return null;
                    }

                }
            return realName;
        }
        return null;
    }


/*        if (file != null) {// 判断上传的文件是否为空
            String realName=null;
            String realPath=null;

            String type = null;// 文件类型
            String fileName = file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:" + fileName);
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {
                // 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
//                    String realPath = request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    // 设置存放图片文件的路径

                    realName=System.currentTimeMillis()+fileName;
                    File f = new File(path + realName);
                    //realPath = path +realName;
                    //获取真实路径
                    realPath=f.getAbsolutePath();
                    System.out.println("存放图片文件的路径:" + path);
                    // 转存文件到指定的路径
                    file.transferTo(new java.io.File(realPath));
                    System.out.println("文件成功上传到指定目录下");
                } else {
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    return null;
                }
                return realName;
            } else {
                System.out.println("文件类型为空");
                return null;
            }
        } else {
            System.out.println("没有找到相对应的文件");
            return null;
        }


    }*/
}
