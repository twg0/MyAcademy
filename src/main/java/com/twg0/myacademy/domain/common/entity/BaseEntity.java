package com.twg0.myacademy.domain.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedDate
	@Column(name = "create_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "update_at")
	private LocalDateTime updateAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createBy;

	@LastModifiedDate
	@Column(name = "updated_by")
	private String updatedBy;
}
