package in.finbox.feature_flag.controller;

import in.finbox.feature_flag.model.FeatureFlag;
import in.finbox.feature_flag.service.FeatureFlagService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeatureFlagController.class)
class FeatureFlagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeatureFlagService featureFlagService;

    @Test
    void testCreateFlag() throws Exception {
        FeatureFlag flag = FeatureFlag.builder().name("flag1").description("desc").build();
        Mockito.when(featureFlagService.createFlag(eq("flag1"), eq("desc"))).thenReturn(flag);

        mockMvc.perform(post("/flags/create")
                        .param("name", "flag1")
                        .param("description", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("flag1"))
                .andExpect(jsonPath("$.description").value("desc"));
    }

    @Test
    void testAddDependency() throws Exception {
        doNothing().when(featureFlagService).addDependency(eq("parent"), eq("child"));

        mockMvc.perform(post("/flags/dependency")
                        .param("parent", "parent")
                        .param("child", "child"))
                .andExpect(status().isAccepted());
    }

    @Test
    void testSetFlagStatus() throws Exception {
        doNothing().when(featureFlagService).setFlagStatus(eq(1L), eq("flag1"), eq(true));

        mockMvc.perform(post("/flags/set")
                        .param("clientId", "1")
                        .param("flag", "flag1")
                        .param("status", "true"))
                .andExpect(status().isAccepted());
    }

    @Test
    void testGetFlagStatus() throws Exception {
        Mockito.when(featureFlagService.getFlagStatus(eq(1L), eq("flag1"))).thenReturn(true);

        mockMvc.perform(get("/flags/status")
                        .param("clientId", "1")
                        .param("flag", "flag1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetEnabledFlags() throws Exception {
        List<String> flags = Arrays.asList("flag1", "flag2");
        Mockito.when(featureFlagService.getEnabledFlags(eq(1L))).thenReturn(flags);

        mockMvc.perform(get("/flags/enabled")
                        .param("clientId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("flag1"))
                .andExpect(jsonPath("$[1]").value("flag2"));
    }

    @Test
    void testGetAllFlags() throws Exception {
        List<FeatureFlag> flags = List.of(
                FeatureFlag.builder().name("flag1").description("desc1").build(),
                FeatureFlag.builder().name("flag2").description("desc2").build()
        );
        Mockito.when(featureFlagService.getAllFlags()).thenReturn(flags);

        mockMvc.perform(get("/flags/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("flag1"))
                .andExpect(jsonPath("$[1].name").value("flag2"));
    }
}
