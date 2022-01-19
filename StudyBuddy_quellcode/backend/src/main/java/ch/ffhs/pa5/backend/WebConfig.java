package ch.ffhs.pa5.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Hier wird die Konfiguration des Servlets vorgenommen um Cross-Origin Resource Sharing (CORS) zu ermöglichen
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Einstellungen zum CORS für die gesamte Applikation
     *
     * @param registry Zuordnung der CORS-Konfiguration
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080");

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET","POST","PATCH","PUT","DELETE");
    }

    /**
     * Wird verwendet, um den angegebenen URI in den tatsächlichen URI aufzulösen
     *
     * @return InternalResourceViewResolver
     */
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
