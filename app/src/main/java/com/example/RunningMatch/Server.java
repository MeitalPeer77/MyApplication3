package com.example.RunningMatch;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class Server {
    //******************  Firebase Objects ****************//
    /* The authentication object of the app */
    private FirebaseAuth mAuth;

    /* Listener object which waits for authentication orocess from the user*/
    private FirebaseAuth.AuthStateListener mAuthListener;

    /* Represents the database */
    private DatabaseReference databaseUsers;

    /* Represents the FireStore database */
    FirebaseFirestore fireStoreDatabase;
    public Server(){

        fireStoreDatabase = FirebaseFirestore.getInstance();

        //todo:delete if using firestore
        databaseUsers = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

    }


    /**
     * Gets the user from the DB and downloads it to a User object
     */
    public void getUser(String email, final User[] user) {
        final String cleanEmail = email.replace(".", "");
        DocumentReference docRef = fireStoreDatabase.collection("users").document(cleanEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userProfile = task.getResult();
                    if (userProfile != null && userProfile.exists()) {
                        user[0] = userProfile.toObject(User.class);
                        System.out.println(user[0]);
                    }
                }
                else{
                    System.out.println(task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "ERROR GETTING USER");
                System.out.println(e);
            }
        });

    }
}
