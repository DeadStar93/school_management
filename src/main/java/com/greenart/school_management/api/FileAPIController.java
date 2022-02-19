package com.greenart.school_management.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


@RestController
public class FileAPIController {
    @Value("${spring.servlet.multipart.location}")
    String path;

    @PostMapping("/image/upload/{type}")
    public Map<String, Object> postImageUpload(@PathVariable String type, @RequestPart MultipartFile file) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(!type.equals("student") && !type.equals("teacher")) {
            resultMap.put("status", false);
            resultMap.put("message", "type:[student,teacher]");
            return resultMap;
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String[] splitFileName = fileName.split("\\.");
        String ext = splitFileName[splitFileName.length-1];

        if(
            !ext.equalsIgnoreCase("jpg") && 
            !ext.equalsIgnoreCase("jpeg") &&
            !ext.equalsIgnoreCase("png") &&
            !ext.equalsIgnoreCase("gif")
        ) {
            resultMap.put("status", false);
            resultMap.put("message", "프로필 사진은 jpg, jpeg, png, gif 형식만 등록 가능합니다.");
            return resultMap;
        }
        Path folderLocation = Paths.get(path+"/"+type);
        String saveFileName = Calendar.getInstance().getTimeInMillis()+"."+ext;
        Path target = folderLocation.resolve(saveFileName);

        try{
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        }   catch(IOException exception) {
            resultMap.put("status", false);
            resultMap.put("message", exception.getMessage());
            return resultMap;
        }

        resultMap.put("status", true);  
        resultMap.put("message", "파일 업로드 완료");
        resultMap.put("image_uri", saveFileName);

        return resultMap;
    }
    
    @GetMapping("/image/{type}/{uri}")
    public ResponseEntity<Resource> getImage(@PathVariable String type, @PathVariable String uri, HttpServletRequest request) throws Exception {
        Path folderLocation = Paths.get(path+"/"+type);
        Path filePath = folderLocation.resolve(uri);

        Resource r = new UrlResource(filePath.toUri());
        String contentType = request.getServletContext().getMimeType(r.getFile().getAbsolutePath());
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(
            HttpHeaders.CONTENT_DISPOSITION, "attatchment; filename*=\""+r.getFilename()+"\"").body(r);
    }
    
    @DeleteMapping("image/{type}/{uri}")
    public String deleteImage(@PathVariable String type, @PathVariable String uri) {
        if(uri.equals("default.jpg")) {return "default.jpg는 삭제할 수 없습니다.";}
            String filePath = path+"/"+type+"/"+uri;
            File deleteFile = new File(filePath);
            if(deleteFile.exists()) {
                deleteFile.delete();
        }
        else {
            return "파일이 존재하지 않습니다.";
        }
        return "이미지가 삭제되었습니다.";
    }
}
