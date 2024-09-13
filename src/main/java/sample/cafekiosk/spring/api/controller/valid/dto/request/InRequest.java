package sample.cafekiosk.spring.api.controller.valid.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class InRequest {

    @NotNull(message = "id는 필수값입니다.")
    private Long id;

    @NotNull(message = "이름은 필수값입니다.")
    private List<String> name;

    @Builder
    private InRequest(Long id, List<String> name) {
        this.id = id;
        this.name = name;
    }

}
