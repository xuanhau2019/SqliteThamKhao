package com.example.sachsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnThemTacGia,btnXemDanhSachTacGia,btnQuanLySach;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnThemTacGia = findViewById(R.id.btnThemTacGia);
        btnXemDanhSachTacGia = findViewById(R.id.btnXemDanhSachTacGia);

        //tao database sach
        database = new Database(this,"sach.sqlite",null,1);
        // tao bang
        // matacgia INTERGER PRIMARY KEY auto increment
        database.QueryData("CREATE TABLE IF NOT EXISTS AUTHOR(matacgia INTERGER PRIMARY KEY, tentacgia varchar(200))");
        database.QueryData("CREATE TABLE IF NOT EXISTS BOOK(masach INTERGER PRIMARY KEY, tensach varchar(200), ngayxuatban varchar, matacgia INTEGER, FOREIGN KEY(matacgia) REFERENCES AUTHOR(matacgia))");

//        database.QueryData("INSERT INTO AUTHOR VALUES(1, 'Ta Quynh')");
//        database.QueryData("INSERT INTO AUTHOR VALUES(2, 'Nguyen Nhat Anh')");
//        database.QueryData("INSERT INTO AUTHOR VALUES(3, 'Pham Tuan')");
//        database.QueryData("INSERT INTO AUTHOR VALUES(4, 'Long Pham')");
//        database.QueryData("INSERT INTO AUTHOR VALUES(5, 'Tran Thanh Tam')");
//
//         database.QueryData("INSERT INTO BOOK VALUES(1, 'Ta Quynh 1','22/11',1)");
//         database.QueryData("INSERT INTO BOOK VALUES(2, 'Ta Quynh 2','23/11',1)");
//         database.QueryData("INSERT INTO BOOK VALUES(3, 'Ta Quynh 3','24/11',1)");

//         database.QueryData("Delete from book");

        Cursor dataBook = database.GetData("SELECT * FROM BOOK");
        while (dataBook.moveToNext()){
            String tenSach = dataBook.getString(1);
            Toast.makeText(this, tenSach, Toast.LENGTH_SHORT).show();
        }

        btnThemTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.themtacgiadialog);

                final EditText edtMaTacGia = dialog.findViewById(R.id.edtMaTacGia);
                final EditText edtTenTacGia = dialog.findViewById(R.id.edtTenTacGia);
                Button btnLuuTacGia = dialog.findViewById(R.id.btnLuuTacGia);
                Button btnXoaTrang = dialog.findViewById(R.id.btnXoaTrang);

                btnLuuTacGia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ma = edtMaTacGia.getText().toString();
                        String ten = edtTenTacGia.getText().toString();

                        // kiểm tra trùng mã
                        Cursor dataBook = database.GetData("SELECT * FROM AUTHOR");
                        ArrayList<String> listMa = new ArrayList<>();
                        while (dataBook.moveToNext()){
                            String maSach = dataBook.getString(0);
                            listMa.add(maSach);
                        }
                        boolean check = false;
                        for(String temp : listMa){
                            if( temp.equals(ma))
                                check = true;
                        }
                        // end check ma

                        if(ma.equals("")){
                            Toast.makeText(MainActivity.this, "Chưa Nhập mã", Toast.LENGTH_SHORT).show();
                        }else if("".equals(ten)){
                            Toast.makeText(MainActivity.this, "Chưa Nhập Tên TG", Toast.LENGTH_SHORT).show();
                        }else if(check == true){
                            Toast.makeText(MainActivity.this, "Trùng Mã Rồi Ba", Toast.LENGTH_SHORT).show();
                        }else{
                            database.QueryData("INSERT INTO AUTHOR VALUES("+ma+", '"+ten+"')");
                            Toast.makeText(MainActivity.this, "Thêm Tác Giả Thành Công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

                btnXoaTrang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtMaTacGia.setText("");
                        edtTenTacGia.setText("");
                        edtMaTacGia.requestFocus();
                    }
                });
                dialog.show();
            }
        });
        btnXemDanhSachTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(MainActivity.this,DanhSachTacGia.class);
                startActivity(intent);
            }
        });
    }
}