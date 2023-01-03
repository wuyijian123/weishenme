package com.bezkoder.spring.files.uploadmultiple.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
//uploads文件夹作为上传文件的根部
  private final Path root = Paths.get("uploads");

  @Override
  public void init() {
    try {
      //创建根目录
      Files.createDirectory(root);
    } catch (IOException e) {
      throw new RuntimeException("无法初始化上传文件夹!");
    }
  }

  @Override
  public void save(MultipartFile file) {
    try {
      //将文件存储到root+文件名
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
    } catch (Exception e) {
      throw new RuntimeException("无法存储该文件. 错误信息: " + e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      //把指定文件形成可供下载的资源
      //获取path
      Path file = root.resolve(filename);
      //转换为uri--资源
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        //如果资源存在并可用，返回该资源
        return resource;
      } else {
        throw new RuntimeException("无法读取该文件");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    //不知道为啥删除之后就无法加载文件
    Path src=Paths.get("D:/idea/weishenme/spring-boot-upload-multiple-files/uploads/");
    try {
      FileSystemUtils.deleteRecursively(src);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      //遍历所有的文件，并返回Stream<Path>
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("不能加载文件!");
    }
  }
}
