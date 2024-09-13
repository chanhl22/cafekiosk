package sample.cafekiosk.spring.api.controller.valid.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidCreateRequest {

    @NotNull(message = "리스트는 필수값입니다.")
    @Valid
    private List<InRequest> list;

    @Builder
    private ValidCreateRequest(List<InRequest> list) {
        this.list = list;
    }

}
