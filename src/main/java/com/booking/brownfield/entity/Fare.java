package com.booking.brownfield.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Fare implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonIgnore
	private int id;
	private int economyFare;
	private int businessFare;
	private int premiumFare;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEconomyFare() {
		return economyFare;
	}

	public void setEconomyFare(int economyFare) {
		this.economyFare = economyFare;
	}

	public int getBusinessFare() {
		return businessFare;
	}

	public void setBusinessFare(int businessFare) {
		this.businessFare = businessFare;
	}

	public int getPremiumFare() {
		return premiumFare;
	}

	public void setPremiumFare(int premiumFare) {
		this.premiumFare = premiumFare;
	}

}
