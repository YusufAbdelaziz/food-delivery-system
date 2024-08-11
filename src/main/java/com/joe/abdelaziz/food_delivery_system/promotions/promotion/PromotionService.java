package com.joe.abdelaziz.food_delivery_system.promotions.promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.orders.order.OrderDTO;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromotionService {

  private final PromotionRepository promotionRepository;
  private final PromotionMapper promotionMapper;
  private final RestaurantService restaurantService;
  private final RestaurantMapper restaurantMapper;

  public PromotionDTO insertPromotion(PromotionDTO promotionDTO) {

    // A check is made to make sure that a single promo code cannot exist in two
    // records and be active at the same time. So if a promo code is not found, this
    // means an insertion can be made.
    try {
      Promotion existingPromotion = getByCode(promotionDTO.getCode());
      if (existingPromotion.isActive()) {
        throw new BusinessLogicException(
            String.format("You can't have two promo code %s instances active at the same time",
                promotionDTO.getCode()));
      }
    } catch (Exception e) {
    }

    if (promotionDTO.getStartDate().isAfter(promotionDTO.getEndDate())) {
      throw new BusinessLogicException("Start date of a promotion cannot be after end date");
    }

    // If the start date is not yet due, this means the promotion is not active yet.
    if (promotionDTO.getStartDate().isAfter(LocalDateTime.now())) {
      promotionDTO.setActive(false);
    }

    RestaurantDTO restaurantDTO = promotionDTO.getRestaurant();
    Restaurant restaurant = null;
    if (restaurantDTO != null && restaurantDTO.getId() != null) {
      restaurant = restaurantService.getById(restaurantDTO.getId());
      promotionDTO.setRestaurant(restaurantMapper.toRestaurantDTO(restaurant));
    }

    Promotion promotion = promotionMapper.toPromotion(promotionDTO);

    if (restaurant != null)
      promotion.setRestaurant(restaurant);

    Promotion savedPromotion = promotionRepository.save(promotion);
    return promotionMapper.toPromotionDto(savedPromotion);
  }

  public OrderDTO applyPromotionCode(String promotionCode, OrderDTO order) {
    Promotion promotion = getByCode(promotionCode);
    validatePromotion(promotion);
    order.setPromotion(promotionMapper.toPromotionDto(promotion));
    Restaurant promotionRestaurant = promotion.getRestaurant();
    // If restaurant is not null and has an id, this means that the promotion is
    // associated with that restaurant.
    if (promotionRestaurant != null && promotionRestaurant.getId() != null) {
      Optional<OrderRestaurantDTO> opFoundOrderRestaurant = order.getRestaurants().stream()
          .filter(orderRestaurant -> {
            return promotionRestaurant.getId().equals(orderRestaurant.getExistingRestaurant().getId());
          }).findFirst();
      // Note that the discount may be for the restaurant's delivery fees, percentage
      // on restaurant's items, or fixed discount on all items.
      if (opFoundOrderRestaurant.isPresent()) {
        var foundOrderRestaurant = opFoundOrderRestaurant.get();
        DiscountType discountType = promotion.getDiscountType();
        if (discountType == DiscountType.DELIVERY) {
          BigDecimal discountedDeliveryFee = foundOrderRestaurant.getDeliveryFee().getCurrentPrice()
              .subtract(promotion.getDiscountValue());
          foundOrderRestaurant.getDeliveryFee().setDiscountedPrice(
              discountedDeliveryFee.intValue() < 0 ? BigDecimal.ZERO : discountedDeliveryFee);
        } else if (discountType == DiscountType.FIXED) {
          BigDecimal discountedReceipt = foundOrderRestaurant.getTotalRestaurantReceipt()
              .subtract(promotion.getDiscountValue());
          foundOrderRestaurant.setDiscountedTotalRestaurantReceipt(
              discountedReceipt.intValue() < 0 ? BigDecimal.ZERO : discountedReceipt);

        } else if (discountType == DiscountType.PERCENTAGE) {
          BigDecimal discountValue = foundOrderRestaurant.getTotalRestaurantReceipt()
              .multiply(promotion.getDiscountValue().divide(new BigDecimal(100)));
          foundOrderRestaurant.setDiscountedTotalRestaurantReceipt(foundOrderRestaurant.getTotalRestaurantReceipt()
              .subtract(discountValue));
        }
        calculateDiscountedReceipt(order);
        return order;
        // A customer may not include a promotion's associated restaurant into his
        // order. This means that the promotion code applies to nothing.
      } else {
        throw new BusinessLogicException(
            "The restaurant associated with the promotion does not exist in the list of restaurants.");
      }
    }

    if (promotion.getDiscountType() == DiscountType.DELIVERY) {
      order.setDiscountedTotalDeliveryFees(BigDecimal.ZERO);
    } else if (promotion.getDiscountType() == DiscountType.FIXED) {
      order.setDiscountedOrderTotal(order.getOrderTotal().subtract(promotion.getDiscountValue()));
    } else if (promotion.getDiscountType() == DiscountType.PERCENTAGE) {
      order.setDiscountedOrderTotal(
          order.getOrderTotal().multiply(promotion.getDiscountValue().divide(new BigDecimal(100))));
    }

    return order;
  }

  /*
   * Recalculates the receipt of the order after applying a promotion code
   * 
   * Note that you first have to check if you have a discount whether on a
   * delivery fee or a restaurant's receipt. The main concern here is to calculate
   * the restaurant that got a discount with restaurants that didn't.
   * 
   * @param dto
   */
  private void calculateDiscountedReceipt(OrderDTO dto) {
    BigDecimal discountedTotalDeliveryFees = BigDecimal.ZERO;
    BigDecimal discountedOrderTotal = BigDecimal.ZERO;

    boolean hasReceiptDiscount = dto.getRestaurants().stream()
        .anyMatch(restaurant -> restaurant.getDiscountedTotalRestaurantReceipt() != null);

    if (hasReceiptDiscount) {
      for (OrderRestaurantDTO restaurant : dto.getRestaurants()) {
        if (restaurant.getDiscountedTotalRestaurantReceipt() != null) {
          discountedOrderTotal = discountedOrderTotal.add(restaurant.getDiscountedTotalRestaurantReceipt());
        } else {
          discountedOrderTotal = discountedOrderTotal.add(restaurant.getTotalRestaurantReceipt());
        }
      }
      dto.setDiscountedOrderTotal(discountedOrderTotal);
    }

    boolean hasOrderDeliveryFeeDiscount = dto.getRestaurants().stream()
        .anyMatch(restaurant -> restaurant.getDeliveryFee().getDiscountedPrice() != null);

    if (hasOrderDeliveryFeeDiscount) {
      for (OrderRestaurantDTO restaurant : dto.getRestaurants()) {
        if (restaurant.getDeliveryFee().getDiscountedPrice() != null) {
          discountedTotalDeliveryFees = discountedTotalDeliveryFees
              .add(restaurant.getDeliveryFee().getDiscountedPrice());
        } else {
          discountedTotalDeliveryFees = discountedTotalDeliveryFees
              .add(restaurant.getDeliveryFee().getCurrentPrice());
        }
      }
      dto.setDiscountedTotalDeliveryFees(discountedTotalDeliveryFees);
    }

  }

  public void incrementUsageCount(Promotion promotion) {
    promotion.setUsedCount(promotion.getUsedCount() + 1);
    promotionRepository.save(promotion);
  }

  public void validatePromotion(Promotion promotion) {
    if (!promotion.isActive()) {
      throw new BusinessLogicException("Promotion code is inactive");
    }

    if (promotion.getStartDate().isAfter(LocalDateTime.now())) {
      throw new BusinessLogicException("Promotion code is not active yet");
    }

    if (promotion.getEndDate().isBefore(LocalDateTime.now())) {
      promotion.setActive(false);
      promotionRepository.save(promotion);
      throw new BusinessLogicException("Promotion code is expired");
    }

    if (promotion.getUsedCount() >= promotion.getMaxUsers()) {
      promotion.setActive(false);
      promotionRepository.save(promotion);
      throw new BusinessLogicException("Promotion code has exceeded the maximum number of usages");
    }
  }

  public Promotion getById(long id) {
    return promotionRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Promotion id %d is not found", id)));
  }

  public List<PromotionDTO> getAll() {
    return promotionRepository.findAll().stream().map(promotion -> promotionMapper.toPromotionDto(promotion)).toList();
  }

  private Promotion getByCode(String promotionCode) {
    return promotionRepository.findByCodeAndActiveTrue(promotionCode)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Promotion code %s is not found", promotionCode)));
  }

  public PromotionDTO deactivatePromotion(Long id) {
    Promotion promo = getById(id);
    promo.setActive(false);
    return promotionMapper.toPromotionDto(promotionRepository.save(promo));
  }

  public PromotionDTO updatePromotion(PromotionDTO dto) {
    Promotion updatedPromotion = promotionMapper.toUpdatePromotion(dto, getById(dto.getId()));
    return promotionMapper.toPromotionDto(promotionRepository.save(updatedPromotion));
  }

  public void deleteById(Long id) {
    promotionRepository.deleteById(id);
  }
}
