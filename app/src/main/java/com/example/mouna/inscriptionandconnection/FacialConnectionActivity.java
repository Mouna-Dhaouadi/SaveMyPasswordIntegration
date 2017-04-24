package com.example.mouna.inscriptionandconnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mouna.inscriptionandconnection.helper.ImageHelper;
import com.example.mouna.inscriptionandconnection.helper.LogHelper;
import com.example.mouna.inscriptionandconnection.helper.SampleApp;
import com.example.mouna.inscriptionandconnection.helper.StorageHelper;
import com.example.mouna.inscriptionandconnection.myapplication.ListEnregistrements;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.TrainingStatus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.example.mouna.inscriptionandconnection.myapplication.ListEnregistrements;


public class FacialConnectionActivity extends AppCompatActivity {


    // Background task of face identification.
    private class IdentificationTask extends AsyncTask<UUID, String, IdentifyResult[]> {
        private boolean mSucceed = true;
        String mPersonGroupId;
        IdentificationTask(String personGroupId) {
            this.mPersonGroupId = personGroupId;
        }

        @Override
        protected IdentifyResult[] doInBackground(UUID... params) {
            String logString = "Request: Identifying faces ";
            for (UUID faceId: params) {
                logString += faceId.toString() + ", ";
            }
            logString += " in group " + mPersonGroupId;
            addLog(logString);

            // Get an instance of face service client to detect faces in image.
            FaceServiceClient faceServiceClient = SampleApp.getFaceServiceClient();
            try{
                publishProgress("Getting person group status...");

                TrainingStatus trainingStatus = faceServiceClient.getPersonGroupTrainingStatus(
                        this.mPersonGroupId);     /* personGroupId */

                if (trainingStatus.status != TrainingStatus.Status.Succeeded) {
                    publishProgress("Person group training status is " + trainingStatus.status);
                    mSucceed = false;
                    return null;
                }

                publishProgress("Identifying...");

                // Start identification.
                return faceServiceClient.identity(
                        this.mPersonGroupId,   /* personGroupId */
                        params,                  /* faceIds */
                        1);  /* maxNumOfCandidatesReturned */
            }  catch (Exception e) {
                mSucceed = false;
                publishProgress(e.getMessage());
                addLog(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            setUiBeforeBackgroundTask();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // Show the status of background detection task on screen.a
            setUiDuringBackgroundTask(values[0]);
        }

        @Override
        protected void onPostExecute(IdentifyResult[] result) {
            // Show the result on screen when detection is done.
            setUiAfterIdentification(result, mSucceed);

            // Show the detailed list of detected faces.
            ListView listView = (ListView) findViewById(R.id.list_identified_faces);
            listView.setAdapter(mFaceListAdapter);

        }
    }

    String mPersonGroupId;

    boolean detected;

    FaceListAdapter mFaceListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_connection);
        List<String> personGroupIdList;

        TextView textView = (TextView) findViewById(R.id.text_person_group_selected);
        // Initialize with detection result.
            personGroupIdList = new ArrayList<>();

            Set<String> personGroupIds
                    = StorageHelper.getAllPersonGroupIds(FacialConnectionActivity.this);

