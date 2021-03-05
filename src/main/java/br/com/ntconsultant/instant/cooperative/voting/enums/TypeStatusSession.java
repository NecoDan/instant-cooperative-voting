package br.com.ntconsultant.instant.cooperative.voting.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Daniel Santos
 */
public enum TypeStatusSession {

    UNDEFINED("UNDEFINED", "0 - UNDEFINED"),

    OPENING("OPENING", "1 - OPENING"),

    FINISHED("FISHISHED", "2 - FINISHED");

    private static final Map<String, TypeStatusSession> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<TypeStatusSession> enumSet = EnumSet.allOf(TypeStatusSession.class);

        for (TypeStatusSession type : enumSet)
            lookup.put(type.code, type);
    }

    private String code;
    private String description;

    TypeStatusSession(String code, String description) {
        inicialize(code, description);
    }

    private void inicialize(String codigo, String descricao) {
        this.code = codigo;
        this.description = descricao;
    }

    public static TypeStatusSession fromCode(String code) {
        if (lookup.containsKey(code))
            return lookup.get(code);
        throw new IllegalArgumentException("Invalid Type Status Session: " + code);
    }

    public static TypeStatusSession of(String codigo) {
        return Stream.of(TypeStatusSession.values()).filter(p -> p.getCode().equals(codigo)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isOpening() {
        return Objects.equals(this, OPENING);
    }

    public boolean isFinished() {
        return Objects.equals(this, FINISHED);
    }
}
