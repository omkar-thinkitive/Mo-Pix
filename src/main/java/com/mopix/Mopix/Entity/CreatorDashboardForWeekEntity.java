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
@Table(name = "creator_dashboard_week")
public class CreatorDashboardForWeekEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity userEntity;

    private Long presentWeekViews;
    private Long lastWeekViews;

    private Long presentWeekLike;
    private Long lastWeekLike;

    private Double nftSale;

    private Long numberOfComments;
    private Long numberOfShares;

    private Double followerGrowthPercentage;
}
