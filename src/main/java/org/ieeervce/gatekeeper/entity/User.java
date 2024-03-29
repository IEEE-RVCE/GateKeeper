package org.ieeervce.gatekeeper.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;



    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "roleName")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "society", referencedColumnName = "societyId",nullable = true)
    private Society society;


    @Column(nullable = false, unique = true)
    private  long number;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean unsubscribed;
    @Column(nullable = false)
    private  boolean enabled;
    public void setName(String name) {
        this.name = name;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setNumber(long number) {
        this.number = number;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Society getSociety() {
        return society;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getNumber() {
        return number;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isUnsubscribed() {
        return unsubscribed;
    }

    public void setUnsubscribed(boolean unsubscribed) {
        this.unsubscribed = unsubscribed;
    }

    public void setSociety(Society society){
        this.society=society;
    }
    public void setRole(Role role){
        this.role= role;
    }
}
