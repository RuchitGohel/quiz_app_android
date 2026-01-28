package com.codedev.demo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    private List<Questions> questionsList;

    public ScoreAdapter(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_question_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Questions question = questionsList.get(position);

        holder.questionText.setText("Q" + (position + 1) + ". " + question.getQuestion());

        String yourAns = "";
        switch (question.getSelectedAns()) {
            case 1: yourAns = question.getOptionA(); break;
            case 2: yourAns = question.getOptionB(); break;
            case 3: yourAns = question.getOptionC(); break;
            case 4: yourAns = question.getOptionD(); break;
            default: yourAns = "Unanswered"; break;
        }

        String correctAns = "";
        switch (question.getCorrectAns()) {
            case 1: correctAns = question.getOptionA(); break;
            case 2: correctAns = question.getOptionB(); break;
            case 3: correctAns = question.getOptionC(); break;
            case 4: correctAns = question.getOptionD(); break;
        }

        holder.yourAnswer.setText("Your Answer: " + yourAns);
        holder.correctAnswer.setText("Correct Answer: " + correctAns);

        if (question.getSelectedAns() == question.getCorrectAns()) {
            holder.status.setText("CORRECT");
            holder.status.setTextColor(Color.GREEN);
            holder.yourAnswer.setTextColor(Color.GREEN);
        } else if (question.getSelectedAns() == 0) {
            holder.status.setText("UNANSWERED");
            holder.status.setTextColor(Color.GRAY);
            holder.yourAnswer.setTextColor(Color.GRAY);
        } else {
            holder.status.setText("WRONG");
            holder.status.setTextColor(Color.RED);
            holder.yourAnswer.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionText, yourAnswer, correctAnswer, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_text);
            yourAnswer = itemView.findViewById(R.id.your_answer);
            correctAnswer = itemView.findViewById(R.id.correct_answer);
            status = itemView.findViewById(R.id.status);
        }
    }
}
