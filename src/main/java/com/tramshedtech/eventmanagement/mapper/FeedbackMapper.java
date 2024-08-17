package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedbackMapper {
    boolean addFeedback(Feedback feedback);
    @Select("SELECT * FROM feedback WHERE event_id = #{eventId}")
    List<Feedback> findByEventId(Long eventId);
}
