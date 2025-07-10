package com.mopix.Mopix.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "creator_dashboard_day")
public class CreatorDashboardForDayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity userEntity;

    private Long presentDayViews;
    private Long lastDayViews;

    private Long presentDayLike;
    private Long lastDayLike;

    private Double nftSale;

    private Long numberOfComments;
    private Long numberOfShares;

    private Double followerGrowthPercentage;
}
