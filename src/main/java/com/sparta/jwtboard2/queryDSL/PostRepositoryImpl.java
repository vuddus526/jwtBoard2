package com.sparta.jwtboard2.queryDSL;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.jwtboard2.entity.Post;
import com.sparta.jwtboard2.entity.QPost;
import com.sparta.jwtboard2.entity.QUser;
import com.sparta.jwtboard2.repository.custom.PostRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Post> findByKeyword(String keyword) {
		QPost post = QPost.post;
		QUser user = QUser.user;

		List<Post> posts = jpaQueryFactory.selectFrom(post)

			.leftJoin(post.user, user).fetchJoin()
			// where == 이 기준으로 조건을 주겠다
			// contains == 앞뒤 구분없이, 1개의 문자만 똑같아도 포함된 것으로 간주하는 메서드
			.where(post.contents.contains(keyword))
			// orderBy == 이 기준으로 정렬하겠다
			// .orderBy(post.modifiedAt.desc())
			// fetch == 기존에 조인된 것을 다시 해제하는 역할 + querydsl 값을 반환해주는 역할
			.fetch();

		return posts;
	}
}
