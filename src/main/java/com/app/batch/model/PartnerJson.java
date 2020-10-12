package com.app.batch.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Table(name = "partner_json", schema="uber_eat")
@Entity
@TypeDef(typeClass = JsonBinaryType.class, name = "jsonb")
public class PartnerJson {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "response", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private String response;

    @Column(name = "date_time")
    private Timestamp dateTime;

    public PartnerJson() {
    }

    public PartnerJson(Integer id, String partnerName, String response, Timestamp dateTime) {
        this.id = id;
        this.partnerName = partnerName;
        this.response = response;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
