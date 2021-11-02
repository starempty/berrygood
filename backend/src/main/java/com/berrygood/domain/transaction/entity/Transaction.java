package com.berrygood.domain.transaction.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.berrygood.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity{
	@Column(nullable = false)
	private String hash;
	@Column(nullable = true)
	private String nonce;
	@Column(nullable = false)
	private String block_hash;
	@Column(nullable = false)
	private String block_number;
	@Column(nullable = false)
	private String transaction_index;
	@Column(nullable = false)
	private String fromhash;
	@Column(nullable = false)
	private String tohash;
	@Column(nullable = true)	
	private String value;
	@Column(nullable = false)	
	private String gas_price;
	@Column(nullable = true)	
	private String gas;
	@Column(nullable = true)	
	private String input;
	@Column(nullable = true)	
	private String creates;
	@Column(nullable = true)	
	private String public_key;
	@Column(nullable = true)	
	private String raw;
	@Column(nullable = false)	
	private String r;
	@Column(nullable = true)	
	private String s;
	@Column(nullable = true)	
	private int v;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	private Date stored_at;
}
