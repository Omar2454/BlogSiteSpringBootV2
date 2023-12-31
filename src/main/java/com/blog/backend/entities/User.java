package com.blog.backend.entities;

import com.blog.backend.Serializers.CommentSerializer;
import com.blog.backend.Serializers.PostSerializer;
import com.blog.backend.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 50)
    @JsonProperty(value = "name")
    private String firstName;

    @Column(name = "last_name", length = 50)
    @JsonIgnore
    private String lastName;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @Column(name = "image")
    @JsonProperty(value = "image")
    private String pic;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "bio", length = 100)
    private String bio;

    @Column(name = "facebook", length = 100)
    @JsonProperty(value = "facebookUrl")
    private String facebook;


    @Column(name = "roles", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @JsonProperty("roles")
    private Role roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @JsonSerialize(using = CommentSerializer.class)
    @JsonIgnore
    private Set<Comment> comments = new LinkedHashSet<>();


    @OneToMany(mappedBy = "user")
    @JsonSerialize(using = PostSerializer.class)
    @JsonIgnore
    private Set<Post> posts = new LinkedHashSet<>();


    @OneToMany(mappedBy = "userID1")
    @JsonManagedReference
    @JsonIgnore
    private Set<Friendship> friendships1;

    @OneToMany(mappedBy = "userID2")
    @JsonManagedReference
    @JsonIgnore
    private Set<Friendship> friendships2;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.getAuthorities();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;

    }
}
