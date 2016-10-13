package com.in.bookapp;

/**
 * Created by RASHI on 08-Oct-16.
 */


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private List<Books> booksList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public BooksAdapter(List<Books> moviesList) {
        this.booksList = booksList;
    }
//error may occur
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.books_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Books books = booksList.get(position);
        holder.title.setText(books.getTitle());
        holder.genre.setText(books.getGenre());
        holder.year.setText(books.getYear());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}
