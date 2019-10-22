package com.springMay.sumbooks.adapters;




        import android.content.Context;
        import android.content.Intent;

        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;


        import android.widget.Filter;
        import android.widget.Filterable;
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


public class BooksRecycleAdapter extends    RecyclerView.Adapter<BooksRecycleAdapter.viewitem>  implements Filterable {


   // String isFave;
    private ArrayList<BooksModel> items;
    private ArrayList<BooksModel> itemsfull;
    private Context context;

    public BooksRecycleAdapter(Context c, ArrayList<BooksModel> item)
    {
        items=item;
        itemsfull=new ArrayList<>(items);
        context=c;

    }

    @Override
    public Filter getFilter() {
        return bookFilter;
    }
    //
    private Filter bookFilter=new Filter() {
        @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<BooksModel>filteredBooks=new ArrayList<>();

            if (charSequence== null || charSequence.length()==0){

              filteredBooks.addAll(itemsfull);

            }
            else{

                String filterPattern = charSequence.toString().toLowerCase().trim();


                for(BooksModel item : itemsfull){

                    if(item.getBookName().toLowerCase().contains(filterPattern)){


                        filteredBooks.add(item);
                    }

                }





            }

           FilterResults results = new FilterResults();
            results.values=filteredBooks;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {


             items.clear();;
             items.addAll((ArrayList)results.values);
             notifyDataSetChanged();
        }
    };
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
                    ws.addFav(context, booksModel.getBookName(), booksModel.getBookPdf(),Z);


                    Intent i = new Intent(context, App.class);
                    context.startActivity(i);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View layout = inflater.inflate(R.layout.toast_layout,
                       null);

                TextView text = layout.findViewById(R.id.text);
                text.setText(booksModel.getBookName() + " Added to Favorite list");

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





    @Override
    public int getItemCount() {
        return items.size();
    }



    // private final View.OnClickListener mOnClickListener = new MyOnClickListener();



//    @Override
//    public void onClick(final View view) {
//        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
//        String item = mList.get(itemPosition);
//        Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
//    }


}
