package com.example.sachsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AuthorAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Author> authors;

    public AuthorAdapter(Context context, int layout, List<Author> authors) {
        this.context = context;
        this.layout = layout;
        this.authors = authors;
    }

    @Override
    public int getCount() {
        return authors.size();
    }

    @Override
    public Object getItem(int i) {
        return authors.get(i);
    }

    @Override
    public long getItemId(int i) {
//        return authors.get(i).getMatacgia();
        return 0;
    }

    private  class ViewHolder{
        TextView txtSTT;
        TextView txtMaTacGia,txtTen;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.txtSTT = view.findViewById(R.id.textViewSTT);
            holder.txtMaTacGia = view.findViewById(R.id.textViewMaTacGia);
            holder.txtTen = view.findViewById(R.id.textViewTenTacGia);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Author author = authors.get(i);

        holder.txtSTT.setText(i+1+"");
        holder.txtMaTacGia.setText(author.getMatacgia()+"");
        holder.txtTen.setText(author.getTentacgia());

        return view;
    }
}
