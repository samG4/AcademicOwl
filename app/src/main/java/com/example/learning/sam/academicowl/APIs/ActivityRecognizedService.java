package com.example.learning.sam.academicowl.APIs;

/**
 * Created by Sam on 12/3/2017.
 */
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;

public class ActivityRecognizedService extends IntentService {

    private static ActivityRecognizedService activity_instance = null;

    public ActivityRecognizedService(){
        super("ActivityRecognizedService");
    }
    public static ActivityRecognizedService getInstance()
    {
        if (activity_instance == null)
            activity_instance = new ActivityRecognizedService();

        return activity_instance;
    }


    public void setActivity(String activity) {
        this.activity = activity;
    }

    private String activity;

    public String getActivity() {
        return activity;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)){
            ActivityRecognitionResult result=ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities){
        for(DetectedActivity activity:probableActivities){
            switch (activity.getType()){
                case DetectedActivity.IN_VEHICLE:{
                    Log.e("ActivityRecognition","In Vehicle: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("IN_VEHICLE");
//                    UserPatternWrtTime.activity =this.activity;
                    break;
                }
                case DetectedActivity.ON_BICYCLE:{
                    Log.e("ActivityRecognition","On Bicycle: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("ON_BICYCLE");
//                    UserPatternWrtTime.activity =this.activity;
                    break;
                }
                case DetectedActivity.ON_FOOT:{
                    Log.e("ActivityRecognition","On Foot: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("ON_FOOT");
//                    UserPatternWrtTime.activity =this.activity;
                    break;
                }
                case DetectedActivity.RUNNING:{
                    Log.e("ActivityRecognition","Running: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("RUNNING");
//                    UserPatternWrtTime.activity =this.activity;
                    break;
                }
                case DetectedActivity.STILL:{
                    Log.e("ActivityRecognition","Still: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("STILL");
//                    UserPatternWrtTime.activity =this.activity;
                    break;
                }
                case DetectedActivity.TILTING:{
                    Log.e("ActivityRecognition","Tilting: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("TILTING");
//                    UserPatternWrtTime.activity =this.activity;
                    break;
                }
                case DetectedActivity.WALKING:{
                    Log.e("ActivityRecognition","Walking: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("WALKING");
//                    UserPatternWrtTime.activity =this.activity;
//                    if(activity.getConfidence()>=75){
//                        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
//                        builder.setContentText("Are you walking?");
//                        builder.setSmallIcon(R.mipmap.ic_launcher);
//                        builder.setContentTitle(getString(R.string.app_name));
//                        NotificationManagerCompat.from(this).notify(0,builder.build());
//                    }
                    break;
                }
                case DetectedActivity.UNKNOWN:{
                    Log.e("ActivityRecognition","Unknown: "+activity.getConfidence());
                    new ActivityRecognizedService().getInstance().setActivity("UNKNOWN");
//                    UserPatternWrtTime.activity =this.activity;
                    break;
                }

            }
        }
    }
}
