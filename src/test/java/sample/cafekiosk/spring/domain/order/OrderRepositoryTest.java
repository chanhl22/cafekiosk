package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.order.OrderStatus.INIT;
import static sample.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.product.ProductType.BOTTLE;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("시작일시와 종료일시 사이에 있는 원하는 판매상태를 가진 주문들을 조회한다.")
    @Test
    void findOrdersBetweenStartDateTimeAndEndDateTimeBySellingStatus() {
        //given
        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createOrder(products, INIT, LocalDateTime.of(2024, 10, 31, 10, 0));
        Order order2 = createOrder(products, PAYMENT_COMPLETED, LocalDateTime.of(2024, 10, 30, 23, 59, 59));
        Order order3 = createOrder(products, PAYMENT_COMPLETED, LocalDateTime.of(2024, 10, 31, 0, 0));
        Order order4 = createOrder(products, PAYMENT_COMPLETED, LocalDateTime.of(2024, 10, 31, 10, 0));
        Order order5 = createOrder(products, PAYMENT_COMPLETED, LocalDateTime.of(2024, 10, 31, 23, 59, 59));
        orderRepository.saveAll(List.of(order1, order2, order3, order4, order5));

        //when
        List<Order> orders = orderRepository.findOrdersBy(LocalDateTime.of(2024, 10, 31, 0, 0), LocalDateTime.of(2024, 10, 31, 23, 59, 59), PAYMENT_COMPLETED);

        //then
        assertThat(orders).hasSize(2)
                .extracting("totalPrice", "orderStatus")
                .containsExactlyInAnyOrder(
                        tuple(9000, PAYMENT_COMPLETED),
                        tuple(9000, PAYMENT_COMPLETED)
                );
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }

    private Order createOrder(List<Product> products, OrderStatus orderStatus, LocalDateTime registeredDateTime) {
        return Order.builder()
                .products(products)
                .orderStatus(orderStatus)
                .registeredDateTime(registeredDateTime)
                .build();
    }

}