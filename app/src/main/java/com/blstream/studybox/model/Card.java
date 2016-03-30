package com.blstream.studybox.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Card implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionNo);
        dest.writeString(question);
        dest.writeString(prompt);
        dest.writeString(answer);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    private Card(Parcel source) {
        questionNo = source.readInt();
        question = source.readString();
        prompt = source.readString();
        answer = source.readString();
    }
}