            for (String personGroupId: personGroupIds) {
                personGroupIdList.add(personGroupId);
            }
        mPersonGroupId = personGroupIdList.get(0);
        String personGroupName = StorageHelper.getPersonGroupName(
                mPersonGroupId, FacialConnectionActivity.this);
        refreshIdentifyButtonEnabledStatus();
        textView.setTextColor(Color.BLACK);
        textView.setText(String.format("Person group to use: %s", personGroupName));
            detected = false;

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.progress_dialog_title));

        LogHelper.clearIdentificationLog();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private void setUiBeforeBackgroundTask() {
        progressDialog.show();
    }

    // Show the status of background detection task on screen.
    private void setUiDuringBackgroundTask(String progress) {
        progressDialog.setMessage(progress);

        setInfo(progress);
    }

    // Show the result on screen when detection is done.
    private void setUiAfterIdentification(IdentifyResult[] result, boolean succeed) {
        progressDialog.dismiss();

        setAllButtonsEnabledStatus(true);
        setIdentifyButtonEnabledStatus(false);


        if (succeed) {
            // Set the information about the detection result.
            setInfo("Identification is done");

            if (result != null) {
                mFaceListAdapter.setIdentificationResult(result);

                String logString = "Response: Success. ";
                for (IdentifyResult identifyResult: result) {
                    logString += "Face " + identifyResult.faceId.toString() + " is identified as "
                            + (identifyResult.candidates.size() > 0
                            ? identifyResult.candidates.get(0).personId.toString()
                            : "Unknown Person")
                            + ". ";
                    if(identifyResult.candidates.size() > 0){
                        if(result[0].candidates.get(0).confidence>0.6){
                            Button btConnect = (Button) findViewById(R.id.btConnect);
                            btConnect = (Button) findViewById(R.id.btConnect);
                            btConnect.setEnabled(true);
                            btConnect.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(FacialConnectionActivity.this, ListEnregistrements.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                    else {
                        Toast.makeText(FacialConnectionActivity.this, "non identification",Toast.LENGTH_LONG);
                    }
                }
                addLog(logString);

            }
        }

    }

    // Background task of face detection.
    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
        @Override
        protected Face[] doInBackground(InputStream... params) {
            // Get an instance of face service client to detect faces in image.
            FaceServiceClient faceServiceClient = SampleApp.getFaceServiceClient();
            try{
                publishProgress("Detecting...");

                // Start detection.
                return faceServiceClient.detect(
                        params[0],  /* Input stream of image to detect */
                        true,       /* Whether to return face ID */
                        false,       /* Whether to return face landmarks */
                        /* Which face attributes to analyze, currently we support:
                           age,gender,headPose,smile,facialHair */
                        null);
            }  catch (Exception e) {
                publishProgress(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            setUiBeforeBackgroundTask();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // Show the status of background detection task on screen.
            setUiDuringBackgroundTask(values[0]);
        }

        @Override
        protected void onPostExecute(Face[] result) {
            progressDialog.dismiss();

            setAllButtonsEnabledStatus(true);

            if (result != null) {
                // Set the adapter of the ListView which contains the details of detected faces.
                mFaceListAdapter = new FaceListAdapter(result);
                ListView listView = (ListView) findViewById(R.id.list_identified_faces);
                listView.setAdapter(mFaceListAdapter);

                if (result.length == 0) {
                    detected = false;
                    setInfo("No faces detected!");
                } else {
                    detected = true;
                    setInfo("Click on the \"Identify\" button to identify the faces in image.");
                }
            } else {
                detected = false;
            }

            refreshIdentifyButtonEnabledStatus();

        }
    }

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;

    // The image selected to detect.
    private Bitmap mBitmap;

    // Progress dialog popped up when communicating with server.
    ProgressDialog progressDialog;

    // Called when image selection is done.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUEST_SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    detected = false;

                    // If image is selected successfully, set the image URI and bitmap.
                    Uri imageUri = data.getData();
                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                            imageUri, getContentResolver());
                    if (mBitmap != null) {
                        // Show the image on screen.
                        ImageView imageView = (ImageView) findViewById(R.id.image);
                        imageView.setImageBitmap(mBitmap);
                    }

                    // Clear the identification result.
                    FaceListAdapter faceListAdapter = new FaceListAdapter(null);
                    ListView listView = (ListView) findViewById(R.id.list_identified_faces);
                    listView.setAdapter(faceListAdapter);

                    // Clear the information panel.
                    setInfo("");

                    // Start detecting in image.
                    detect(mBitmap);
                }
                break;
            default:
                break;
        }
    }

    // Start detecting in image.
    private void detect(Bitmap bitmap) {
        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        setAllButtonsEnabledStatus(false);
        Button btConnect= (Button) findViewById(R.id.btConnect) ;
        btConnect.setEnabled(false);

        // Start a background task to detect faces in the image.
        new DetectionTask().execute(inputStream);
    }

    // Called when the "Select Image" button is clicked.
    public void selectImage(View view) {
        Intent intent = new Intent(this, SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    // Called when the "Detect" button is clicked.
    public void identify(View view) {
        // Start detection task only if the image to detect is selected.
        if (detected && mPersonGroupId != null) {
            // Start a background task to identify faces in the image.
            List<UUID> faceIds = new ArrayList<>();
            for (Face face:  mFaceListAdapter.faces) {
                faceIds.add(face.faceId);
            }

            setAllButtonsEnabledStatus(false);

            Button btConnect= (Button) findViewById(R.id.btConnect) ;
            btConnect.setEnabled(false);

            new IdentificationTask(mPersonGroupId).execute(
                    faceIds.toArray(new UUID[faceIds.size()]));
        } else {
            // Not detected or person group exists.
            setInfo("Please select an image and create a person group first.");
        }

    }


    // Add a log item.
    private void addLog(String log) {
        LogHelper.addIdentificationLog(log);
    }

    // Set whether the buttons are enabled.
    private void setAllButtonsEnabledStatus(boolean isEnabled) {

        Button groupButton = (Button) findViewById(R.id.select_image);
        groupButton.setEnabled(isEnabled);

        Button identifyButton = (Button) findViewById(R.id.identify);
        identifyButton.setEnabled(isEnabled);


    }

    // Set the group button is enabled or not.
    private void setIdentifyButtonEnabledStatus(boolean isEnabled) {
        Button button = (Button) findViewById(R.id.identify);
        button.setEnabled(isEnabled);
    }

    // Set the group button is enabled or not.
    private void refreshIdentifyButtonEnabledStatus() {
        if (detected && mPersonGroupId != null) {
            setIdentifyButtonEnabledStatus(true);
        } else {
            setIdentifyButtonEnabledStatus(false);
        }
    }

    // Set the information panel on screen.
    private void setInfo(String info) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(info);
    }

    // The adapter of the GridView which contains the details of the detected faces.
    private class FaceListAdapter extends BaseAdapter {
        // The detected faces.
        List<Face> faces;

        List<IdentifyResult> mIdentifyResults;

        // The thumbnails of detected faces.
        List<Bitmap> faceThumbnails;

        // Initialize with detection result.
        FaceListAdapter(Face[] detectionResult) {
            faces = new ArrayList<>();
            faceThumbnails = new ArrayList<>();
            mIdentifyResults = new ArrayList<>();

            if (detectionResult != null) {
                faces = Arrays.asList(detectionResult);
                for (Face face: faces) {
                    try {
                        // Crop face thumbnail with five main landmarks drawn from original image.
                        faceThumbnails.add(ImageHelper.generateFaceThumbnail(
                                mBitmap, face.faceRectangle));
                    } catch (IOException e) {
                        // Show the exception when generating face thumbnail fails.
                        setInfo(e.getMessage());
                    }
                }
            }
        }

        public void setIdentificationResult(IdentifyResult[] identifyResults) {
            mIdentifyResults = Arrays.asList(identifyResults);
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public int getCount() {
            return faces.size();
        }

        @Override
        public Object getItem(int position) {
            return faces.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater =
                        (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(
                        R.layout.item_face_with_description, parent, false);
            }
            convertView.setId(position);

            // Show the face thumbnail.
            ((ImageView)convertView.findViewById(R.id.face_thumbnail)).setImageBitmap(
                    faceThumbnails.get(position));

            if (mIdentifyResults.size() == faces.size()) {
                // Show the face details.
                DecimalFormat formatter = new DecimalFormat("#0.00");
                if (mIdentifyResults.get(position).candidates.size() > 0) {
                    String personId =
                            mIdentifyResults.get(position).candidates.get(0).personId.toString();
                    String personName = StorageHelper.getPersonName(
                            personId, mPersonGroupId, FacialConnectionActivity.this);
                    String identity = "Person: " + personName + "\n"
                            + "Confidence: " + formatter.format(
                            mIdentifyResults.get(position).candidates.get(0).confidence);
                    ((TextView) convertView.findViewById(R.id.text_detected_face)).setText(
                            identity);
                } else {
                    ((TextView) convertView.findViewById(R.id.text_detected_face)).setText(
                            R.string.face_cannot_be_identified);
                }
            }

            return convertView;
        }
    }

}

