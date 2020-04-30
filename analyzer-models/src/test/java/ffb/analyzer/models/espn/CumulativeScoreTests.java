package ffb.analyzer.models.espn;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CumulativeScoreTests {
    private static final String CUMULATIVE_SCORE_FILE = "cumulative-score.json";

    @Test
    public void testCumulativeScoreDeserialization() throws IOException {
        File file = new File(Objects.requireNonNull(getClass()
            .getClassLoader()
            .getResource(CUMULATIVE_SCORE_FILE)
        ).getFile());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<CumulativeScore> cumulativeScores = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, CumulativeScore.class));

        Assert.assertEquals(1, cumulativeScores.size());
        Assert.assertEquals(0, cumulativeScores.get(0).getLosses());
        Assert.assertEquals(0, cumulativeScores.get(0).getWins());
        Assert.assertEquals(0, cumulativeScores.get(0).getTies());
        Assert.assertEquals(7, cumulativeScores.get(0).getScoresByStats().getScores().size());
    }
}