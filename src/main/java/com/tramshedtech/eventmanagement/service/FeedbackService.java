package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getFeedbackByEventId(Long eventId);
    boolean addFeedback(Feedback feedback);
}
