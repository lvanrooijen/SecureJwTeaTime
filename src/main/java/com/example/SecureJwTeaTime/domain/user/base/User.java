package com.example.SecureJwTeaTime.domain.user.base;

import com.example.SecureJwTeaTime.security.refreshtoken.RefreshToken;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Represents users of this app */
@Entity
@Table(name = "users")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements UserDetails {

  public User(
      String email,
      String password,
      Role role,
      LocalDate createdOn,
      boolean isActivated,
      RefreshToken refreshToken) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.createdOn = createdOn;
    this.isActivated = isActivated;
    this.refreshToken = refreshToken;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Setter
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Setter
  @Column(name = "password", nullable = false)
  private String password;

  @Setter
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Setter
  @Column(name = "created_on", nullable = false)
  private LocalDate createdOn;

  @Setter
  @Column(name = "activated", nullable = false)
  private boolean isActivated;

  @Setter
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private RefreshToken refreshToken;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.toString()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  public String getRole() {
    return this.role.toString();
  }

  public Boolean isAdmin() {
    return false;
  }

  public String getAccountName() {
    return this.email;
  }

  public Boolean isSameUSer(User user) {
    return (this.id.equals(user.getId()));
  }
}
