package com.telixia.springboot.controller;

import com.telixia.springboot.domain.ResponseResult;
import com.telixia.springboot.domain.UploadModel;
import com.telixia.springboot.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/upload")
public class UploadController extends BaseController {

    @Autowired
    private FileStorageService fileStorageService;
    
    // 3.1.1 Single file upload
    @PostMapping("")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

        ResponseResult rr = new ResponseResult();

        if (uploadfile.isEmpty()) {
            return ResponseEntity.ok().body("please select a file!");
        }

        try {
            String[] fileUrls = saveUploadedFiles(Arrays.asList(uploadfile));
            rr.setMessage(getApiUrl() + fileUrls[0]);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        rr.setstatusCode(200);
        return ResponseEntity.ok(rr);
    }

    // 3.1.2 Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {

        // Get file name
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(","));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return ResponseEntity.ok().body("please select a file!");
        }

        try {
            saveUploadedFiles(Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body("Successfully uploaded - " + uploadedFileName);
    }

    // 3.1.3 maps html form to a Model
    @PostMapping("/api/upload/multi/model")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {

        try {
            saveUploadedFiles(Arrays.asList(model.getFiles()));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body("Successfully uploaded!");
    }

    //save file
    private String[] saveUploadedFiles(List<MultipartFile> files) throws IOException {
        String[] fileUrls = new String[files.size()];
        int index = 0;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            
            fileUrls[index] = fileStorageService.storeFile(file);
            index++;
        }
        return fileUrls;
    }

}
