package sample.cafekiosk.spring.api.controller.valid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sample.cafekiosk.spring.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.valid.dto.request.InRequest;
import sample.cafekiosk.spring.api.controller.valid.dto.request.ValidCreateRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ValidControllerTest extends ControllerTestSupport  {

    @DisplayName("하위 Request Dto에서 검증하기")
    @Test
    void valid() throws Exception {
        //given
        ValidCreateRequest request = ValidCreateRequest.builder()
                .list(List.of(
                        InRequest.builder()
                                .id(1L)
                                .name(List.of("hello", "world"))
                                .build(),
                        InRequest.builder()
                                .id(2L)
                                .name(List.of("hello", "world"))
                                .build()
                ))
                .build();

        //when //then
        mockMvc.perform(post("/api/v1/valid")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("하위 Request Dto에서 검증하기 - id값에 null이 들어올 수 없다.")
    @Test
    void validWithoutId() throws Exception {
        //given
        ValidCreateRequest request = ValidCreateRequest.builder()
                .list(List.of(
                        InRequest.builder()
                                .id(null)
                                .name(List.of("hello", "world"))
                                .build(),
                        InRequest.builder()
                                .id(2L)
                                .name(List.of("hello", "world"))
                                .build()
                ))
                .build();

        //when //then
        mockMvc.perform(post("/api/v1/valid")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("id는 필수값입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("하위 Request Dto에서 검증하기 - list값에 빈 리스트가 들어오면 하위 Dto의 valid는 동작하지 않는다.")
    @Test
    void validWithEmptyList() throws Exception {
        //given
        ValidCreateRequest request = ValidCreateRequest.builder()
                .list(List.of(

                ))
                .build();

        //when //then
        mockMvc.perform(post("/api/v1/valid")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("하위 Request Dto에서 검증하기 - 리스트값에 null이 들어올 수 없다.")
    @Test
    void validWithoutList() throws Exception {
        //given
        ValidCreateRequest request = ValidCreateRequest.builder()
                .list(null)
                .build();

        //when //then
        mockMvc.perform(post("/api/v1/valid")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("리스트는 필수값입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }


}