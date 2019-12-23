package com.example.googleauth;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ProgramViewHolder>{

    private  String[] PidData;
    private  String[] TitleData;
    private String[] ContentData;


    public MyAdapter(String[] Pid ,String[] titleData , String[] contentData ){
        this.PidData = Pid;
        this.TitleData = titleData;
        this.ContentData = contentData;
    }


    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.my_text_view,viewGroup,false);

        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder programViewHolder, int i) {
        String pid = PidData[i];
        String name = TitleData[i];
        String service = ContentData[i];


        programViewHolder.PidView.setText(pid);
        programViewHolder.textView.setText(name);
        programViewHolder.ContentDataView.setText(service);


         }

    @Override
    public int getItemCount() {
        return  TitleData.length;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {

        TextView PidView;
        TextView textView;
        TextView ContentDataView;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);

            //imgview = (ImageView) itemView.findViewById(R.id.staidtextView);
            //            textView = (TextView) itemView.findViewById(R.id.tusImageView);
            PidView = (TextView) itemView.findViewById(R.id.PidtextView);
            textView = (TextView) itemView.findViewById(R.id.TitletextView);
            ContentDataView = (TextView) itemView.findViewById(R.id.textView2);
            //textAddressView = (TextView) itemView.findViewById(R.id.InstaLink);
            //textStatusView = (TextView) itemView.findViewById(R.id.statusView);

        }
    }
}
