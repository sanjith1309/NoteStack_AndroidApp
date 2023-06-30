package com.example.projectpdf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> {

    private Context context;
    private List<File> fileList;

    public PDFAdapter(Context context, List<File> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downpdflist, parent, false);
        return new PDFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        File file = fileList.get(position);
        holder.bind(file);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class PDFViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView pdfNameTextView;
        private ImageView pdfIconImageView;
        private ImageView deleteButtonImageView;
        private CardView pdfItemCardView;

        public PDFViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfNameTextView = itemView.findViewById(R.id.pdf_name);
            deleteButtonImageView = itemView.findViewById(R.id.delete_button);
            pdfIconImageView = itemView.findViewById(R.id.img);
            pdfItemCardView = itemView.findViewById(R.id.downpdf);
            pdfItemCardView.setOnClickListener(this);
            deleteButtonImageView.setOnClickListener(this);
        }

        public void bind(File file) {
            pdfNameTextView.setText(file.getName());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                File file = fileList.get(position);

                if (view.getId() == R.id.downpdf) {
                    openPDFFile(file);
                } else if (view.getId() == R.id.delete_button) {
                    showDeleteConfirmationDialog(file);
                }
            }
        }

        private void openPDFFile(File file) {
            Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }


        private void showDeleteConfirmationDialog(final File file) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete PDF");
            builder.setMessage("Are you sure you want to delete this PDF?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deletePDFFile(file);
                }
            });
            builder.setNegativeButton("Cancel", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private void deletePDFFile(File file) {
            if (file.delete()) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    fileList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "PDF deleted successfully", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Failed to delete PDF", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

