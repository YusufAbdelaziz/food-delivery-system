package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.orders.orderItem.OrderItem;
import com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee.RestaurantDeliveryFee;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.utiles.validation.NullableRating;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@SqlResultSetMapping(name = "OrderRestaurantStatsDTOMapping", classes = @ConstructorResult(targetClass = OrderRestaurantStatsDTO.class, columns = {
    @ColumnResult(name = "restaurantId", type = Long.class),
    @ColumnResult(name = "avgRating", type = BigDecimal.class)
}))
@NamedNativeQuery(name = "OrderRestaurant.findRestaurantsAvgRating", query = "SELECT r.restaurant_id AS restaurantId, AVG(r.restaurant_rating) AS avgRating "
    +
    "FROM order_restaurant r GROUP BY r.restaurant_id", resultSetMapping = "OrderRestaurantStatsDTOMapping")
/**
 * Represents an order restaurant entity in the database.
 * This entity represents the many-to-many relationship between orders and
 * restaurants.
 */
@Entity
@Table(name = "order_restaurant")
@Getter
@Setter
public class OrderRestaurant extends BaseEntity {

  /**
   * The unique identifier of the order restaurant.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_restaurant_id")
  private Long id;

  @Transient
  private String name;

  /**
   * The order associated with this order restaurant.
   */
  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
  private Order order;

  @NullableRating
  private Byte restaurantRating;

  @Size(max = 2000, message = "Restaurant feedback should be at least 2000 characters in length")
  private String restaurantFeedback;
  /**
   * The restaurant associated with this order restaurant.
   */
  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  @JsonIgnoreProperties({ "address", "orderRestaurants", "cuisines", "promotions", "menu" })
  private Restaurant existingRestaurant;

  /**
   * The delivery fee associated with this order restaurant.
   */
  @OneToOne(mappedBy = "orderRestaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private RestaurantDeliveryFee deliveryFee;

  /**
   * The order items associated with this order restaurant.
   */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "order_restaurant_id")
  private Set<OrderItem> items = new HashSet<>();

  private BigDecimal totalRestaurantReceipt;

  private BigDecimal discountedTotalRestaurantReceipt;

  public void addItem(OrderItem item) {
    items.add(item);
  }

  @PostLoad
  public void loadName() {
    name = existingRestaurant.getName();
  }

}
