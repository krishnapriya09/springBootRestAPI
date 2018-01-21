package controller;

import java.util.logging.Logger;


/**
 * Created by KrishnaPriya on 1/21/2018.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FileMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** A rest controller provides api to upload single/multiple files as post request and get the uploaded files as an get request*/
@RestController
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(UploadController.class);


    private static String UPLOAD_FOLDER = "C:/uploads/file/upload/";


    @Autowired
    private FileUploadRepository fileUploadData;// Can perform CRUD operations on the file meta data in DB.

    @RequestMapping(value = "/api/fileUpload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadFile,
                                        final HttpServletRequest request) {

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<String>("please select a file to upload!", HttpStatus.OK);
        }

        try {
            saveUploadedFiles(Arrays.asList(uploadFile));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successfully file uploaded - " + uploadfile.getOriginalFilename(),
                new HttpHeaders(), HttpStatus.OK);

    }

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.GET)
    public List<FileMetaData> fileMetaData() {
        List<FileMetaData> fileUploadedMetaData = fileMetaData.findAll();
        return fileUploadedMetaData;
    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            saveMetaData(file);

        }

    }

    private void saveMetaData(MultipartFile file) throws IOException {
        FileMetaData metaData = new FileMetaData();
        metaData.setName(file.getOriginalFilename());
        metaData.setContentType(file.getContentType());
        fileUploadMetaData.save(metaData);
    }
}