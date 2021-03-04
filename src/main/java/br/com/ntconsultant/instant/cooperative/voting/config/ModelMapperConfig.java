package br.com.ntconsultant.instant.cooperative.voting.config;


import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModelRequest;
import br.com.ntconsultant.instant.cooperative.voting.dto.SessionModel;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Session;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Pauta.class, PautaModel.class);
        modelMapper.createTypeMap(PautaModelRequest.class, Pauta.class);
        modelMapper.createTypeMap(PautaModelRequest.class, PautaModel.class);
        modelMapper.createTypeMap(Session.class, SessionModel.class);
        return new ModelMapper();
    }
}
