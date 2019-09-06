package jeonghoj.boardproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class User implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Boolean enabled;
    @Column
    private LocalDateTime createdDate;
    @Column
    @Setter
    private String authority;

    @Builder
    public User(String username, String password, LocalDateTime createdDate) {
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
        this.enabled = true;
        this.authority = "ROLE_USER";
    }
}
