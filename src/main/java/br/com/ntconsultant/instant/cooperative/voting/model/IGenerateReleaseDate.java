package br.com.ntconsultant.instant.cooperative.voting.model;

import java.time.LocalDateTime;

public interface IGenerateReleaseDate {
    static LocalDateTime generateDtRelease() {
        return LocalDateTime.now();
    }
}
