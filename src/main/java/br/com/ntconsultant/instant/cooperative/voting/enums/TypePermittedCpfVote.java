package br.com.ntconsultant.instant.cooperative.voting.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Daniel Santos
 */
@Slf4j
public enum TypePermittedCpfVote {

    ABLE_TO_VOTE("ABLE_TO_VOTE"),

    UNABLE_TO_VOTE("UNABLE_TO_VOTE");

    private static final Map<String, TypePermittedCpfVote> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<TypePermittedCpfVote> enumSet = EnumSet.allOf(TypePermittedCpfVote.class);

        for (TypePermittedCpfVote type : enumSet)
            lookup.put(type.code, type);
    }

    private String code;

    TypePermittedCpfVote(String code) {
        inicialize(code);
    }

    private void inicialize(String codigo) {
        this.code = codigo;
    }

    public static TypePermittedCpfVote fromCode(String code) {
        if (lookup.containsKey(code))
            return lookup.get(code);
        throw getIllegalArgumentException(code);
    }

    public static TypePermittedCpfVote of(String code) {
        return Stream.of(TypePermittedCpfVote.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> getIllegalArgumentException(code));
    }

    private static IllegalArgumentException getIllegalArgumentException(String code) {
        log.error("[instant-cooperative-voting] [type permitted cpf vote] Invalid Type Permitted Cpf Vote Code {} ", code);
        return new IllegalArgumentException("Invalid Type Permitted Cpf Vote Code: " + code);
    }

    public String getCode() {
        return this.code;
    }

    public boolean isAbleToVote() {
        return Objects.equals(this, ABLE_TO_VOTE);
    }

    public boolean isUnableToVote() {
        return Objects.equals(this, UNABLE_TO_VOTE);
    }

}
