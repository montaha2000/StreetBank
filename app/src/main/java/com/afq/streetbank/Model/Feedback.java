package com.afq.streetbank.Model;

public class Feedback {
    String displayName;
    String feedback;

    public Feedback() {
    }

    public Feedback(String displayName, String feedback) {
        this.displayName = displayName;
        this.feedback = feedback;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
