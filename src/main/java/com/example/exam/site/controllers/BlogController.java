package com.example.exam.site.controllers;

import com.example.exam.site.models.Blog;
import com.example.exam.site.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/blog")
    public String blogMain(Model model,
                           @PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        model.addAttribute("blogsPage", blogService.getProductsPaginated(page));
        return "page-blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "page-blogAdd";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam("image") MultipartFile multipartFile, Model model, Blog blog) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        blog.setPhotos(fileName);
        blogService.saveBlog(blog);
        String uploadDir = "./blog-photos/" + blog.getId();
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = multipartFile.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return "redirect:/blog";
    }



}
