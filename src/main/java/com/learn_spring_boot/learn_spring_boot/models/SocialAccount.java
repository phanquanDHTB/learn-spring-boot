package com.learn_spring_boot.learn_spring_boot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider", length = 20)
    private String provider;

    @Column(name = "provider_id", length = 50)
    private String providerId;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
