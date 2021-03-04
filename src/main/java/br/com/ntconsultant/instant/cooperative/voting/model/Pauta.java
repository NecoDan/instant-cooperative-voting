package br.com.ntconsultant.instant.cooperative.voting.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 */
@Document
@Data
@Builder
public class Pauta {
    @Id
    private String id;
    private String titulo;
    @DBRef
    private Session session;
    private LocalDateTime dt;

    public void gerarDtLancamento() {
        this.dt = LocalDateTime.now();
    }

//    public Pauta iniciarSessao(Instant fim) {
//        if (sessao != null) throw new SessaoJaExisteException(id);
//
//        sessao = sessao.iniciar(fim);
//        return this;
//    }
//
//    public Pauta votar(Voto voto) {
//        if (sessao == null) throw new SessaoNaoEncontradaException(id);
//
//        sessaoVotacao.adicionarVoto(voto);
//        return this;
//    }
//
//    public boolean isFinalizada() {
//        return Optional.ofNullable(sessaoVotacao)
//                .map(SessaoVotacao::isFinalizada)
//                .orElse(false);
//    }
//
//    public Map<OpcaoVoto, Long> getResultadoVotacao() {
//        return Optional.ofNullable(sessaoVotacao)
//                .map(SessaoVotacao::getResultadoVotacao)
//                .orElse(null);
//    }
}
