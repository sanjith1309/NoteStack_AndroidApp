package com.example.projectpdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class viewdownloads extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PDFAdapter pdfAdapter;
    private List<File> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdownloads);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fileList = new ArrayList<>();

        // Set the path to your "mydownloadedpdfs" folder
        String path = getExternalFilesDir(null) + File.separator + "mydownloadedpdfs";
        getFileList(path);


        pdfAdapter = new PDFAdapter(this, fileList);
        recyclerView.setAdapter(pdfAdapter);
    }

    private void getFileList(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".pdf")) {
                    fileList.add(file);
                }
            }
        }
    }
}
