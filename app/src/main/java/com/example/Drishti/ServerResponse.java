package com.example.Drishti;



//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ServerResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("high_accuracy")
    @Expose
    private String highAccuracy;
    @SerializedName("low_accuracy")
    @Expose
    private String lowAccuracy;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHighAccuracy() {
        return highAccuracy;
    }

    public void setHighAccuracy(String highAccuracy) {
        this.highAccuracy = highAccuracy;
    }

    public String getLowAccuracy() {
        return lowAccuracy;
    }

    public void setLowAccuracy(String lowAccuracy) {
        this.lowAccuracy = lowAccuracy;
    }

}