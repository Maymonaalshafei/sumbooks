package com.springMay.sumbooks.adapters;




import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.springMay.sumbooks.Models.BooksModel;
import com.springMay.sumbooks.R;
import com.springMay.sumbooks.activities.App;
import com.springMay.sumbooks.activities.PdfView;

import com.springMay.sumbooks.webServices.WS;


import java.util.ArrayList;


public class FavoriteRecycleAdapter extends    RecyclerView.Adapter<FavoriteRecycleAdapter.viewitem> {

    //String isFave;
    private ArrayList<BooksModel> items;
    private Context context;

    public FavoriteRecycleAdapter(Context c, ArrayList<BooksModel> item)
    {
        items=item;
        context=c;

    }
    //
    class  viewitem extends RecyclerView.ViewHolder
    {

        TextView bookName;
        ImageView save;



        //initialize the items
        public viewitem(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.tvBookName);
            save = itemView.findViewById(R.id.ivSave);


        }
    }
    //
    @NonNull
    @Override
    public viewitem onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {



        //the itemView now is the row
        //We will add 2 onClicks


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookscard, parent, false);






        return new viewitem(itemView);
    }
    //
    @Override
    public void onBindViewHolder(@NonNull final viewitem holder, final int position) {
        final    BooksModel booksModel=items.get(position);
        holder.bookName.setText(booksModel.getBookName());

        holder.bookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // i wanna add action to make a copy of the hole row to the my books list fragment

                openbook(booksModel.getBookPdf());
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // i wanna add action to make a copy of the hole row to the my books list fragment

                SharedPreferences userSettings = PreferenceManager.getDefaultSharedPreferences(context);
                String Z= userSettings.getString("id","");

                    WS ws = new WS();
                    ws.deleteFav(context, booksModel.getBookName(), booksModel.getBookPdf(),Z);


                    Intent i = new Intent(context, App.class);
                    context.startActivity(i);


                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View layout = inflater.inflate(R.layout.toast_layout2,
                        null);

                TextView text = layout.findViewById(R.id.text);
                text.setText(booksModel.getBookName() + " Removed from Favorite list");

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();



            }
        });
    }
    //
    private void openbook(String bookpdf) {


        Intent i= new Intent(context, PdfView.class);
        i.putExtra("path",  bookpdf);
        context.startActivity(i);


    }
    //
    @Override
    public int getItemCount() {
        return items.size();
    }




}
