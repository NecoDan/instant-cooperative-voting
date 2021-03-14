package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.kafka.RecordVotingResult;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessEndSessionMessageService implements IProcessEndSessionMessageService {

    private final RecordVotingResult recordVotingResult;
    private final IPautaReportService pautaReportService;

    @Override
    public void executeProcessMessages() {
        Flux<Pauta> pautaFlux = pautaReportService.getAll();
        List<Pauta> pautaList = pautaFlux.collectList().block();

        if (Objects.isNull(pautaList) || pautaList.isEmpty()) {
            return;
        }

        pautaList.parallelStream()
                .forEach(this::generateMessageFrom);
    }

    public void generateMessageFrom(Pauta pauta) {
        if (pauta.isFinished())
            recordVotingResult.execute(pauta);
    }
}
