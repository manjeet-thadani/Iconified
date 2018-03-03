package com.genius.iconified.classifier;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.genius.iconified.MainActivity;
import com.genius.iconified.R;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by manjeet on 3/3/18.
 */

public class CaptureImageActivity extends AppCompatActivity{

    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";

    private static final String MODEL_FILE = "file:///android_asset/graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/labels.txt";

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();

    private CameraView cameraView;
    private FloatingActionButton captureImageFAB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        cameraView = findViewById(R.id.activity_capture_image_cameraview);
        captureImageFAB = findViewById(R.id.activity_capture_image_capture_image_fab);

        captureImageFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.captureImage();
            }
        });


        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                byte[] picture = cameraKitImage.getJpeg();

                //Toast.makeText(CaptureImageActivity.this, "Captured", Toast.LENGTH_SHORT).show();

                Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
                //Toast.makeText(getApplicationContext(), results.toString(), Toast.LENGTH_SHORT).show();

                List<ClassifierResult> resultList = new ArrayList<>();
                for (Classifier.Recognition result : results){
                    ClassifierResult classifierResult = new ClassifierResult();
                    classifierResult.setIconName(result.getTitle());
                    classifierResult.setIconConfidenceLevel(result.getConfidence());

                    resultList.add(classifierResult);
                }
                ClassifierResultHolder holder = new ClassifierResultHolder();
                holder.setResults(resultList);

                Bundle bundle = new Bundle();
                bundle.putSerializable("holder", holder);

                Intent intent = new Intent(getApplicationContext(), ClassifierResultsActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        captureImageFAB.setVisibility(View.GONE);
        initTensorFlowAndLoadModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        try {
            classifier = TensorFlowImageClassifier.create(
                    getAssets(),
                    MODEL_FILE,
                    LABEL_FILE,
                    INPUT_SIZE,
                    IMAGE_MEAN,
                    IMAGE_STD,
                    INPUT_NAME,
                    OUTPUT_NAME);
            makeButtonVisible();
        } catch (final Exception e) {
            throw new RuntimeException("Error initializing TensorFlow!", e);
        }
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                captureImageFAB.setVisibility(View.VISIBLE);
            }
        });
    }
}
