package br.com.ntconsultant.instant.cooperative.voting.util;

import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class PautaCreator {

    public static PautaModel createValidPautaModel(Pauta pauta) {
        ObjectMapper objectMapper = inicializeObjectMapper();

        PautaModel pautaModel = objectMapper.convertValue(pauta, PautaModel.class);
        pautaModel.setStatus((Objects.isNull(pauta.getTypeStatusSession())) ? "" : pauta.getTypeStatusSession().getCode());
        pautaModel.generateVoteTotalizers(Objects.isNull(pauta.getSession()) ? Collections.emptyList() : pauta.getSession().getVotes());
        pautaModel.setDtCreated(pauta.getDtCreated());

        return pautaModel;
    }

    public static Pauta createPautaToBeSaved() {
        return Pauta.builder()
                .title("O voto deve ser obrigatorio")
                .build();
    }

    public static Pauta createValidPauta() {
        return Pauta.builder()
                .id(UUID.randomUUID().toString())
                .title("Deve haver flebilizacao para o porte de armas")
                .session(Session.start(Instant.now().minusSeconds(10)))
                .build()
                .generateDtCreatedThis();
    }

    public static Pauta createdValidUpdatedPauta() {
        return Pauta.builder()
                .id(UUID.randomUUID().toString())
                .title("Assembleia geral deve ser reunir diaramente")
                .build();
    }

    private static ObjectMapper inicializeObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return Jackson2ObjectMapperBuilder.json().modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
    }
}
