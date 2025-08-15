package IntegracionBackFront.backfront.Services.ServiceImage;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ServiceImage {
    //1.Definir el tamaño de las imágenes en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2.Definir extenciones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    private final Cloudinary cloudinary;

    public ServiceImage(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Multipart sirve para manejar archivo
    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        //Verificar que el archivo está vacío
        if (file == null || file.isEmpty()){
            throw new IllegalArgumentException("El archivo está vacío.");
        }

        //Verificamos si el tamaño excede el límite
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El archivo no debe ser mayor de 5MB");
        }

        //Verificamos la extención del archivo
        if (file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("El nombre del archivo es inválido");
        }

        String extension = file.getOriginalFilename().substring();
    }
}
