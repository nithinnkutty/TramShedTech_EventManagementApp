package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.Feedback;
import com.tramshedtech.eventmanagement.mapper.FeedbackMapper;
import com.tramshedtech.eventmanagement.service.FeedbackService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public List<Feedback> getFeedbackByEventId(Long eventId) {
        return feedbackMapper.findByEventId(eventId);
    }

    @Override
    public boolean addFeedback(Feedback feedback) {
        return feedbackMapper.addFeedback(feedback);
    }
}
