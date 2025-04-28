package com.example.eshop;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by User on 2/3/2017.
 */
public class UpdateAdapter extends BaseAdapter {
    Context c;
    ArrayList<Datacraft> data;
    ImageView img;

    public UpdateAdapter(Context c, ArrayList<Datacraft> data) {
        this.c = c;

        this.data = data;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {


        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        data.removeAll(Collections.singleton(null));
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null)
        {

            convertView= LayoutInflater.from(c).inflate(R.layout.items,parent,false);
        }

        TextView pnme=(TextView)convertView.findViewById(R.id.itmPname);
        TextView femail=(TextView)convertView.findViewById(R.id.femail1);
        ImageView imgg=(ImageView)convertView.findViewById(R.id.itemImg);
        final Datacraft dc= (Datacraft) this.getItem(position);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef = storage.getReferenceFromUrl(dc.getImg());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                pnme.setText(dc.getPname());
                femail.setText(dc.getFEmail());
                Glide.with(c).load(uri).circleCrop().into(imgg);
            }
        });

        //Toast.makeText(c,"ccc"+dc.getImg(),Toast.LENGTH_SHORT).show();

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c.getApplicationContext(),Details.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pname", dc.getPname());
                intent.putExtra("fname", dc.getFName());
                intent.putExtra("category", dc.getCategory());
                intent.putExtra("price", dc.getPrice());
                intent.putExtra("url", dc.getImg());
                c.getApplicationContext().startActivity(intent);
            }
        });
        return convertView;
    }


}