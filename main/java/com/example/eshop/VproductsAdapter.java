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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Collections;
import java.util.List;


/**
 * Created by User on 2/3/2017.
 */
public class VproductsAdapter extends BaseAdapter {
    Context c;
    List<Datacraft> data;
    ImageView img;

    public VproductsAdapter(Context c, List<Datacraft> data) {
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
        TextView femail=(TextView)convertView.findViewById(R.id.vfemail);
        ImageView img=(ImageView)convertView.findViewById(R.id.vImg);

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
                Toast.makeText(c,"ccc"+dc.getFEmail(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(c.getApplicationContext(),ViewOrder.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pname", dc.getPname());
                intent.putExtra("email", dc.getFEmail());
                intent.putExtra("url", dc.getImg());
                c.getApplicationContext().startActivity(intent);
            }
        });
        return convertView;
    }


}