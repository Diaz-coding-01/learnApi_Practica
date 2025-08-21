package IntegracionBackFront.backfront.Controller.ControllerImage;

import IntegracionBackFront.backfront.Services.ServiceImage.ServiceImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class ControllerImage {
    @Autowired
    private final ServiceImage serviceImage;

    public ControllerImage(ServiceImage serviceImage) {
        this.serviceImage = serviceImage;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file){
        try{
            String imageUrl = serviceImage.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida correctamente",
                    "url", imageUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }

    @PostMapping("/uploadToFolder")
    public ResponseEntity<?> uploadImageToFolder(
            @RequestParam("image") MultipartFile file,
            @RequestParam String folder
    ){
        try {
            String imageUrl = serviceImage.uploadImage(file, folder);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida correctamente",
                    "url", imageUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }
}
