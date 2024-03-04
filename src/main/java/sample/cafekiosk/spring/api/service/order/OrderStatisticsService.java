package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderStatisticsService {

    public void sendOrderStatisticsMail(LocalDateTime orderDate, String email) {
        // 해당 일자에 결제완료된 주문들을 가져와서

        // 총 매출 합계를 계산하고

        // 메일 전송
    }

}
