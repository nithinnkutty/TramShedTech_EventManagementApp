package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedbackMapper {
    List<Feedback> getFeedbackByEventId(@Param("eventId") Long eventId);
    boolean addFeedback(Feedback feedback);
}
