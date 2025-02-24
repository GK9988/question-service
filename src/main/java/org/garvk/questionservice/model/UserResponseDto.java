package org.garvk.questionservice.model;

public class UserResponseDto {
    private int qId;
    private String qAnswer;

    public UserResponseDto(){}

    public UserResponseDto(int qId, String qAnswer) {
        this.qId = qId;
        this.qAnswer = qAnswer;
    }

    public String getqAnswer() {
        return qAnswer;
    }

    public void setqAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }
}
