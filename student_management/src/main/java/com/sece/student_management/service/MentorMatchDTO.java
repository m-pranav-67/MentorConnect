package com.sece.student_management.service;

import com.sece.student_management.entity.Alumni;

public class MentorMatchDTO {
    private Alumni mentor;
    private long sharedTagsCount;

    public MentorMatchDTO(Alumni mentor, long sharedTagsCount) {
        this.mentor = mentor;
        this.sharedTagsCount = sharedTagsCount;
    }
    
    public Alumni getMentor() { return mentor; }
    public long getSharedTagsCount() { return sharedTagsCount; }
}
