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
        Root<Articles> articlesRoot = q.from(Articles.class);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String title = params.get("title");
            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(articlesRoot.get("title"), String.format("%%%s%%", title)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = em.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int pageSize = 5;
                String pageSizeParam = params.get("pageSize");
                    if(pageSizeParam != null && !pageSizeParam.isEmpty()){
                         Integer.parseInt(pageSizeParam);
                    }
                query.setFirstResult((Integer.parseInt(page) - 1) * pageSize);
                query.setMaxResults(pageSize);

            }
        }
        return query.getResultList();
    }
}
