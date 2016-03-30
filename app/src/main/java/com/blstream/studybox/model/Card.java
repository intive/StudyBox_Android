
package com.blstream.studybox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Card {

    @SerializedName("questionNo")
    @Expose
    private Integer questionNo;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("answer")
    @Expose
    private String answer;

    // TODO: delete, its only for time when change model
    public Card(String que, String prmt, String ans) {
            question = que;
            answer = ans;
            prompt = prmt;
    }

    /**
     * 
     * @return
     *     The questionNo
     */
    public Integer getQuestionNo() {
        return questionNo;
    }

    /**
     * 
     * @param questionNo
     *     The questionNo
     */
    public void setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }

    /**
     * 
     * @return
     *     The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 
     * @param question
     *     The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 
     * @return
     *     The prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * 
     * @param prompt
     *     The prompt
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * 
     * @return
     *     The answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 
     * @param answer
     *     The answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
