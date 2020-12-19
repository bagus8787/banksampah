package com.sahitya.banksampahsahitya.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.FileUtils;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class EditProfilActivity extends AppCompatActivity {

    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;

    EditText nama, nope, email, address, password, pass_confirm;
    Spinner sex, rt;

    String avatar_location, avatar_type;

    Button btn_update;

    ImageView img_profil, btn_img_profil;

    Bitmap b;

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    private static final String TYPE_1 = "multipart";

    Uri uri;
    File file;

    Context mContext;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profil");
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mContext = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        nama = findViewById(R.id.it_nama_p);
        nope = findViewById(R.id.it_nope_p);
        email = findViewById(R.id.it_email_p);
        sex = findViewById(R.id.it_sex_p);
        address = findViewById(R.id.it_address_p);
        password = findViewById(R.id.it_password_p);
        pass_confirm = findViewById(R.id.it_password_confirm_p);
        rt = findViewById(R.id.it_rt_p);

        img_profil = findViewById(R.id.img_profil_user);

        btn_img_profil = findViewById(R.id.btn_img_profil);
        btn_img_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        nama.setHint(sharedPrefManager.getSPNama());
        nope.setHint(sharedPrefManager.getSPMobile());
        email.setHint(sharedPrefManager.getSPEmail());

        avatar_location = sharedPrefManager.getSPAvatar();
        Log.d("url_foto", avatar_location);
        String url_photo ;
        url_photo = sharedPrefManager.getSPAvatar();

        Picasso.get().load(url_photo)
                .resize(150, 150)
                .centerCrop()
                .error(R.drawable.ic_baseline_account_circle)
                .into(img_profil);

        boolean hasWarga = sharedPrefManager.hasWarga();

        if (hasWarga) {
            address.setHint(sharedPrefManager.getSpAddress());
            sex.setSelection(getIndex(sex, sharedPrefManager.getSpSex()));
            rt.setSelection(getIndex(rt, sharedPrefManager.getSpRt()));
        } else {
            rt.setVisibility(View.GONE);
            sex.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            findViewById(R.id.lbl_it_gender).setVisibility(View.GONE);
            findViewById(R.id.lbl_it_rt).setVisibility(View.GONE);
            findViewById(R.id.lbl_it_address).setVisibility(View.GONE);
        }

//      Update profile
        btn_update = findViewById(R.id.btn_edit_profile);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                MultipartBody.Part filename = null;

                if (uri != null) {
                    file = new File(uri.getPath());
                    try {
                        file = FileUtils.from(EditProfilActivity.this, uri);
                        Log.d("file", "File...:::: uti - " + file.getPath() + " file -" + file + " : " + file.exists());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MediaType media;
                    if (uri.toString().startsWith("content://")) {
                        media = MediaType.parse(getContentResolver().getType(uri));
                    } else {
                        media = MediaType.parse(uri.toString());
                    }
                    RequestBody requestFile =
                            RequestBody.create(
                                    media,
                                    file
                            );

                    filename = MultipartBody.Part.createFormData("avatar_location", file.getName(), requestFile);
                }

                RequestBody femail = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
                RequestBody fnama = RequestBody.create(MediaType.parse("text/plain"), nama.getText().toString());
                RequestBody fsex = RequestBody.create(MediaType.parse("text/plain"), sex.getSelectedItem().toString());
                RequestBody fnope = RequestBody.create(MediaType.parse("text/plain"), nope.getText().toString());
                RequestBody frt = RequestBody.create(MediaType.parse("text/plain"), rt.getSelectedItem().toString());
                RequestBody faddress = RequestBody.create(MediaType.parse("text/plain"), address.getText().toString());
                RequestBody fpassword = RequestBody.create(MediaType.parse("text/plain"), password.getText().toString());
                RequestBody fpass_confirm = RequestBody.create(MediaType.parse("text/plain"), pass_confirm.getText().toString());

                Call<BaseResponse> updateUser = apiInterface.updateUser(
                        sharedPrefManager.getSPToken(),
                        femail,
                        fnama,
                        fsex,
                        fnope,
                        frt,
                        faddress,
                        filename,
                        fpassword,
                        fpass_confirm
                );

                Log.d("Log_update", updateUser.toString());
                updateUser.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressDialog.dismiss();
                        if (response.code() >= 200 && response.code() < 300) {
                            String message = response.body().getMessage();
                            Log.d("pesannya", String.valueOf(response.code()));
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                            sharedPrefManager.setSpAddress(address.getText().toString());
                            sharedPrefManager.setSpEmail(email.getText().toString());
                            sharedPrefManager.setSpNama(nama.getText().toString());
                            sharedPrefManager.setSpMobile(nope.getText().toString());
                            sharedPrefManager.setSpSex(sex.getSelectedItem().toString());
                            sharedPrefManager.setSpRt(rt.getSelectedItem().toString());
                            finish();

                        } else {
                            try {
                                Gson gson = new Gson();
                                BaseResponse errorResponse = gson.fromJson(
                                        response.errorBody().string(),
                                        BaseResponse.class);

                                for (Map.Entry<String, ArrayList<String>> entry : errorResponse.getErrors().entrySet()) {
                                    String key = entry.getKey();
                                    String value = entry.getValue().get(0);
                                    Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);

        }else{
            openGallery();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if(data != null) {
                uri = data.getData();
                img_profil.setImageURI(uri);
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                ((ImageView) findViewById(R.id.img_profil_user)).setImageURI(uri);


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "cropped" , "cropped");

                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Log.d("result", String.valueOf(uri));

                Toast.makeText(this, "Gambar disimpan", Toast.LENGTH_LONG).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Gambar gagal di crop: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (uri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(uri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .setMultiTouchEnabled(true)
                .start(this);

        Log.d("image_uri", String.valueOf(imageUri));

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}