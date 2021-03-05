package br.com.ntconsultant.instant.cooperative.voting.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Daniel Santos
 */
public enum VoteType {

    SIM("SIM", "1 - SIM"),

    NAO("NAO", "2 - NÃO");

    private static final Map<String, VoteType> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<VoteType> enumSet = EnumSet.allOf(VoteType.class);

        for (VoteType type : enumSet)
            lookup.put(type.code, type);
    }

    private String code;
    private String description;

    VoteType(String code, String description) {
        inicialize(code, description);
    }

    private void inicialize(String codigo, String descricao) {
        this.code = codigo;
        this.description = descricao;
    }

    public static VoteType fromCode(String code) {
        if (lookup.containsKey(code))
            return lookup.get(code);
        throw new IllegalArgumentException("Invalid Vote Type Code: " + code);
    }

    public static VoteType of(String codigo) {
        return Stream.of(VoteType.values()).filter(p -> p.getCode().equals(codigo)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isYes() {
        return Objects.equals(this, SIM);
    }

    public boolean isNo() {
        return Objects.equals(this, NAO);
    }
}
