package com.codedev.demo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class DbSeeder {

    public static void seed(FirebaseFirestore firestore) {
        // Warning: This will overwrite data.

        WriteBatch batch = firestore.batch();

        // 1. Create 20 Categories
        Map<String, Object> categoriesData = new HashMap<>();
        categoriesData.put("COUNT", 20L);

        String[] categoryNames = {
                "General Knowledge", "Science", "History", "Geography", "Technology",
                "Sports", "Literature", "Movies", "Music", "Art",
                "Mathematics", "Physics", "Chemistry", "Biology", "Astronomy",
                "Politics", "Economics", "Philosophy", "Psychology", "Sociology"
        };

        for (int i = 0; i < 20; i++) {
            String catId = "cat_" + (i + 1);
            categoriesData.put("CAT" + (i + 1) + "_ID", catId);
            categoriesData.put("CAT" + (i + 1) + "_NAME", categoryNames[i]);

            // Create Category Document
            createCategory(firestore, catId, 5); // 5 Sets per category
        }

        DocumentReference catDoc = firestore.collection("QUIZ").document("Categories");
        batch.set(catDoc, categoriesData);

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("DB_SEEDER", "Categories seeded successfully.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("DB_SEEDER", "Error seeding categories", e);
            }
        });
    }

    private static void createCategory(FirebaseFirestore firestore, String catId, int numSets) {
        Map<String, Object> catData = new HashMap<>();
        catData.put("SETS", (long) numSets);

        for (int i = 0; i < numSets; i++) {
            String setId = catId + "_set_" + (i + 1);
            catData.put("SET" + (i + 1) + "_ID", setId);

            // Create Set Collection (Questions)
            createSet(firestore, catId, setId, 20); // 20 Questions per set for better randomization
        }

        firestore.collection("QUIZ").document(catId).set(catData);
    }

    private static void createSet(FirebaseFirestore firestore, String catId, String setId, int numQuestions) {
        // Create QUESTIONS_LIST document
        Map<String, Object> qListData = new HashMap<>();
        qListData.put("COUNT", String.valueOf(numQuestions)); // The app expects count as String in Questions List based on previous code analysis

        for (int i = 0; i < numQuestions; i++) {
            String qId = "q_" + (i + 1);
            qListData.put("Q" + (i + 1) + "_ID", qId);
            createQuestion(firestore, catId, setId, qId, i + 1);
        }

        firestore.collection("QUIZ").document(catId)
                .collection(setId).document("QUESTIONS_LIST")
                .set(qListData);
    }

    private static void createQuestion(FirebaseFirestore firestore, String catId, String setId, String qId, int index) {
        Map<String, Object> qData = new HashMap<>();
        qData.put("QUESTION", "This is sample question " + index + " for " + catId + " - " + setId);
        qData.put("A", "Option A");
        qData.put("B", "Option B");
        qData.put("C", "Option C (Correct)");
        qData.put("D", "Option D");
        qData.put("ANSWER", "3"); // C is correct

        firestore.collection("QUIZ").document(catId)
                .collection(setId).document(qId)
                .set(qData);
    }
}
