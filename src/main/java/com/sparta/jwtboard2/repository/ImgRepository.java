package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgRepository extends JpaRepository<Img, Long> {
    List<Img> findAllByPostId(Long id);
}
