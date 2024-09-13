package sample.cafekiosk.spring.api.controller.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.valid.dto.request.ValidCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ValidController {

    private final ProductService productService;

    @PostMapping("/api/v1/valid")
    public ApiResponse<String> valid(@Valid @RequestBody ValidCreateRequest request) {
        System.out.println(request.toString());
        return ApiResponse.ok("ok");
    }

}
