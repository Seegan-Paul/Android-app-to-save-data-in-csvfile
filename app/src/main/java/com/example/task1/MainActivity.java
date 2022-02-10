package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText name,age,gender,email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.editTextPersonName);
        age = findViewById(R.id.editTextAge);
        gender = findViewById(R.id.editTextGender);
        email = findViewById(R.id.editTextMail);
        phone = findViewById(R.id.editTextPhone);


        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.Q){
            //Android version < 10
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                askpermission();
            }
        }
        else{
            //Android version >= 10
            String path = "CSV_File";
            File file2 = new File(getExternalFilesDir(null)+"/"+path);
            if(!file2.exists()){
                file2.mkdir();
                //Toast.makeText(MainActivity.this, "Folder created\n"+file2.getPath(),Toast.LENGTH_LONG).show();
                File file3 = new File(file2.getPath()+"/xyz.csv");
                try {
                    file3.createNewFile();
                    //if(file3.createNewFile())
                    //  Toast.makeText(MainActivity.this, "File created\n"+file3.getPath(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    //e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
                String csv = file3.getPath();
                CSVWriter csvWriter = null;
                try {
                    csvWriter = new CSVWriter(new FileWriter(csv,true));
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
                String row1[] = new String[]{"Name","Age", "Gender", "Email", "Phone no"};
                csvWriter.writeNext(row1);
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void askpermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                create();
        }
        else {
            Toast.makeText(MainActivity.this, "Permission Denied",Toast.LENGTH_LONG).show();
        }
    }

    public void create() {
        String path = "CSV_file";
        File file = new File(Environment.getExternalStorageDirectory(),path);
        if(!file.exists()){
            file.mkdir();
            File file1 = new File(file.getPath()+"/xyz.csv");
            try {
                file1.createNewFile();
                /*if(file1.createNewFile())
                    Toast.makeText(MainActivity.this, "File created\n"+file1.getPath(),Toast.LENGTH_LONG).show();*/
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
            }

            String csv = file1.getPath();
            CSVWriter csvWriter = null;
            try {
                csvWriter = new CSVWriter(new FileWriter(csv,true));
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
            }
            String row1[] = new String[]{"Name","Age", "Gender", "Email", "Phone no"};
            csvWriter.writeNext(row1);
            try {
                csvWriter.close();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }

    public void submit(View view) {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.Q){
            //Android version < 10
            String name1,age1,gender1,email1,phone1;
            name1 = name.getText().toString();
            age1 = age.getText().toString();
            gender1 = gender.getText().toString();
            email1 = email.getText().toString();
            phone1 = phone.getText().toString();

            String csv1 = "/sdcard/CSV_File/xyz.csv";
            try {
                CSVWriter csvwritter1 = new CSVWriter(new FileWriter(csv1,true));
                String row[] = new String[]{name1,age1,gender1,email1,phone1};
                csvwritter1.writeNext(row);
                csvwritter1.close();
                Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }else{
            //Android version >= 10
            String name1,age1,gender1,email1,phone1;
            name1 = name.getText().toString();
            age1 = age.getText().toString();
            gender1 = gender.getText().toString();
            email1 = email.getText().toString();
            phone1 = phone.getText().toString();
            String path = "CSV_File/xyz.csv";
            File file2 = new File(getExternalFilesDir(null)+"/"+path);
            String csv1 = file2.getPath();
            try {
                CSVWriter csvwritter1 = new CSVWriter(new FileWriter(csv1,true));
                String row[] = new String[]{name1,age1,gender1,email1,phone1};
                csvwritter1.writeNext(row);
                csvwritter1.close();
                Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
}