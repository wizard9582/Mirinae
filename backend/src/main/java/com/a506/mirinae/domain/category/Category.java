// 2021.09.13 HanQ 신한규
package com.a506.mirinae.domain.category;

import com.a506.mirinae.domain.funding.Funding;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Funding> fundings = new ArrayList<>();
}
