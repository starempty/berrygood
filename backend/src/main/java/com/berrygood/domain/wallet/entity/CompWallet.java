package com.berrygood.domain.wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.global.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompWallet extends BaseEntity{
	@Column(nullable = false)
	private String address;
	@OneToOne
	@JoinColumn(name = "OWNER_ID")
	private Comp comp;
	private int berry;
	private int balance;
	
	public void updateWallet(CompWallet wallet) {
		this.balance = wallet.balance;
		this.berry = wallet.berry;
	}
}
