package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        //given
        ProductType type = ProductType.HANDMADE;

        //when
        boolean result = ProductType.containsStockType(type);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        //given
        ProductType type1 = ProductType.BAKERY;
        ProductType type2 = ProductType.BOTTLE;

        //when
        boolean result1 = ProductType.containsStockType(type1);
        boolean result2 = ProductType.containsStockType(type2);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

}