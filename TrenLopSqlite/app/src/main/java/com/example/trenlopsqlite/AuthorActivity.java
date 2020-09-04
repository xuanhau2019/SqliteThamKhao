package com.example.trenlopsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class AuthorActivity extends AppCompatActivity {

    EditText edtID,edtName,edtAddress,edtEmail;
    Button btnSelect,btnSave,btnDelete,btnUpdate;
    GridView gv_display;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);

        btnSelect = findViewById(R.id.btnSelect);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        gv_display = findViewById(R.id.gv_display);

        database = new Database(this);


        select();
        save();
        delete();
        update();
        onItemClick();
    }

    private void onItemClick() {
        gv_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Author author = database.getAllAuthorByID(i+1);
                edtID.setText(author.getId_author()+"");
                edtName.setText(author.getName());
                edtEmail.setText(author.getEmail());
                edtAddress.setText(author.getAddress());
            }
        });
    }

    private void update() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(edtID.getText().toString());
                if(database.updateAuthor(id,edtName.getText().toString(),edtAddress.getText().toString(),edtEmail.getText().toString()))
                    Toast.makeText(AuthorActivity.this, "Update thanh cong", Toast.LENGTH_SHORT).show();
                Toast.makeText(AuthorActivity.this, "Update chua thanh cong", Toast.LENGTH_SHORT).show();
                edtID.setText("");
                edtName.setText("");
                edtAddress.setText("");
                edtEmail.setText("");
            }
        });
    }

    private void select() {
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Author> authorArrayList = new ArrayList<>();
                ArrayList<String> stringArrayList = new ArrayList<>();

                if(edtID.getText().toString().equals(""))
                    authorArrayList = database.getAllAuthor();
                else
                    authorArrayList.add(database.getAllAuthorByID(Integer.parseInt(edtID.getText().toString())));

                if(authorArrayList.size()>0){
                    for(Author author: authorArrayList){
                        stringArrayList.add(author.getId_author()+"\t" + author.getName()+"\t"+author.getAddress()+"\t"+author.getEmail());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AuthorActivity.this,android.R.layout.simple_list_item_1,stringArrayList);
                    gv_display.setAdapter(adapter);
                }else{
                    Toast.makeText(AuthorActivity.this, "CSDL NULL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void delete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =Integer.parseInt(edtID.getText().toString());
                if(database.deleteAuthor(id) ==  true)
                    Toast.makeText(AuthorActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AuthorActivity.this, "Xoa that bai", Toast.LENGTH_SHORT).show();
                edtID.setText("");
            }
        });

}
    private void save() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Author author = new Author(Integer.parseInt(edtID.getText().toString()),edtName.getText().toString(),edtAddress.getText().toString(),edtEmail.getText().toString());
                if(database.insertAuthor(author) > 0 ){
                    Toast.makeText(AuthorActivity.this, "Them Thanh Cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AuthorActivity.this, "Them Khong Thanh Cong", Toast.LENGTH_SHORT).show();
                }
                edtID.setText("");
                edtName.setText("");
                edtAddress.setText("");
                edtEmail.setText("");

                edtID.setCursorVisible(true);
            }
        });
    }
}