package com.example.eshop;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eshop.Datacraft;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by User on 2/3/2017.
 */
public class BulkAdapter extends BaseAdapter {
    Context c;
    ArrayList<Datacraft> data;
    ImageView img;

    public BulkAdapter(Context c, ArrayList<Datacraft> data) {
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

            convertView= LayoutInflater.from(c).inflate(R.layout.vproducts,parent,false);
        }

        TextView pnme=(TextView)convertView.findViewById(R.id.vpname);
        ImageView img=(ImageView)convertView.findViewById(R.id.vImg);
        TextView femail=(TextView)convertView.findViewById(R.id.femail1);
        final Datacraft dc= (Datacraft) this.getItem(position);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef = storage.getReferenceFromUrl(dc.getImg());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                pnme.setText(dc.getPname());
                femail.setText(dc.getFEmail());
                Glide.with(c).load(uri).circleCrop().into(img);
            }
        });
        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c.getApplicationContext(),BulkRequest.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pname", dc.getPname());
                intent.putExtra("femail", dc.getFEmail());
                intent.putExtra("url", dc.getImg());
                c.getApplicationContext().startActivity(intent);
            }
        });
        return convertView;
    }


}