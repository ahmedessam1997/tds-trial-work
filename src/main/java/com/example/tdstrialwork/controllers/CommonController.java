package com.example.tdstrialwork.controllers;

import com.example.tdstrialwork.helpers.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


@RequestMapping("/api/v1")
public abstract class CommonController {

    protected Pageable paging(Integer offset, Integer limit, Sort sort) {
        var pageSize = limit == null ? Constants.DEFAULT_PAGE_SIZE : limit;
        var page = (offset == null ? 0 : offset) / pageSize;
        return PageRequest.of(page, pageSize, sort);
    }

    protected Pageable paging(final Integer offset, final Integer limit) {
        return paging(offset, limit, Sort.by("id"));
    }

    protected Sort sortBy(final String expr) {
        if (expr == null || expr.isBlank()) {
            return Sort.by("id");
        }

        final var direction = expr.startsWith(">") ? Sort.Direction.DESC : Sort.Direction.ASC;
        final var property = expr.replaceAll("^>|^<", "");

        return Sort.by(direction, property);
    }

    protected <T> Specification<T> specificationFrom(final String query) {
        return RSQLJPASupport.toSpecification(query, true);
    }
}
