package IntegracionBackFront.backfront.Services.ServiceImage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

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

        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));

        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder) throws IOException{
        validateImage(file);
        String originalFileName = file.getOriginalFilename();
        String extension = file.getOriginalFilename().substring(originalFileName.lastIndexOf(".")).toLowerCase();
        String uniqueFileName = "img" + UUID.randomUUID() + extension;

        Map<String, Object> opctions = ObjectUtils.asMap(
                "folder", folder,
                "public_id", uniqueFileName, //Nombre único para el archivo
                "use_filename", false,       //No usar el nombre original
                "unique_filename", false,    //No generar nombre único (Ya lo hacemos de manera manual)
                "overwrite", false,          //No sobreescribir archivos existentes
                "resource_type", "auto",
                "quality", "auto:good"
        );

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), opctions);
        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        //Verificar que el archivo está vacío
        if (file == null || file.isEmpty()){ throw new IllegalArgumentException("El archivo está vacío."); }

        //Verificamos si el tamaño excede el límite
        if (file.getSize() > MAX_FILE_SIZE){ throw new IllegalArgumentException("El archivo no debe ser mayor de 5MB"); }

        //Verificamos la extención del archivo
        String originalFileName =  file.getOriginalFilename();
        if (originalFileName == null) { throw new IllegalArgumentException("El nombre del archivo es inválido"); }

        String extension = file.getOriginalFilename().substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){ throw new IllegalArgumentException("Solo se periten archivos .jpg, .jpeg y .png"); }
        if(!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe ser una imágen válida");
    }
}
