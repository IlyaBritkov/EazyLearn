package com.eazylearn.scheduling;

import com.eazylearn.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CardScheduledTasks {
    private final CardRepository cardRepository;

    /**
     * Lower ProficiencyLevel of not updated for long time (1 week) Cards.
     **/
    @Scheduled(fixedDelay = 3_600_000) // 1 week
    @Transactional
    public void decreaseCardsProficiencyLevel() {
        cardRepository.decreaseProficiencyLevelIfLastUpdateDateLessThanWeek(5);
    }
}
