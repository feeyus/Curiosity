package com.example.curiosity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddPet extends AppCompatActivity {

    ImageButton back_button;
    ImageButton settings_button;

    FirebaseAuth fAuth; //to get user id
    FirebaseFirestore fStore; //for data retrieval
    String userid;

    TextView petname;
    TextView pettype;
    TextView petbreed;
    TextView dob;
    TextView trackerno;

    Button addpetButton;

    String numberofpets;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpet);

        back_button=(ImageButton)findViewById(R.id.back_button);
        settings_button=(ImageButton)findViewById(R.id.settings_button);
        petname = findViewById(R.id.petName);
        pettype = findViewById(R.id.petType);
        petbreed = findViewById(R.id.petBreed);
        dob = findViewById(R.id.petDOB);
        trackerno = findViewById(R.id.trackerNumber);
        addpetButton =findViewById(R.id.addpetButton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //getting docreference
        userid = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userid);

        //pulling data from db
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                //getting number of pets from the db and setting it to variable
                numberofpets = documentSnapshot.getString("Number of Pets");
            }
        });

        //MaterialDatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        final MaterialDatePicker materialDatePicker = builder.build();
        //calendar
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dob.setText(materialDatePicker.getHeaderText());
            }
        });

        addpetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update number of pets
                Map<String, String> userMap =new HashMap<>();
                int nop = Integer.parseInt(numberofpets) + 1;
                userMap.put("Number of Pets", Integer.toString(nop));
                DocumentReference documentReference = fStore.collection("Users").document(userid);
                documentReference.set(userMap, SetOptions.merge());

                //add to pet collection
                Map<String, String> petMap =new HashMap<>();
                String uniqueId = UUID.randomUUID().toString();
                petMap.put("Pet ID",uniqueId);
                petMap.put("Pet Name",petname.getText().toString());
                petMap.put("Pet Type",pettype.getText().toString());
                petMap.put("Pet Breed",petbreed.getText().toString());
                petMap.put("Pet DOB",dob.getText().toString());
                petMap.put("Tracker Number",trackerno.getText().toString());
                petMap.put("Status","Found");

                documentReference = fStore.collection("Users")
                                .document(userid)
                                .collection("Pets")
                                .document(""+uniqueId);
                documentReference.set(petMap, SetOptions.merge());

                //updating petdocnames
                documentReference = fStore.collection("Users")
                        .document(userid)
                        .collection("Pets")
                        .document("PetDocNames");
                Map<String, String> petDocName =new HashMap<>();
                petDocName.put("PetDocName"+nop,uniqueId);
                documentReference.set(petDocName, SetOptions.merge());

                Intent intent=new Intent(AddPet.this,Pet.class);
                startActivity(intent);
            }
        });
        //on press settings button
        settings_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(AddPet.this,Settings.class);
                startActivity(intent);
            }
        });

        //on press go back
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(AddPet.this,Pet.class);
                startActivity(intent);
            }
        });


    }
}