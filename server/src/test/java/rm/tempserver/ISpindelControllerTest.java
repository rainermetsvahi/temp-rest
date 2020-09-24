package rm.tempserver;

import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rm.tempserver.json.ISpindelConfig;
import rm.tempserver.json.Json;
import rm.tempserver.repository.ISpindelSetupRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ISpindelControllerTest {
    private static final String CONFIG_PATH = "/api/v1/ispindel/config";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private ISpindelSetupRepository setupRepository;

    @Test
    public void shouldReadSgFormula() throws Exception {
        List<String> formulas = Arrays.asList(
            "0.9624769036550106 + 0.0014557954208046433 *tilt",
            "0.9540172620401591 + 0.0018366432818672036 *tilt-0.000003883656025339071 *tilt*tilt",
            "0.8569582340773092 + 0.008490982720547135 *tilt-0.00014777102241208132 *tilt*tilt " +
                "+ 9.877083679884538e-7*tilt*tilt*tilt"
        );

        // mock repo
        doAnswer(inv -> {
            return null;
        }).when(this.setupRepository).add(any());

        for (String formula: formulas) {
            this.testConfigUpdate(
                new ISpindelConfig()
                    .setName("iSpindel1")
                    .setFormula(formula)
            );
        }
    }

    @SneakyThrows
    private void testConfigUpdate(ISpindelConfig config) throws Exception {
        this.mockMvc
            .perform(
                post(CONFIG_PATH)
                    .header("Content-Type", "application/json")
                    .content(Json.serialize(config))
            )
            .andExpect(status().isOk());
    }
}
