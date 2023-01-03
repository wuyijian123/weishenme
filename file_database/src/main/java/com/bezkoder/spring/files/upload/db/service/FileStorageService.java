package com.bezkoder.spring.files.upload.db.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.upload.db.model.FileDB;
import com.bezkoder.spring.files.upload.db.repository.FileDBRepository;

@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;
    //通过调用FileDBRepository存储文件
  public FileDB store(MultipartFile file) throws IOException {
    //获得文件名
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    //通过FileDB的构造函数创建一个FileDB的实例
    //file.getContentType()获取文件类型--MultipartFile实例方法
    //file.getBytes()获取文件的二进制数组--MultipartFile实例方法
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

    //将file存储到数据库，并返回一个FileDB实例
    return fileDBRepository.save(FileDB);
  }
 //获取文件，根据id查找指定的文件
  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }

  //获取所有文件，查找数据库来获取所有
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
}
