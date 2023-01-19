package cn.edu.lzzy.mypractices.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author lzzy_gxy on 2021/11/11.
 * Description:
 */
public class FileUtils {
    public static final String UPLOAD_PARAM_URL = "urls";
    public static final String UPLOAD_PARAM_MESSAGE = "msg";
    public static final String UPLOAD_PARAM_STATUS = "status";

    /**
     * 使用随机名称上传文件到指定目录
     *
     * @param file 文件
     * @param dir  上传至的目录
     * @return 结果打包为JSONObject
     */
    public static JSONObject uploadWithRandomName(MultipartFile file, String dir) {
        JSONObject result = new JSONObject();
        try {
            String originalName = file.getOriginalFilename();
            String suffix = Objects.requireNonNull(originalName).substring(originalName.lastIndexOf("."));
            String newName = UUID.randomUUID().toString() + suffix;
            File f = new File(dir + newName);
            file.transferTo(f);
            result.put(UPLOAD_PARAM_URL, newName);
            result.put(UPLOAD_PARAM_MESSAGE, "文件上传成功！");
            result.put(UPLOAD_PARAM_STATUS, true);
        } catch (IOException | JSONException e) {
            result.put(UPLOAD_PARAM_URL, "");
            result.put(UPLOAD_PARAM_MESSAGE, "上传失败：" + e.getMessage());
            result.put(UPLOAD_PARAM_STATUS, false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 使用源文件名称上传文件到指定目录
     *
     * @param file 文件
     * @param dir  上传至的目录
     * @return 结果打包为JSONObject
     */
    public static JSONObject uploadWithOriginalName(MultipartFile file, String dir) {
        JSONObject result = new JSONObject();
        try {
            String originalName = file.getOriginalFilename();
            // Check for Unix-style path
            int unixSep = Objects.requireNonNull(originalName).lastIndexOf('/');
            // Check for Windows-style path
            int winSep = originalName.lastIndexOf('\\');
            // Cut off at latest possible point
            int pos = (Math.max(winSep, unixSep));
            if (pos != -1) {
                // Any sort of path separator found...
                originalName = originalName.substring(pos + 1);
            }
            File f = new File(dir + originalName);
            file.transferTo(f);
            result.put(UPLOAD_PARAM_URL, originalName);
            result.put(UPLOAD_PARAM_MESSAGE, "文件上传成功！");
            result.put(UPLOAD_PARAM_STATUS, true);
        } catch (IOException | JSONException e) {
            result.put(UPLOAD_PARAM_URL, "");
            result.put(UPLOAD_PARAM_MESSAGE, "上传失败：" + e.getMessage());
            result.put(UPLOAD_PARAM_STATUS, false);
            e.printStackTrace();
        }
        return result;
    }
}
