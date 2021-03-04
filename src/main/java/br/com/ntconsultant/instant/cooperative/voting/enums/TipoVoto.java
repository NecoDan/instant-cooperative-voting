package br.com.ntconsultant.instant.cooperative.voting.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Daniel Santos
 */
public enum TipoVoto {
    SIM("SIM", "1 - SIM"),

    NAO("NAO", "2 - NÃO");

    private static final Map<String, TipoVoto> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<TipoVoto> enumSet = EnumSet.allOf(TipoVoto.class);

        for (TipoVoto type : enumSet)
            lookup.put(type.codigo, type);
    }

    private String codigo;
    private String descricao;

    TipoVoto(String codigo, String descricao) {
        inicialize(codigo, descricao);
    }

    private void inicialize(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoVoto fromCodigo(String codigo) {
        if (lookup.containsKey(codigo))
            return lookup.get(codigo);
        throw new IllegalArgumentException("Código do Tipo Sexo inválido: " + codigo);
    }

    public static TipoVoto of(String codigo) {
        return Stream.of(TipoVoto.values()).filter(p -> p.getCodigo().equals(codigo)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean isFeminino() {
        return Objects.equals(this, SIM);
    }

    public boolean isMasculino() {
        return Objects.equals(this, NAO);
    }
}
