package com.mopix.Mopix.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_post")
public class ReportPostEntity extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID reportUUID;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private UserEntity reportedUser;

    @ManyToOne
    private PostEntity postEntity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    private boolean isInformUSer;
    private boolean isTakenAction;
    private boolean isResolve;

    private boolean isDelete;

}
