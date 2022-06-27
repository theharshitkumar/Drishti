package com.example.Drishti;

import android.content.Context;
import android.net.Uri;
//import android.os.FileUtils;
import android.util.Log;
//import com.example.cameraxexample.FileUtils.getFile;
import android.speech.tts.TextToSpeech;


import java.io.File;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import retrofit2.http.body;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class retrofit2 {
    public String retropath;
    private Context context;

    public retrofit2(Context context) {
        this.context = context;
    }


    public void func_retrofit2(Uri filepath, Integer urlcode) {
        retropath = filepath.toString();

        Log.d("here in retropath", retropath);
        Log.d("urlcode",urlcode.toString());
        uploadFile(filepath,urlcode);
        //Toast.makeText(getActivity() ,msg, Toast.LENGTH_SHORT).show();
    }

    private void uploadFile(Uri fileUri, Integer urlcode) {
        //RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, "description");

        File originalFile = FileUtils.getFile(this.context.getApplicationContext(), fileUri);
        //Log.d("originalfile",originalFile.toString());

        //ContentResolver result = (ContentResolver) GlobalClass.context.getContentResolver();
        RequestBody filepart = RequestBody.create(
                MediaType.parse("image/jpeg"), originalFile);
        //Log.d("filepart",filepart.toString());

        MultipartBody.Part file = MultipartBody.Part.createFormData("file", originalFile.getName(), filepart);
        //Log.d("file",file.toString());

        //create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.10.45:8000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // get client & call object for the request
        UserClient client = retrofit.create(UserClient.class);

        // finally , execute the request
        Call<ServerResponse> call = null;
        if (urlcode == 0){
            call = client.uploadPhoto_currency(file);
            Log.d("Uploading","currency");
        }

        if (urlcode == 1){
            call = client.uploadPhoto_general(file);
            Log.d("Uploading","general object");
        }
        //Log.d("response",call.toString());

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    response.body(); // have your all data
                    //here
                    //String predict_result = response.body().getResult();
                    if(response.body().getHighAccuracy() != ""){
                        Log.d("high accuracy","not empty");
                        String highAccuracy = response.body().getHighAccuracy();
                        highconfi(highAccuracy);
                    }

                    //if(response.body().getLowAccuracy() != ""){
                     //   Log.d("low accuracy","empty");
                      //  String lowAccuracy = response.body().getLowAccuracy();
                       // lowconfi(lowAccuracy);
                    //}

                    if(response.body().getHighAccuracy() == ""){
                        Log.d("high accuracy","empty");
                        speak("I      cannot      detect      anything");
                    }

                    //if(response.body().getLowAccuracy() != ""){
                      //  Log.d("low accuracy","empty");
                       // String lowAccuracy = response.body().getLowAccuracy();
                       // lowconfi(lowAccuracy);
                    //}


                    //lowconfi(lowAccuracy);

                    //here end
                    //Log.d("all class",allclass);
                    //Log.d("result", response.body().getResult());
                    //Log.d("body", response.body().toString());
                } else Log.d("error", String.valueOf(response.errorBody()));

                //Toast.makeText(this, "Yeah!", Toast.LENGTH_SHORT).show();
                Log.d("successful", String.valueOf(response.isSuccessful()));
                if (response.body() != null) {
                    Log.d("body", response.body().toString());
                }
                //Log.d("File uploaded","Yes");
                //Log.d("call",call.toString());
                //Log.d("response",response.toString());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                //Toast.makeText(this, "NOoo!", Toast.LENGTH_SHORT).show();
                Log.d("File uploaded", "no");
                String filenotuploaded = "Cannot      connect      to      the      server.      Please      check      connection.";
                speak(filenotuploaded);
                //Log.d("call", call.toString());
                //Log.d("response", t.toString());
            }
        });
    }
    TextToSpeech text_to_speech; // declare tts here

    public void highconfi(final String text){
        String[] arrOfStr_highaccuracy = text.split(",", 0);
        String allclass ="I      have      detected      ";
        for (String a : arrOfStr_highaccuracy) {
            Log.d("split", a);
            allclass = allclass + a+"      ";
        }
        allclass = allclass + "   with      70%      or      above      accuracy";
        Log.d("allclass",allclass);
        speak(allclass);
    }

    public void lowconfi(final String text){
        String[] arrOfStr_lowaccuracy = text.split(",", 0);
        String allclass ="      and      I      have      also      detected      ";
        for (String a : arrOfStr_lowaccuracy) {
            Log.d("split", a);
            allclass = allclass + a+"      ";
        }
        allclass = allclass + "   but      with      less      then      70%      accuracy";
        Log.d("allclass",allclass);
        speak(allclass);
    }



    public void speak(final String text) { // make text 'final'
        // ... do not declare tts here
        text_to_speech = new TextToSpeech(this.context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = text_to_speech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        text_to_speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    }
                } else {
                    Log.e("TTS", "Failed");
                }
            }
        });


    }
}


