package com.example.employeedetails;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.department.setText(model.getDepartment());
        holder.gender.setText(model.getGender());
        holder.email.setText(model.getEmail());
        holder.address.setText(model.getAddress());

        Glide.with(holder.img.getContext())
                .load(model.getEurl())
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        //make the edit operations to particular recycle view.
        //bind the pop box in the update_popup.xml to the edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1078)
                        .create();

                //after clicking the Edit button dialog box should open below. to make it use the "dialog.plus.show()"
                //dialogPlus.show();


                //to view the update dialog box
                View view = dialogPlus.getHolderView();


                //to access the text fields in the update pop box
                EditText name = view.findViewById(R.id.txtName);
                EditText department = view.findViewById(R.id.txtDepartment);
                EditText gender = view.findViewById(R.id.txtGender);
                EditText email = view.findViewById(R.id.txtmail);
                EditText address = view.findViewById(R.id.txtAddress);
                EditText eurl = view.findViewById(R.id.txtImageUrl);

                //to access the Update button
                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                //to set the text in the text field in the update dialog box by fetching through the firebase
                name.setText(model.getName());
                department.setText(model.getDepartment());
                gender.setText(model.getGender());
                email.setText(model.getEmail());
                address.setText(model.getAddress());
                eurl.setText(model.getEurl());

                //to view the dialog box
                dialogPlus.show();

                //to make the click operations according to the update
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("department",department.getText().toString());
                        map.put("gender",gender.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("address",address.getText().toString());
                        map.put("eurl",eurl.getText().toString());

                        //for update button

                        FirebaseDatabase.getInstance().getReference().child("employee")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        //when toast messages display after the successfully updated data.
                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

                                        //to close the update pop up box after click the update button on the dialog box.
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        //when toast messages display after error in update details.
                                        Toast.makeText(holder.name.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });

        //for delete button
        //for alert dialog box when click the delete button
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted date can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //make the delete implementation
                        FirebaseDatabase.getInstance().getReference().child("employee")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                //to show alert dialog button
                builder.show();

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name,department,gender,email,address;

        //link to access the edit and delete buttons.
        Button btnEdit,btnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.nametext);
            department = (TextView)itemView.findViewById(R.id.departmenttext);
            gender = (TextView)itemView.findViewById(R.id.gendertext);
            email = (TextView)itemView.findViewById(R.id.emailtext);
            address = (TextView)itemView.findViewById(R.id.addresstext);

            //link to access the edit and delete buttons.
            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }
}
