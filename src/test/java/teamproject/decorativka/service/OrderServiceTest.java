package teamproject.decorativka.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.order.OrderCreateRequestDto;
import teamproject.decorativka.dto.order.OrderItemCreateRequestDto;
import teamproject.decorativka.dto.order.OrderItemResponseDto;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.mapper.OrderMapper;
import teamproject.decorativka.model.Order;
import teamproject.decorativka.model.OrderItem;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.repository.OrderItemRepository;
import teamproject.decorativka.repository.OrderRepository;
import teamproject.decorativka.service.impl.EmailContentServiceImpl;
import teamproject.decorativka.service.impl.EmailSenderServiceImpl;
import teamproject.decorativka.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private static final Long VALID_ID = 1L;
    private static final Long NOT_VALID_ID = -1L;
    private static final String VALID_FIRST_NAME = "First name";
    private static final String VALID_LAST_NAME = "Last name";
    private static final String VALID_PATRONYMIC = "Patronymic";
    private static final String VALID_SHIPPING_ADDRESS = "Anywhere 123";
    private static final String VALID_EMAIL = "email@mail.com";
    private static final String VALID_PHONE_NUMBER = "0967894561";
    private static final String VALID_COMMENT = "Valid comment";
    private static final Order.Status VALID_STATUS = Order.Status.PENDING;
    private static final String VALID_STATUS_STRING_VALUE = "COMPLETED";
    private static final BigDecimal VALID_TOTAL = BigDecimal.TEN;
    private static final LocalDateTime VALID_ORDER_DATE = LocalDateTime.now();
    private static final Integer VALID_QUANTITY = 2;
    private static final BigDecimal VALID_PRICE = BigDecimal.valueOf(5);
    private static final Product VALID_PRODUCT = createValidProduct();

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private ProductService productService;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private EmailSenderServiceImpl emailSenderServiceImpl;
    @Mock
    private EmailContentServiceImpl emailContentService;

    private static Product createValidProduct() {
        Product product = new Product();
        product.setId(VALID_ID);
        product.setPrice(VALID_PRICE);
        return product;
    }

    private Order createValidOrder() {
        Order order = new Order();
        order.setId(VALID_ID);
        order.setFirstName(VALID_FIRST_NAME);
        order.setLastName(VALID_LAST_NAME);
        order.setPatronymic(VALID_PATRONYMIC);
        order.setShippingAddress(VALID_SHIPPING_ADDRESS);
        order.setEmail(VALID_EMAIL);
        order.setPhoneNumber(VALID_PHONE_NUMBER);
        order.setComment(VALID_COMMENT);
        order.setStatus(VALID_STATUS);
        order.setTotal(VALID_TOTAL);
        order.setOrderDate(VALID_ORDER_DATE);
        return order;
    }

    private OrderItem createValidOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(createValidOrder());
        orderItem.setQuantity(VALID_QUANTITY);
        orderItem.setProduct(createValidProduct());
        orderItem.setPrice(VALID_TOTAL);
        return orderItem;
    }

    private OrderItemCreateRequestDto createValidOrderItemRequestDto() {
        return new OrderItemCreateRequestDto(
                VALID_ID, VALID_QUANTITY
        );
    }

    private OrderCreateRequestDto createValidOrderRequestDto() {
        return new OrderCreateRequestDto(List.of(createValidOrderItemRequestDto()),
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_PATRONYMIC,
                VALID_SHIPPING_ADDRESS,
                VALID_EMAIL,
                VALID_PHONE_NUMBER,
                VALID_COMMENT
        );
    }

    private OrderItemResponseDto createValidOrderItemResponseDto() {
        return new OrderItemResponseDto(
                VALID_ID,
                VALID_ID,
                VALID_QUANTITY,
                VALID_TOTAL
        );
    }

    private OrderResponseDto createValidOrderResponseDto() {
        return new OrderResponseDto(
                VALID_ID,
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_PATRONYMIC,
                VALID_SHIPPING_ADDRESS,
                VALID_EMAIL,
                VALID_PHONE_NUMBER,
                VALID_STATUS,
                VALID_COMMENT,
                VALID_TOTAL,
                VALID_ORDER_DATE,
                List.of(createValidOrderItemResponseDto())
        );
    }

    @Test
    @DisplayName("Verify placeOrder() method works")
    public void placeOrder_ValidRequestDto_ValidOrderResponseDto() {
        OrderCreateRequestDto requestDto = createValidOrderRequestDto();
        OrderResponseDto expectedResponse = createValidOrderResponseDto();
        Order mockOrder = createValidOrder();
        OrderItem mockOrderItem = createValidOrderItem();
        when(productService.getAllByIds(anyList()))
                .thenReturn(List.of(VALID_PRODUCT));
        when(orderItemRepository.save(any(OrderItem.class)))
                .thenReturn(mockOrderItem);
        when(orderMapper.toModel(any(OrderCreateRequestDto.class)))
                .thenReturn(mockOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
        when(orderMapper.toDto(any(Order.class))).thenReturn(expectedResponse);
        when(emailContentService.createOrderConfirmationContent(any(Order.class)))
                .thenReturn("Mocked email content");

        OrderResponseDto actualResponse = orderService.placeOrder(requestDto);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(expectedResponse, actualResponse);
        verify(productService).getAllByIds(anyList());
        verify(orderItemRepository).save(any(OrderItem.class));
        verify(orderMapper).toModel(requestDto);
        verify(orderMapper).toDto(mockOrder);
        verify(emailSenderServiceImpl)
                .sendEmail(any(String.class),any(String.class), any(String.class));
    }

    @Test
    @DisplayName("Verify getAllOrders() method works")
    public void getAllOrders_ValidPageable_ValidListOrderResponseDto() {
        List<Order> orders = List.of(createValidOrder());
        List<OrderResponseDto> expected = List.of(createValidOrderResponseDto());
        Pageable pageable = PageRequest.of(0, 10);
        when(orderRepository.findAllWithOrderItems(pageable)).thenReturn(orders);
        when(orderMapper.toDto(any(Order.class)))
                .thenReturn(createValidOrderResponseDto());

        List<OrderResponseDto> actual = orderService.getAllOrders(pageable);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify getAllNotCompletedOrders() method works")
    public void getAllNotCompletedOrders_ValidPageable_ValidListOrderResponseDto() {
        List<Order> orders = List.of(createValidOrder());
        List<OrderResponseDto> expected = List.of(createValidOrderResponseDto());
        Pageable pageable = PageRequest.of(0, 10);
        when(orderRepository.findAllWithOrderItems(pageable)).thenReturn(orders);
        when(orderMapper.toDto(any(Order.class)))
                .thenReturn(createValidOrderResponseDto());

        List<OrderResponseDto> actual = orderService.getAllNotCompletedOrders(pageable);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify updateStatus() method works")
    public void updateStatus_ValidIdAndStatus_UpdatedStatus() {
        Order validOrder = createValidOrder();
        validOrder.setStatus(Order.Status.COMPLETED);
        OrderResponseDto expected = new OrderResponseDto(
                VALID_ID,
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_PATRONYMIC,
                VALID_SHIPPING_ADDRESS,
                VALID_EMAIL,
                VALID_PHONE_NUMBER,
                Order.Status.COMPLETED,
                VALID_COMMENT,
                VALID_TOTAL,
                VALID_ORDER_DATE,
                List.of(createValidOrderItemResponseDto())
        );

        when(orderRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(createValidOrder()));
        when(orderRepository.save(validOrder)).thenReturn(validOrder);
        when(orderMapper.toDto(validOrder)).thenReturn(expected);
        when(emailContentService.createStatusUpdateContent(
                any(Order.class), any(String.class)))
                .thenReturn("Mocked email content");
        OrderResponseDto actual = orderService
                .updateOrderStatus(VALID_ID, VALID_STATUS_STRING_VALUE);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify updateStatus throw expection with not valid id")
    public void updateStatus_NotValidId_EntityNotFoundException() {
        when(orderRepository.findByIdAndDeletedFalse(NOT_VALID_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderService.updateOrderStatus(NOT_VALID_ID,
                        VALID_STATUS_STRING_VALUE));
    }
}
