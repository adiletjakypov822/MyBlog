package com.example.adiletblog.Repositories;

import com.example.adiletblog.Models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
