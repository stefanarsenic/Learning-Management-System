package org.example.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Collate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String jmbg;
    private String ime;
    private String prezime;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String biografija;
    @ManyToOne
    private RegisteredUser user;
}
