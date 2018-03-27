package com.zmsoft.ccd.lib.utils.tts;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;


/**
 * @author DangGui
 * @create 2017/10/31.
 */


public class OfflineResource {
    public static final String VOICE_FEMALE = "F";
    public static final String VOICE_MALE = "M";
    private static final String SAMPLE_DIR = "baiduTTS";

    private AssetManager assets;
    private String destPath;
    private String textFilename;
    private String modelFilename;
    private Context mContext;

    public OfflineResource(Context context, String voiceType) throws IOException {
        context = context.getApplicationContext();
        this.mContext = context;
        this.assets = context.getApplicationContext().getAssets();
        this.destPath = TtsFileUtil.createTmpDir(context);
        setOfflineVoiceType(voiceType);
    }

    public String getModelFilename() {
        return modelFilename;
    }

    public String getTextFilename() {
        return textFilename;
    }

    public void setOfflineVoiceType(String voiceType) throws IOException {
        String text = "bd_etts_text.dat";
        String model;
        if (VOICE_MALE.equals(voiceType)) {
            model = "bd_etts_speech_male.dat";
        } else if (VOICE_FEMALE.equals(voiceType)) {
            model = "bd_etts_speech_female.dat";
        } else {
            throw new RuntimeException("voice type is not in list");
        }
        textFilename = copyAssetsFile(text);
        modelFilename = copyAssetsFile(model);

    }


    private String copyAssetsFile(String sourceFilename) throws IOException {
        String destFilename = destPath + "/" + sourceFilename;
        TtsFileUtil.copyFromAssets(mContext, assets, sourceFilename, destFilename, false);
        return destFilename;
    }


}
