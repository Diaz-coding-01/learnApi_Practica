package IntegracionBackFront.backfront.Config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfigCloudinary {
    private String cloudName;
    private String key;
    private String secret;

    @Bean
    public Cloudinary cloudinary(){
        //Cargando las variables del env
        Dotenv objDotenv = Dotenv.load();

        //Map para almacenar la config
        Map<String, String> config = new HashMap<>();

        //Obteniendo las credenciales desde las variables de entorno
        config.put("cloud_name", objDotenv.get("CLOUDINARY_NAME"));
        config.put("api_key", objDotenv.get("CLOUDINARY_API_KEY"));
        config.put("api_secret", objDotenv.get("CLOUDINARY_API_SECRET"));

        return new Cloudinary(config);
    }
}
