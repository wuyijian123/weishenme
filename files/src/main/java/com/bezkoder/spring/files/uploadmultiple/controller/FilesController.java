package com.bezkoder.spring.files.uploadmultiple.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bezkoder.spring.files.uploadmultiple.message.ResponseMessage;
import com.bezkoder.spring.files.uploadmultiple.model.FileInfo;
import com.bezkoder.spring.files.uploadmultiple.service.FilesStorageService;

@Controller
@CrossOrigin("http://localhost:8081")
public class FilesController {

  public FilesController(FilesStorageService storageService) {
    this.storageService = storageService;
  }

  final FilesStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) {
   //响应消息，存储上传的文件名+文件上传成功
    String message = "";
    try {
      //存储所有文件的文件名
      List<String> fileNames = new ArrayList<>();
      //遍历所有上传文件
      Arrays.asList(files).stream().forEach(file -> {
        //存储到upload
        storageService.save(file);
        //提取文件名
        fileNames.add(file.getOriginalFilename());
      });
      //将文件名列表封装为消息
      message =  fileNames+"文件上传成功。 ";
      //封装返回
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "文件上传失败";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {

    List<FileInfo> fileInfos = storageService.loadAll()
            .map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    })
            .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
  @DeleteMapping("/files")
  public  ResponseEntity<ResponseMessage> deleteAll(){
    storageService.deleteAll();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
