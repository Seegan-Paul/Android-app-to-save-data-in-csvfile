package com.example.task1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText name,age,gender,email,phone;
    ImageView image;
    Uri imageFilePath;
    Bitmap ImageStore;
    static final int PICK_IMAGE = 100;
    ByteArrayOutputStream byteArrayOutputStream;
    byte [] ImageByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.editTextPersonName);
        age = findViewById(R.id.editTextAge);
        gender = findViewById(R.id.editTextGender);
        email = findViewById(R.id.editTextMail);
        phone = findViewById(R.id.editTextPhone);
        image = findViewById(R.id.imageView);


        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.Q){
            //Android version < 10
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                askpermission();
            }
        }
        else{
            //Android version >= 10
            String path = "Excel_File";
            File file2 = new File(getExternalFilesDir(null)+"/"+path);
            if(!file2.exists()){
                file2.mkdir();
                //Toast.makeText(MainActivity.this, "Folder created\n"+file2.getPath(),Toast.LENGTH_LONG).show();
                File file3 = new File(file2.getPath()+"/xyz.xls");
                try {
                    file3.createNewFile();
                    //if(file3.createNewFile())
                    //  Toast.makeText(MainActivity.this, "File created\n"+file3.getPath(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    //e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
                /*String csv = file3.getPath();
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
                }*/

                String row1[] = new String[]{"Name","Age", "Gender", "Email", "Phone no","image"};
                Sheet sheet = null;
                Row row = null;
                Cell cell = null;

                Workbook workbook = new HSSFWorkbook();
                sheet = workbook.createSheet("sheet1");
                row = sheet.createRow(0);
                for(int i=0;i<6;i++){
                    cell = row.createCell(i);
                    cell.setCellValue(row1[i]);

                }

                FileOutputStream fileOutputStream = null;

                try {
                    fileOutputStream = new FileOutputStream(file3);
                    workbook.write(fileOutputStream);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(fileOutputStream != null){
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
        String path = "Excel_File";
        File file = new File(Environment.getExternalStorageDirectory(),path);
        if(!file.exists()){
            file.mkdir();
            //Toast.makeText(MainActivity.this, "Folder created\n"+file2.getPath(),Toast.LENGTH_LONG).show();
            File file1 = new File(file.getPath()+"/xyz.xls");
            try {
                file1.createNewFile();
                //if(file3.createNewFile())
                //  Toast.makeText(MainActivity.this, "File created\n"+file3.getPath(),Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                //e.printStackTrace();
                Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
            }

            String row1[] = new String[]{"Name","Age", "Gender", "Email", "Phone no","Image"};
            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            Workbook workbook = new HSSFWorkbook();
            sheet = workbook.createSheet("sheet1");
            row = sheet.createRow(0);
            for(int i=0;i<6;i++){
                cell = row.createCell(i);
                cell.setCellValue(row1[i]);

            }
            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream(file1);
                workbook.write(fileOutputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(fileOutputStream != null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
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
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageStore.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            ImageByte = byteArrayOutputStream.toByteArray();

            String csv1 = "/sdcard/Excel_File/xyz.xls";
            try {
                FileInputStream fileInputStream = new FileInputStream(csv1);

                String val[] = new String[]{name1,age1,gender1,email1,phone1};
                //String row1[] = new String[]{"Name","Age", "Gender", "Email", "Phone no"};
                Sheet sheet = null;
                Row row = null;
                Cell cell = null;


                Workbook book1 = new HSSFWorkbook(fileInputStream);
                sheet = book1.getSheet("sheet1");
                int lastrow = sheet.getLastRowNum();
                row = sheet.createRow(lastrow+1);
                int i =0;
                for(i=0;i<5;i++){
                    cell = row.createCell(i);
                    cell.setCellValue(val[i]);
                }
                int cellNum = row.getLastCellNum();
                int Index = book1.addPicture(ImageByte,Workbook.PICTURE_TYPE_JPEG);
                CreationHelper creationHelper = book1.getCreationHelper();

                ClientAnchor clientAnchor = creationHelper.createClientAnchor();
                clientAnchor.setCol1(cellNum);
                clientAnchor.setRow1(lastrow+1);
                clientAnchor.setCol2(cellNum+1);
                clientAnchor.setRow2(lastrow+2);

                Drawing drawing = sheet.createDrawingPatriarch();
                drawing.createPicture(clientAnchor, Index);
                //row.createCell(cellNum);

                fileInputStream.close();
                FileOutputStream fileOutputStream = null;

                try {
                    fileOutputStream = new FileOutputStream(csv1);
                    book1.write(fileOutputStream);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(fileOutputStream != null){
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

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
            String path = "Excel_File/xyz.xls";
            File file2 = new File(getExternalFilesDir(null)+"/"+path);
            String csv1 = file2.getPath();

            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageStore.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            ImageByte = byteArrayOutputStream.toByteArray();

            try {
                FileInputStream fileInputStream = new FileInputStream(csv1);

                String val[] = new String[]{name1,age1,gender1,email1,phone1};
                //String row1[] = new String[]{"Name","Age", "Gender", "Email", "Phone no"};
                Sheet sheet = null;
                Row row1 = null;
                Cell cell = null;

                Workbook book1 = new HSSFWorkbook(fileInputStream);
                sheet = book1.getSheet("sheet1");
                int lastrow = sheet.getLastRowNum();
                row1 = sheet.createRow(lastrow+1);
                for(int i=0;i<5;i++){
                    cell = row1.createCell(i);
                    cell.setCellValue(val[i]);

                }

                int cellNum = row1.getLastCellNum();
                int Index = book1.addPicture(ImageByte,Workbook.PICTURE_TYPE_JPEG);
                CreationHelper creationHelper = book1.getCreationHelper();

                ClientAnchor clientAnchor = creationHelper.createClientAnchor();
                clientAnchor.setCol1(cellNum);
                clientAnchor.setRow1(lastrow+1);
                clientAnchor.setCol2(cellNum+1);
                clientAnchor.setRow2(lastrow+2);

                Drawing drawing = sheet.createDrawingPatriarch();
                drawing.createPicture(clientAnchor, Index);

                fileInputStream.close();


                FileOutputStream fileOutputStream = null;

                try {
                    fileOutputStream = new FileOutputStream(csv1);
                    book1.write(fileOutputStream);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(fileOutputStream != null){
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void selectImage(View view) {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE);
        }catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            imageFilePath = data.getData();
            ImageStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
            //ImageStore = BitmapFactory.decodeFile(imageFilePath.toString());
            image.setImageBitmap(ImageStore);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}