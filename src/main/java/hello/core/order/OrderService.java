package hello.core.order;

public interface OrderService {
    //최종 order 내용 반환 인터페이스
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
