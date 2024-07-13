package com.joe.abdelaziz.food_delivery_system.base;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class BaseEntity {

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

}
