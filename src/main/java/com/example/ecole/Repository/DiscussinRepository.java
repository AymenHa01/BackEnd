package com.example.ecole.Repository;

import com.example.ecole.Entities.Messages.Discussion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiscussinRepository extends JpaRepository<Discussion , Integer> {
    @Query("SELECT u FROM Discussion u WHERE u.codeDiscussion LIKE %:email% AND u.type= :type")
    List<Discussion> findByDisscussionContaining(@Param("email") String email , @Param("type") String type);


    Optional<Discussion>  findByCodeDiscussion(String code);

    @Modifying
    @Transactional
    @Query("UPDATE Discussion d SET d.Discussionvu = :newDiscussionVu WHERE d.id = :discussionId")
    void updateDiscussionVu(int discussionId, boolean newDiscussionVu);




}
