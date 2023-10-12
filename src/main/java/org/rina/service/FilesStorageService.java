package org.rina.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FilesStorageService {
    private final Path rootBlog = Paths.get("C:\\Users\\yseuk\\git\\MPAC\\TFEFrontEnd\\src\\assets\\files");
    private final Path root= Paths.get("fichier/files");

    public String fileNameFormatter(String name,String extension) {
        String pattern = "yyyyMMddHHmmssSSS";
        String dateToString = new SimpleDateFormat(pattern, new Locale("fr", "FR")).format(new Date());
        return name + "_" + dateToString + extension;
    }

    public String getExtension(String fileName){
        int lastIndexOfDot = fileName!=null? fileName.lastIndexOf("."):-1;
        if (lastIndexOfDot == -1) {
            return "";
        }
        return fileName.substring(lastIndexOfDot);
    }

    public String saveFileForBlog(MultipartFile file){
        try {
            String filename = fileNameFormatter("image", getExtension(file.getOriginalFilename()));
            String path = "assets/files/"+filename;
            Files.copy(file.getInputStream(), this.rootBlog.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public String saveFile(MultipartFile file){
        try {
            String filename = fileNameFormatter("file", getExtension(file.getOriginalFilename()));
            String path = "fichier/files/"+filename;
            Files.copy(file.getInputStream(), this.root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public void deleteFile(String url) {
        try {
            Path filePath = Paths.get(url);
            Files.delete(filePath);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    public Resource loadFile(String url) {
        try {
            Path filePath = Paths.get(url);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
