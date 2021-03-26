package com.example.adiletblog.Controllers;


import com.example.adiletblog.Models.Post;
import com.example.adiletblog.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogNewAdd(@RequestParam String titleBlog, @RequestParam String url,
                             @RequestParam String fullTextBlog, Model model){
        Post post = new Post(titleBlog,url,fullTextBlog);
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> blog = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        blog.ifPresent(res::add);
        model.addAttribute("blog",res);
        return "blog-detail";
    }

    @GetMapping("/blog/{id}/update")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-update";
    }

    @PostMapping("/blog/{id}/update")
    public  String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String url, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setUrl(url);
        post.setFullText(full_text);
        postRepository.save(post);
        return  "redirect:/";
    }

    @PostMapping("/blog/{id}/delete")
    public String blogDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }
}

