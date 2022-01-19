package io.ecommerce.userservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ecommerce.userservice.config.test.SpringBootTestSupporter;
import io.ecommerce.userservice.core.client.OrderServiceClient;
import io.ecommerce.userservice.core.domain.dto.request.SignupRequest;
import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.core.repository.UserRepo;
import io.ecommerce.userservice.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static io.ecommerce.userservice.generator.docs.UserSignupDocument.signupUserDocument;
import static io.ecommerce.userservice.generator.mock.UserGenerator.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : choi-ys
 * @date : 2022/01/07 5:10 오후
 */
@SpringBootTestSupporter
@AutoConfigureRestDocs
@DisplayName("API:UserSignup")
class UserSignupControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserRepo userRepo;
    private static final String URL_TEMPLATE = "/user";

    @MockBean
    private OrderServiceClient orderServiceClient;

    public UserSignupControllerTest(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserRepo userRepo
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepo = userRepo;
    }

    @Test
    @DisplayName("[200:POST]회원 가입")
    public void signup() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest(EMAIL, PASSWORD, NAME);

        // When
        ResultActions resultActions = mockMvc.perform(
                post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupRequest))
        );

        // Then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.email").value(signupRequest.getEmail()))
                .andExpect(jsonPath("$.name").value(signupRequest.getName()))
                .andDo(signupUserDocument())
        ;
    }

    @Test
    @DisplayName("[400:POST]회원 가입 실패: 중복된 이메일")
    public void signup_fail_cause_duplicated_email() throws Exception {
        // Given
        User user = userMock();
        userRepo.save(user);
        SignupRequest signupRequest = new SignupRequest(user.getEmail(), user.getPassword(), user.getName());

        // When
        ResultActions resultActions = this.mockMvc.perform(
                post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupRequest))
        );

        // Then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("method").value(HttpMethod.POST.name()))
                .andExpect(jsonPath("path").value(URL_TEMPLATE))
                .andExpect(jsonPath("code").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getCode()))
                .andExpect(jsonPath("message").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getMessage()))
                .andExpect(jsonPath("$.errorDetails.[0].object").value("signupRequest"))
                .andExpect(jsonPath("$.errorDetails.[0].field").value("email"))
                .andExpect(jsonPath("$.errorDetails.[0].code").value("EmailUnique"))
                .andExpect(jsonPath("$.errorDetails.[0].rejectedValue").value(user.getEmail()))
        ;
    }

    @Test
    @DisplayName("[400:POST]회원 가입 실패: 값이 없는 요청")
    public void signup_fail_cause_empty_request() throws Exception {
        // When
        ResultActions resultActions = this.mockMvc.perform(
                post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("method").value(HttpMethod.POST.name()))
                .andExpect(jsonPath("path").value(URL_TEMPLATE))
                .andExpect(jsonPath("code").value(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getCode()))
                .andExpect(jsonPath("message").value(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getMessage()))
                .andExpect(jsonPath("$.errorDetails").isEmpty())
        ;
    }
}