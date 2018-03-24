package org.cinema.actor.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Actor implements Serializable {
	
	@Id
	private Integer id;
	
	@NotBlank
	private String nom;
	
	@NotBlank
	private String prenom;
}
