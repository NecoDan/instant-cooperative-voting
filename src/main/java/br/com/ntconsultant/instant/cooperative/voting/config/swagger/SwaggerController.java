package br.com.ntconsultant.instant.cooperative.voting.config.swagger;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.info.BuildProperties;
//import org.springframework.core.io.Resource;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///**
// * @author Daniel Santos
// */
//@RestController
//@ApiIgnore
public class SwaggerController {

//    private final Optional<BuildProperties> buildProperties;
//
//    @Value("classpath:templates/home.html")
//    private Resource homeTemplate;
//
//    private String template;
//
//    public SwaggerController(Optional<BuildProperties> buildProperties) {
//        this.buildProperties = buildProperties;
//    }
//
//    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
//    @ResponseBody
//    public String index() throws IOException {
//        return template != null ? template : loadTemplate();
//    }
//
//    private String loadTemplate() throws IOException {
//        InputStream inputStream = homeTemplate.getInputStream();
//
//        template = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
//
//        template = buildProperties.map(entries -> template
//                .replace("$version", entries.getVersion())
//                .replace("$timestamp", entries.getTime().toString()))
//                .orElse(template);
//
//        return template;
//    }
}
