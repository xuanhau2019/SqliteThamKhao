package com.example.sachsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DanhSachTacGia extends AppCompatActivity {
    ListView listviewDanhSachTacGia;
    ArrayList<Author> authors  = new ArrayList<>();
    AuthorAdapter adapter = new AuthorAdapter(this, R.layout.dong_author,authors);
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tac_gia);

        listviewDanhSachTacGia = findViewById(R.id.listviewDanhSachTacGia);
        listviewDanhSachTacGia.setAdapter(adapter);

        GetDataAuthor();

        listviewDanhSachTacGia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                Author author = (Author) adapter.getItem(position);

                final Dialog dialog = new Dialog(DanhSachTacGia.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.sua_tacgia_dialog);


                final EditText edtMaTacGiaNew = dialog.findViewById(R.id.edtMaTacGiaNew);
                final EditText edtTenTacGiaNew = dialog.findViewById(R.id.edtTenTacGiaNew);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
                Button btnXoaTrangNew = dialog.findViewById(R.id.btnXoaTrangNew);

                edtMaTacGiaNew.setText(author.getMatacgia()+"");
                edtTenTacGiaNew.setText(author.getTentacgia()+"");

                btnXoaTrangNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtMaTacGiaNew.setText("");
                        edtTenTacGiaNew.setText("");
                        edtMaTacGiaNew.requestFocus();
                    }
                });
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ma = edtMaTacGiaNew.getText().toString();
                        String ten = edtTenTacGiaNew.getText().toString();

                        if("".equals(ma)){
                            Toast.makeText(DanhSachTacGia.this, "Chưa Nhập Mã TG", Toast.LENGTH_SHORT).show();
                        }else if("".equals(ten)){
                            Toast.makeText(DanhSachTacGia.this, "Chưa Nhập Tên TG", Toast.LENGTH_SHORT).show();
                        }else{
                            database.QueryData("UPDATE AUTHOR SET tentacgia = '"+ten+"' WHERE matacgia = "+ma+" ");
                            Toast.makeText(DanhSachTacGia.this, "Sửa Tác Giả Thành Công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetDataAuthor();
                        }
                    }
                });
                dialog.show();
            }
        });

        listviewDanhSachTacGia.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int possition, long l) {
                final AlertDialog.Builder dialog; dialog = new AlertDialog.Builder(DanhSachTacGia.this);
                dialog.setTitle("Thông báo");
                dialog.setMessage("Bạn có muốn xóa không?");
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j<authors.size(); j++){
                            if (j == possition){
                                int temp = authors.get(j).getMatacgia();
                                database.QueryData("DELETE FROM AUTHOR WHERE matacgia = "+temp+" ");
                            }
                        }
                        GetDataAuthor();
                }
                });
                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.setCancelable(true);
                    }
                });
                dialog.show();
                return true;
            }
        });

    }
    private void GetDataAuthor(){
        authors.clear();
        database = new Database(this, "sach.sqlite", null, 1);
        Cursor dataAuthor = database.GetData("SELECT * FROM AUTHOR");
        while (dataAuthor.moveToNext()) {
            int ma = dataAuthor.getInt(0);
            String ten = dataAuthor.getString(1);
            authors.add(new Author(ma, ten));
        }
        adapter.notifyDataSetChanged();
    }
}