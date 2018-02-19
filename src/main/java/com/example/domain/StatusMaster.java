package com.example.domain;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;


@Entity
public class StatusMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotBlank
	private Long status_Id;

	@NotBlank
	private String status_name;

	public Long getStatus_Id() {
		return status_Id;
	}

	public void setStatus_Id(Long status_Id) {
		this.status_Id = status_Id;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
}