package com.example.demo.repository;

import com.example.demo.domain.Researcher;
import com.example.demo.domain.sub.Strength;
import com.example.demo.utils.dto.ResearcherSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.demo.domain.QResearcher.researcher;
import static com.querydsl.core.types.dsl.Expressions.TRUE;

@RequiredArgsConstructor
@Repository
public class ResearcherQueryDslRepository {

    private final JPAQueryFactory queryFactory;


    public List<Researcher> search(ResearcherSearchDto dto) {
        return queryFactory
                .select(researcher)
                .from(researcher)
                .where(nameContains(dto.getQuery())
                        .and(capacityContains(dto.getQuery()))
                        .and(strengthEq(dto.getStrength()))
                        .and(researcher.isRewarded.eq(dto.isRewarded())))
//                        .and(researcher.forCompany.eq(dto.isForCompany()))
//                        .and(researcher.forStudent.eq(dto.isForStudent())))
                .fetch();
    }

    private BooleanExpression nameContains(String name) {
        return !StringUtils.hasText(name) ? TRUE : researcher.name.contains(name);
    }

    private BooleanExpression capacityContains(String capacity) {
        return !StringUtils.hasText(capacity) ? TRUE : researcher.capacity.contains(capacity);
    }

    private BooleanExpression strengthEq(Strength strength) {
        return researcher.strength.eq(strength);
    }
}
