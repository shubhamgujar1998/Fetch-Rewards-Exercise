package com.shubham.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable=false)
    private int id;

    @Column(nullable=false)
    private String payer;

    @Column(nullable=false)
    private int points;

    @Column(nullable=false)
    private String timestamp;
}
