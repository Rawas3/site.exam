package com.example.exam.site.services;

import com.example.exam.site.models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    void saveBlog(Blog blog);

    Page<Blog> getProductsPaginated(Pageable page);

    Blog getBlog(Long id);

    void deleteBlog(Long id);
}
