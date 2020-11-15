package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Profile extends AppCompatActivity {


    private String userId;
    private FirebaseUser user;
    private DatabaseReference reference;

    private Button LogOut;
    private EditText search_edit;
    private ImageView view_weather;
    private FloatingActionButton floating_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LogOut = (Button) findViewById(R.id.logout);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        final TextView greetingText = (TextView) findViewById(R.id.greeting);

        final TextView view_town = (TextView) findViewById(R.id.town);
        view_town.setText("");
        final TextView view_temp = (TextView) findViewById(R.id.temp);
        view_temp.setText("");
        final TextView view_desc = (TextView) findViewById(R.id.desc);
        view_desc.setText("");

        view_weather = findViewById(R.id.wheather_image);
        search_edit = findViewById(R.id.search_edit);
        floating_search = findViewById(R.id.floating_search);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String name = userProfile.name;
                    greetingText.setText("Welcome " + name + " !");
                    floating_search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //hide keyboard
                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(), 0);
                            api_key(String.valueOf(search_edit.getText()));
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

            private void api_key(final String City) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("#")
                        .get()
                        .build();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
                    Response response = client.newCall(request).execute();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String responseData = response.body().string();
                            try {
                                JSONObject json = new JSONObject(responseData);
                                JSONArray array = json.getJSONArray("weather");
                                JSONObject object = array.getJSONObject(0);

                                String description = object.getString("description");
                                String icons = object.getString("icon");

                                JSONObject temp = json.getJSONObject("main");
                                Double Temp = temp.getDouble("temp");

                                setText(view_town, City);

                                String temps = Math.round(Temp) + " °C";
                                setText(view_temp, temps);
                                setText(view_desc, description);
                                setImage(view_weather, icons);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void setText(final TextView text, final String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(value);
                    }
                });
            }

            private void setImage(final ImageView imageView, final String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (value) {
                            case "01d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                                break;
                            case "01n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                                break;
                            case "02d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                                break;
                            case "02n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                                break;
                            case "03d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                                break;
                            case "03n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                                break;
                            case "04d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                                break;
                            case "04n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                                break;
                            case "09d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                                break;
                            case "09n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                                break;
                            case "10d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                                break;
                            case "10n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                                break;
                            case "11d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                                break;
                            case "11n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                                break;
                            case "13d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                                break;
                            case "13n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                                break;
                            default:
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.wheather));

                        }
                    }
                });

            }


        });


    }


}