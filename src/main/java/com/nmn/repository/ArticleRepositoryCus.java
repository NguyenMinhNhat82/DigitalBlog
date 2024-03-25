package com.nmn.repository;


import com.nmn.model.Articles;
import com.nmn.model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ArticleRepositoryCus {

    @Autowired
    EntityManager em;
    public List<Articles>getListArticles(Map<String, String> params){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Articles> q = cb.createQuery(Articles.class);
        Root<Articles> userRoot = q.from(Articles.class);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String username = params.get("username");
            if (username != null && !username.isEmpty()) {
                predicates.add(cb.like(userRoot.get("username"), String.format("%%%s%%", username)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = em.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int pageSize = Integer.parseInt("2");
                query.setFirstResult((Integer.parseInt(page) - 1) * pageSize);
                query.setMaxResults(pageSize);
            }
        }
        return query.getResultList();
    }
}
