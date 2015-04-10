package com.thinkful.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<NoteListItem> mNoteListItems = new ArrayList<NoteListItem>() {
//        {
//            add (new NoteListItem("This is your first note."));
//        }
    };

    public NoteListItemAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;

        NoteDAO dao = new NoteDAO(context);
        mNoteListItems = dao.list();
    }

    @Override
    public NoteListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, viewGroup, false);

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Get the position (i.e. index) of the note in the list that the user clicked on
//                int position = mRecyclerView.getChildPosition(v);
//                removeItem(position);
//                Intent intent = new Intent(mContext, EditNoteActivity.class);
//                NoteListItem noteListItem = mNoteListItems.get(position);
//                Toast.makeText(mContext, "Editing Note", Toast.LENGTH_SHORT).show();

                NoteListItem noteListItem = mNoteListItems.get(mRecyclerView.getChildPosition(v));
                removeItem(mRecyclerView.getChildPosition(v));

                Intent intent = new Intent(mContext, EditNoteActivity.class);
                intent.putExtra("Note", noteListItem);

                ((Activity) mContext).startActivityForResult(intent, 1);
                return true;
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the position (i.e. index) of the note in the list that the user clicked on
                int position = mRecyclerView.getChildPosition(v);

                NoteListItem noteListItem = mNoteListItems.get(position);
                NoteDAO dao = new NoteDAO(mContext);
                dao.delete(noteListItem);

                // Notify with Toas and Call the removeItem method with the position
                Toast.makeText(mContext, "Deleted:" + noteListItem.getText(), Toast.LENGTH_SHORT).show();
                removeItem(position);
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteListItemAdapter.ViewHolder viewHolder, int i) {
        NoteListItem noteListItem = mNoteListItems.get(i);
        viewHolder.setText(noteListItem.getText());
    }

    @Override
    public int getItemCount() {
        return mNoteListItems.size();
    }

    public void addItem(NoteListItem item) {
        //add note to beginning of the list
        mNoteListItems.add(0, item);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
        //remove note after tapping
        mNoteListItems.remove(position);
        notifyItemRemoved(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        public void setText(String text) {
            this.text.setText(text);
        }
    }

}
