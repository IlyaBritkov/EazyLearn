package com.eazylearn;

import com.eazylearn.enums.ProficiencyLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EazyLearnApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void proficiencyLevelEnumsResolving() {
        ProficiencyLevel proficiencyLevel = ProficiencyLevel.LOW;
        int levelPoints = proficiencyLevel.getLevelPoints();
        int expectedPoints = 15;
        Assertions.assertEquals(expectedPoints, levelPoints);
    }

}
