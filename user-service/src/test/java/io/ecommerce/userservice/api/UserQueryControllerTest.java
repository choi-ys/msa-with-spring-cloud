package io.ecommerce.userservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ecommerce.userservice.config.test.SpringBootTestSupporter;
import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.error.ErrorCode;
import io.ecommerce.userservice.generator.UserGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author : choi-ys
 * @date : 2022/01/08 2:36 오후
 */
@SpringBootTestSupporter
@DisplayName("API:userQuery")
@Import(UserGenerator.class)
class UserQueryControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserGenerator userGenerator;
    private final String USER_URL = "/user";

    public UserQueryControllerTest(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserGenerator userGenerator
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userGenerator = userGenerator;
    }

    @Test
    @DisplayName("[200:GET]회원조회")
    public void findById() throws Exception {
        // Given
        final User user = userGenerator.savedUser();
        final String urlTemplate = String.format("%s/{id}", USER_URL);

        // When
        ResultActions resultActions = this.mockMvc.perform(get(urlTemplate, user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[400:GET]회원조회 실패:존재하지 않는 회원")
    public void newMock() throws Exception {
        // Given
        final String urlTemplate = String.format("%s/%s", USER_URL, 0L);

        // When
        ResultActions resultActions = this.mockMvc.perform(get(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        resultActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.code").value(ErrorCode.RESOURCE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.RESOURCE_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$.path").value(urlTemplate))
                .andExpect(jsonPath("$.errorDetails").exists())
        ;
    }
}