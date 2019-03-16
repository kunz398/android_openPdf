package com.smartfiji.allpdftypetest;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
Button btn_open_assts, btn_open_storage, btn_open_from_internet;
    private int PICK_PDF_CODE = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Request Read and Write External Storage
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();

        btn_open_assts = (Button)findViewById(R.id.btn_open_assets);
        btn_open_storage = (Button)findViewById(R.id.btn_open_storage);
        btn_open_from_internet = (Button)findViewById(R.id.btn_open_from_interner);

        btn_open_from_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ViewActivity.class);
                intent.putExtra("ViewType","internet");
                startActivity(intent);
            }
        });

            btn_open_assts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,ViewActivity.class);
                    intent.putExtra("ViewType","assets");
                    startActivity(intent);
                }
            });

            btn_open_storage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browsePDF = new Intent(Intent.ACTION_GET_CONTENT);
                    browsePDF.setType("application/pdf");
                    browsePDF.addCategory(Intent.CATEGORY_OPENABLE);
                      startActivityForResult(Intent.createChooser(browsePDF,"Select PDF"),PICK_PDF_CODE);
                }
            });
    }
    /// ctrl + o

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PDF_CODE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedPDF = data.getData();
            Intent intent = new Intent(MainActivity.this,ViewActivity.class);
            intent.putExtra("ViewType","storage");
            intent.putExtra("FileUri",selectedPDF.toString());
            startActivity(intent);
        }
    }
}
