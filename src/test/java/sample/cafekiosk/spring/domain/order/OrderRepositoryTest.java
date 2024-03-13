package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.order.OrderStatus.INIT;
import static sample.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;

@ActiveProfiles("test")
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("시작일시와 종료일시 사이에 있는 원하는 판매상태를 가진 주문들을 조회한다.")
    @Test
    void findOrdersBetweenStartDateTimeAndEndDateTimeBySellingStatus() {
        //given
        Order order1 = createOrder(PAYMENT_COMPLETED, 4000, LocalDateTime.of(2023, 10, 31, 10, 0));
        Order order2 = createOrder(INIT, 5000, LocalDateTime.of(2023, 10, 31, 10, 0));
        Order order3 = createOrder(PAYMENT_COMPLETED, 6000, LocalDateTime.of(2023, 10, 31, 10, 0));
        orderRepository.saveAll(List.of(order1, order2, order3));

        //when
        List<Order> orders = orderRepository.findOrdersBy(LocalDateTime.of(2023, 10, 31, 0, 0), LocalDateTime.of(2023, 10, 31, 23, 59), PAYMENT_COMPLETED);

        //then
        assertThat(orders).hasSize(2)
                .extracting("totalPrice", "orderStatus")
                .containsExactlyInAnyOrder(
                        tuple(4000, PAYMENT_COMPLETED),
                        tuple(6000, PAYMENT_COMPLETED)
                );
    }

    private Order createOrder(OrderStatus orderStatus, int totalPrice, LocalDateTime registeredDateTime) {
        return Order.builder()
                .orderStatus(orderStatus)
                .totalPrice(totalPrice)
                .registeredDateTime(registeredDateTime)
                .build();
    }

}