package com.campusfood.campus_food_backend.controller;

import com.campusfood.campus_food_backend.entity.FoodItem;
import com.campusfood.campus_food_backend.entity.Order;
import com.campusfood.campus_food_backend.entity.OrderItem;
import com.campusfood.campus_food_backend.repository.FoodItemRepository;
import com.campusfood.campus_food_backend.repository.OrderItemRepository;
import com.campusfood.campus_food_backend.repository.OrderRepository;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final FoodItemRepository foodItemRepository;

    public OrderController(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            FoodItemRepository foodItemRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.foodItemRepository = foodItemRepository;
    }

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Place an order
    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request) {

        Order order = new Order();

        order.setUser(request.getUser());
        order.setTotalAmount(request.getTotalAmount());
        order.setPickupLocation(request.getPickupLocation());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // Save order items
        if (request.getItems() != null) {

            for (OrderItemRequest itemRequest : request.getItems()) {

                FoodItem foodItem = foodItemRepository
                        .findById(itemRequest.getFoodId())
                        .orElse(null);

                if (foodItem != null) {

                    OrderItem orderItem = new OrderItem();

                    orderItem.setOrder(savedOrder);
                    orderItem.setFoodItem(foodItem);
                    orderItem.setQuantity(itemRequest.getQuantity());
                    orderItem.setPrice(foodItem.getPrice());

                    orderItemRepository.save(orderItem);
                }
            }
        }

        return savedOrder;
    }

    // Update order status
    @PutMapping("/{id}/status")
    public Order updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return null;
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }

    // Request class for placing order
    public static class OrderRequest {

        private com.campusfood.campus_food_backend.entity.User user;
        private double totalAmount;
        private String pickupLocation;
        private List<OrderItemRequest> items;

        public com.campusfood.campus_food_backend.entity.User getUser() {
            return user;
        }

        public void setUser(
                com.campusfood.campus_food_backend.entity.User user) {
            this.user = user;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getPickupLocation() {
            return pickupLocation;
        }

        public void setPickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
        }

        public List<OrderItemRequest> getItems() {
            return items;
        }

        public void setItems(List<OrderItemRequest> items) {
            this.items = items;
        }
    }

    // Request class for individual food item
    public static class OrderItemRequest {

        private Long foodId;
        private int quantity;

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}