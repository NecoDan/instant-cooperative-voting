package br.com.ntconsultant.instant.cooperative.voting.jobs;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.ProcessEndSessionException;
import br.com.ntconsultant.instant.cooperative.voting.service.IProcessEndSessionMessageService;
import br.com.ntconsultant.instant.cooperative.voting.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessEndSessionMessageJob {

    private final IProcessEndSessionMessageService processEndSessionMessageService;

    @Scheduled(cron = "${job.cron-job}")
    @Async("asyncExecutor")
//    @SchedulerLock(name = "ascertainmentDeleteRecordsJob", lockAtLeastFor = "${lock-job.at-least}", lockAtMostFor = "${lock-job.at-most}")
    public void executeDeletion() {
        try {
            MDC.put("startTime", String.valueOf(System.currentTimeMillis()));
            long startTime = System.currentTimeMillis();
            log.info("Instant Cooperative Voting: JOB to process end of session messages, start process message.");

            processEndSessionMessageService.executeProcessMessages();

            Utils.timeElapsed();
            log.info("Instant Cooperative Voting: End process message - JOB to process end of session messages.");
        } catch (ProcessEndSessionException e) {
            log.error("Error reading ProcessEndSessionMessagesJob in ProcessEndSessionMessagesService|method:executeProcessMessages|incident:{}", e.getMessage());
        }
    }
}
