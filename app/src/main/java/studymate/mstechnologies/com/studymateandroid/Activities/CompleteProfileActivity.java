package studymate.mstechnologies.com.studymateandroid.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.valdesekamdem.library.mdtoast.MDToast;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.Requests;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class CompleteProfileActivity extends AppCompatActivity {

  //WIDGETS
  private Spinner departmentsSpinner,majorsSpinner;
  private CircleImageView profileImagev;
  private Button finishBtn;

  //VARIABLES
  Retrofit retrofit = APIClientRetrofit.getClient();
  ArrayList<String> departmentsList;
  ArrayList<String> majorsList;
  studymate.mstechnologies.com.studymateandroid.Models.CompleteProfile cp;
  Requests requests ;
  private static final int GALLERY_REQUEST = 2;
  private static final int CAMETA_REQUEST = 1;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_complete_profile);
    WidgetsInit();
    cp=new studymate.mstechnologies.com.studymateandroid.Models.CompleteProfile();
    VariablesInit();
    LoadDepartments();
    ClickListeners();
  }

  private void LoadDepartments()
  {



    final  APIinterfaceRetrofit getDepartments = retrofit.create(APIinterfaceRetrofit.class);
    Call<ArrayList<String>> getDepartmentsCall = getDepartments.getDepartments();
    getDepartmentsCall.enqueue(new Callback<ArrayList<String>>() {
      @Override
      public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
        if (response.code()==200)
        {
          departmentsList=response.body();
          ArrayAdapter<String> adapter = new ArrayAdapter<String>(CompleteProfileActivity.this, android.R.layout.simple_spinner_item,  departmentsList);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          departmentsSpinner.setAdapter(adapter);

        }
      }

      @Override public void onFailure(Call<ArrayList<String>> call, Throwable t) {
        MDToast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.SomethingWentWrong),Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      }
    });

  }


  private void VariablesInit()
  {
  departmentsList = new ArrayList<>();
  majorsList = new ArrayList<>();
  SharedPreferences prefs = this.getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
  int id = prefs.getInt("Id",-1);

  cp.setId(id);
  cp.setMajor("");
  requests= new Requests(getApplicationContext());

  }

  private void ClickListeners()
  {
    departmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        LoadMajors(departmentsSpinner.getSelectedItem().toString().trim());
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {
        MDToast.makeText(getApplicationContext(),getResources().getString(R.string.SelectDepartment),Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();
      }
    });
    majorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        cp.setMajor(majorsSpinner.getSelectedItem().toString().trim());

      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {
        MDToast.makeText(getApplicationContext(),getResources().getString(R.string.SelectMajor),Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();
      }
    });
    finishBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.ep_spin_kit_pb);
        FoldingCube foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);
        progressBar.setVisibility(View.VISIBLE);
             Bitmap bitmap = ((BitmapDrawable) profileImagev.getDrawable()).getBitmap();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();

             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
             byte[] imageInByte = baos.toByteArray();
             Log.v("ARRAY BYTE LENGTH", imageInByte.length + "");
             cp.setImage(imageInByte);

        if(cp.getId() !=-1 && !cp.getMajor().isEmpty())
        {
          progressBar.setVisibility(View.VISIBLE);
          APIinterfaceRetrofit updateProfile = retrofit.create(APIinterfaceRetrofit.class);
          Call<String> updateProfileCall = updateProfile.updateProfile(cp);
          updateProfileCall.enqueue(new Callback<String>() {
            @Override public void onResponse(Call<String> call, Response<String> response) {
              if(response.code()==200)
              {

                getSharedPreferences(Utils.Login_Preferences,MODE_PRIVATE).edit().putInt("First_Login",0).apply();
                Intent homeIntent = new Intent(CompleteProfileActivity.this,HomeActivity.class);
                startActivity(homeIntent);
                progressBar.setVisibility(View.INVISIBLE);
                finish();
              }
              else
              {
                Toast.makeText(CompleteProfileActivity.this, response.code()+"", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
              }
            }

            @Override public void onFailure(Call<String> call, Throwable t) {
              MDToast.makeText(getApplicationContext(),getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
            }
          });
        }
        else
        {
          progressBar.setVisibility(View.INVISIBLE);
          MDToast.makeText(getApplicationContext(),getResources().getString(R.string.SelectMajor),Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        }
        try {
          baos.close();
        }
        catch (IOException e)
        {
          Log.d("Complete Profile : " , e.getMessage());
        }
      }
    });

    profileImagev.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        ProfilePicEvent();
      }
    });
  }

  private void LoadMajors(String selectedDep)
  {





    final APIinterfaceRetrofit  getMajors = retrofit.create(APIinterfaceRetrofit.class);
    Call<ArrayList<String>> getMajorsCall = getMajors.getMajors(selectedDep);
    getMajorsCall.enqueue(new Callback<ArrayList<String>>() {
      @Override
      public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
        if (response.code()==200)
        {
          majorsList=response.body();
          ArrayAdapter<String> adapter = new ArrayAdapter<String>(CompleteProfileActivity.this, android.R.layout.simple_spinner_item, majorsList);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          majorsSpinner.setAdapter(adapter);

        }
      }

      @Override public void onFailure(Call<ArrayList<String>> call, Throwable t)
      {
        MDToast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.SomethingWentWrong),
            Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      }
    });



  }

  private void WidgetsInit()
  {
    departmentsSpinner=(Spinner)findViewById(R.id.departmentSpinner);
    majorsSpinner = (Spinner)findViewById(R.id.majorSpinner);
    profileImagev = (CircleImageView)findViewById(R.id.cp_profileImage);
    finishBtn = (Button)findViewById(R.id.cp_finishBtn);


  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
   /*
    if(requestCode==CAMERA_REQUEST_CODE)
    {
      if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
      {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_FROM_CAMERA);
      }
      else
      {
        MDToast.makeText(getApplicationContext(),getResources().getString(R.string.cameraPermissionFailed),Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      }
    }*/
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
     ///GALLERY
    if(requestCode==GALLERY_REQUEST && resultCode == RESULT_OK)
    {
      Uri ImageUri = data.getData();
      CropImage.activity(ImageUri)
          .setGuidelines(CropImageView.Guidelines.ON)
          .setCropShape(CropImageView.CropShape.OVAL)
          .setAspectRatio(1,1)
          .setFixAspectRatio(true)
          .start(this);
    }
    if(requestCode==CAMETA_REQUEST&& resultCode == RESULT_OK)
    {
      Uri ImageUri = data.getData();
      CropImage.activity(ImageUri)
          //.setGuidelines(CropImageView.Guidelines.OFF)
          .setCropShape(CropImageView.CropShape.OVAL)
          .setAspectRatio(1,1)
          .setFixAspectRatio(true)
          .start(this);
    }
     /// IMAGE CROPPER
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        profileImagev.setImageURI(result.getUri());

      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Exception error = result.getError();
      }
    }

  }

  private void ProfilePicEvent()
  {
    String[] ways = new String[]{getResources().getString(R.string.TakeAPic),getResources().getString(R.string.ChooseFromGallary),getResources().getString(R.string.Cancel)};
    AlertDialog.Builder builder = new AlertDialog.Builder(CompleteProfileActivity.this);
    builder.setTitle(getResources().getString(R.string.SelectImage));
    builder.setItems(ways, new DialogInterface.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.M) @Override public void onClick(DialogInterface dialog, int which) {
        if(which==0)
        {
          if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMETA_REQUEST);
          }
          else
          {
            String[] permissionsRequested = {Manifest.permission.CAMERA};
            requestPermissions(permissionsRequested,CAMETA_REQUEST);
          }
        }
        else if(which==1)
        {

          Intent galleryIntent = new Intent();
          galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
          galleryIntent.setType("image/*");
          startActivityForResult(galleryIntent,GALLERY_REQUEST);

        }
        else if (which==2)
        {
          dialog.dismiss();
        }
      }
    });
    builder.show();
  }

}


