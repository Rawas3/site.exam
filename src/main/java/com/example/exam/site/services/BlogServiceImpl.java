package com.example.exam.site.services;

import com.example.exam.site.exceptions.BlogNotFoundException;
import com.example.exam.site.models.Blog;
import com.example.exam.site.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService{

    private final BlogRepository blogRepository;

    @Override
    public void saveBlog(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public Page<Blog> getProductsPaginated(Pageable page) {
        return blogRepository.findAll(page);
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(BlogNotFoundException::new);
    }

    @Override
    public void deleteBlog (Long id) {
        blogRepository.deleteById(id);
    }

}
