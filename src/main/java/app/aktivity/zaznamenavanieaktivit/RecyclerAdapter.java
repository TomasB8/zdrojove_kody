package app.aktivity.zaznamenavanieaktivit;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<HashMap<String, String>> aktivity;
    ArrayList<String> pocet;

    public RecyclerAdapter(ArrayList<HashMap<String, String>> aktivity, ArrayList<String> pocet) {
        super();
        this.aktivity = aktivity;
        this.pocet = pocet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.karta_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
//        for(int j=0; j<aktivity.size(); j++){
            HashMap<String, String> zaznam = aktivity.get(i);
            holder.id.setText(String.valueOf(zaznam.get("_id")));
            holder.nazov.setText(String.valueOf(zaznam.get("nazov")));
            holder.pocet.setText(String.valueOf(pocet.get(i)));
            holder.farba.setBackgroundColor(Integer.parseInt(String.valueOf(zaznam.get("farba"))));
//        }

    }

    @Override
    public int getItemCount() {
        return aktivity.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public Button farba;
        public TextView nazov, pocet, id;

        public ViewHolder(View itemView){
            super(itemView);
            id = (TextView)itemView.findViewById(R.id.tvHomeId);
            farba = (Button)itemView.findViewById(R.id.btnHomeFarba);
            nazov = (TextView)itemView.findViewById(R.id.tvHomeNazov);
            pocet = (TextView)itemView.findViewById(R.id.tvHomePocet);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nazov.getText().toString().trim();

                    if(!pocet.getText().toString().equals("0")){
                        Intent intent = new Intent(nazov.getContext(), ZaznamyList.class);
                        intent.putExtra("nazov", name);
                        nazov.getContext().startActivity(intent);
                    }else{
                        Toast.makeText(id.getContext(), "Pre túto aktivitu nemáte žiadny záznam!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
