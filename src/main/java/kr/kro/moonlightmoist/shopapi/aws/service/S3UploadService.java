package kr.kro.moonlightmoist.shopapi.aws.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3UploadService {
    String uploadOneFile(MultipartFile file, String path); // 단일 파일 업로드
//    List<String> uploadMultipleFiles()
}
