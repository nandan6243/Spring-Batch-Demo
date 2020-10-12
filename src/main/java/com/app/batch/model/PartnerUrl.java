package com.app.batch.model;

import javax.persistence.*;

@Table(name = "partner_url", schema="uber_eat")
@Entity
public class PartnerUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "partner_url")
	private String partnerUrl;

	public String getPartnerUrl() {
		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}
}
