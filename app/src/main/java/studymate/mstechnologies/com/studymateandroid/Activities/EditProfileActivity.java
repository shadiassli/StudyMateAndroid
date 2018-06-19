

package studymate.mstechnologies.com.studymateandroid.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.valdesekamdem.library.mdtoast.MDToast;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import studymate.mstechnologies.com.studymateandroid.Models.EditProfile;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.Requests;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class EditProfileActivity extends AppCompatActivity {

//WIDGETS
  private Spinner departmentsSpinner,majorsSpinner;
  EditText userNameET,phoneNumberET;
  private CircleImageView profileImagev;
  private ImageButton birthdateBtn;
  private Button finishBtn;
  //Variables
  Requests requests;
  private static final int GALLERY_REQUEST = 2;
  private static final int CAMETA_REQUEST = 1;
  int userID;
  EditProfile editProfile;
  ArrayList<String> departmentsList;
  ArrayList<String> majorsList;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);
    VariablesInit();
    WidgetsInit();
    LoadDepartments();
    ClickListeners();
  }

  private void VariablesInit()
  {
    requests = new Requests(this);
    departmentsList = new ArrayList<>();
    majorsList = new ArrayList<>();
    SharedPreferences preferences = getSharedPreferences(Utils.Login_Preferences,MODE_PRIVATE);
    userID=preferences.getInt("Id",-1);
  }

  private void LoadDepartments()
  {
    departmentsList = requests.loadDepartments();
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_spinner_item,  departmentsList);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    departmentsSpinner.setAdapter(adapter);
  }

  private void ClickListeners()
  {
    profileImagev.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        ProfilePicEvent();
      }
    });
    departmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        LoadMajors(departmentsSpinner.getSelectedItem().toString().trim());

      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {
        MDToast.makeText(getApplicationContext(),getResources().getString(R.string.SelectDepartment),
            Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();

      }
    });
    majorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //sets the major
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {
        MDToast.makeText(getApplicationContext(),getResources().getString(R.string.SelectMajor),Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();

      }
    });
  }

  private void LoadMajors(String selectedDepartment)
  {
    departmentsList = requests.loadMajors(selectedDepartment);
  }

  private void ProfilePicEvent()
  {
    String[] ways = new String[]{getResources().getString(R.string.TakeAPic),getResources().getString(R.string.ChooseFromGallary),getResources().getString(R.string.Cancel)};
    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
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

  private void WidgetsInit()
  {
    departmentsSpinner=(Spinner)findViewById(R.id.ep_departmentSpinner);
    majorsSpinner = (Spinner)findViewById(R.id.ep_majorSpinner);
    profileImagev = (CircleImageView)findViewById(R.id.ep_user_pic);
    userNameET = (EditText)findViewById(R.id.ep_user_name_ET);
    phoneNumberET = (EditText)findViewById(R.id.ep_phone_number_ET);
    birthdateBtn = (ImageButton)findViewById(R.id.ep_birthDateBtn);
    finishBtn = (Button)findViewById(R.id.cp_finishBtn);
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
}
