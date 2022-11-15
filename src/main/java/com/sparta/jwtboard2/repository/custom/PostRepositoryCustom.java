package com.sparta.jwtboard2.repository.custom;

import java.util.List;

import com.sparta.jwtboard2.entity.Post;

public interface PostRepositoryCustom {
	List<Post> findByKeyword(String keyword);
}
