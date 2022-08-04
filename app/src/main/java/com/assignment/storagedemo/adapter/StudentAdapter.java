package com.assignment.storagedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.storagedemo.R;
import com.assignment.storagedemo.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private final List<Student> students;
    private ItemClickListener itemClickListener;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView studentEmail;
        public TextView studentFirstName;
        public TextView studentLastName;
        public TextView studentProgram;
        public TextView studentRegNumber;
        public TextView studentYearOfStudy;

        public ViewHolder(View itemView) {
            super(itemView);
            studentFirstName = itemView.findViewById(R.id.std_first_name);
            studentLastName = itemView.findViewById(R.id.std_last_name);
            studentEmail = itemView.findViewById(R.id.std_email);
            studentProgram = itemView.findViewById(R.id.std_program);
            studentRegNumber = itemView.findViewById(R.id.std_reg_number);
            studentYearOfStudy = itemView.findViewById(R.id.std_year_of_study);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Student student = students.get(position);
        // Set item views based on your views and data model
        TextView firstName = holder.studentFirstName;
        TextView lastName = holder.studentLastName;
        TextView email = holder.studentEmail;
        TextView regNumber = holder.studentRegNumber;
        TextView program = holder.studentProgram;
        TextView yearOfStudy = holder.studentYearOfStudy;

        firstName.setText(student.getStudentFirstName());
        lastName.setText(student.getStudentLastName());
        email.setText(student.getStudentEmail());
        regNumber.setText(String.valueOf(student.getStudentRegNumber()));
        program.setText(student.getStudentProgram());
        yearOfStudy.setText(String.valueOf(student.getStudentYearOfStudy()));

//        button.setText(contact.isOnline() ? "Message" : "Offline");
//        button.setEnabled(contact.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

