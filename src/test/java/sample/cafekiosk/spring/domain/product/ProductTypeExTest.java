package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeExTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockTypeEx() {
        //given
        ProductType[] productTypes = ProductType.values();

        for (ProductType productType : productTypes) {
            if (productType == ProductType.HANDMADE) {
                //when
                boolean result = ProductType.containsStockType(productType);

                //then
                assertThat(result).isFalse();
            }
        }

        for (ProductType productType : productTypes) {
            if (productType == ProductType.BAKERY || productType == ProductType.BOTTLE) {
                //when
                boolean result = ProductType.containsStockType(productType);

                //then
                assertThat(result).isTrue();
            }
        }
    }

}