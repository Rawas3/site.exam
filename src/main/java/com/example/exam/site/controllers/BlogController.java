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
import org.springframework.web.bind.annotation.*;
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
                           @PageableDefault(size = 4, sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
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

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("blog", blogService.getBlog(id));
        return "page-blogDetails";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("blog", blogService.getBlog(id));
        return "page-blogEdit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(Model model, Blog blog) {
        blogService.saveBlog(blog);
        return "redirect:/blog";
    }

    @RequestMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        blogService.deleteBlog(id);
        return "redirect:/blog";
    }


}
