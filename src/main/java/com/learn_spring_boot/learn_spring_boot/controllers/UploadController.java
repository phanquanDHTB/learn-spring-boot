package com.learn_spring_boot.learn_spring_boot.controllers;

import com.learn_spring_boot.learn_spring_boot.util.DateUtil;
import com.learn_spring_boot.learn_spring_boot.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/upload")
@RequiredArgsConstructor
public class UploadController {
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    private final Path staticPath = Paths.get("static");
    private final Path imagePath = Paths.get("images");
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@ModelAttribute("files") List<MultipartFile> files){
        try {
            List<String> img = new ArrayList<>();
            if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
            }
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                String filename = DateUtil.dateUpFile() + file.getOriginalFilename();
                Path path = CURRENT_FOLDER.resolve(staticPath)
                        .resolve(imagePath).resolve(filename);
                try (OutputStream os = Files.newOutputStream(path)) {
                    os.write(file.getBytes());
                }
                img.add("/" + imagePath + "/" + filename);
            }
            return ResponseEntity.ok(ResponseEntity.ok(img));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("static/images/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("static/images/23092023093146ÂNH.jpg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
