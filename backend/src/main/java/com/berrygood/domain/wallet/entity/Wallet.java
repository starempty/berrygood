package com.berrygood.domain.wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.berrygood.domain.user.entity.User;
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
public class Wallet extends BaseEntity{
	@Column(nullable = false)
	private String address;
	@OneToOne
	@JoinColumn(name = "OWNER_ID")
	private User user;
	private int berry;
	private int balance;
	private int receivecnt;

	public void updateWallet(Wallet wallet) {
		this.balance = wallet.balance;
		if(this.balance < wallet.balance) this.receivecnt += 1;
		else this.berry = wallet.berry;
	}
	
}
