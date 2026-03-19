package com.example.activity_5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button btnSearch, btnClear;
    private ImageView pokemonImage;
    private TextView txtId, txtName, txtType, txtHP, txtAttack, txtDefense, txtSpAttack, txtSpDefense, txtSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //components
        searchInput = findViewById(R.id.searchInput);
        btnSearch = findViewById(R.id.btnSearch);
        btnClear = findViewById(R.id.btnClear);
        pokemonImage = findViewById(R.id.pokemonImage);

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtType = findViewById(R.id.txtType);
        txtHP = findViewById(R.id.txtHP);
        txtAttack = findViewById(R.id.txtAttack);
        txtDefense = findViewById(R.id.txtDefense);
        txtSpAttack = findViewById(R.id.txtSpAttack);
        txtSpDefense = findViewById(R.id.txtSpDefense);
        txtSpeed = findViewById(R.id.txtSpeed);


        btnSearch.setOnClickListener(v -> searchPokemon());
        btnClear.setOnClickListener(v -> clearData());
    }

    private void searchPokemon() {
        String name = searchInput.getText().toString().toLowerCase().trim();


        //pag empty, toast
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a Pokemon name!", Toast.LENGTH_SHORT).show();
            return;
        }


        new Thread(() -> {
            try {

                //get from api
                URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + name);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();


                InputStream inputStream = conn.getInputStream();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                String result = scanner.hasNext() ? scanner.next() : "";
                JSONObject json = new JSONObject(result);

                int id = json.getInt("id");
                String pName = json.getString("name").toUpperCase();


                JSONArray typesArray = json.getJSONArray("types");
                StringBuilder typeBuilder = new StringBuilder();
                for (int i = 0; i < typesArray.length(); i++) {
                    String typeName = typesArray.getJSONObject(i)
                            .getJSONObject("type")
                            .getString("name").toUpperCase();
                    typeBuilder.append(typeName);
                    if (i < typesArray.length() - 1) typeBuilder.append(" / ");
                }

               //gif

                JSONObject sprites = json.getJSONObject("sprites");
                JSONObject versions = sprites.getJSONObject("versions");
                JSONObject genV = versions.getJSONObject("generation-v");
                JSONObject blackWhite = genV.getJSONObject("black-white");
                JSONObject animated = blackWhite.getJSONObject("animated");
                String gifUrl = animated.getString("front_default");

              //stats
                JSONArray stats = json.getJSONArray("stats");
                int hp = stats.getJSONObject(0).getInt("base_stat");
                int atk = stats.getJSONObject(1).getInt("base_stat");
                int def = stats.getJSONObject(2).getInt("base_stat");
                int spa = stats.getJSONObject(3).getInt("base_stat");
                int spd = stats.getJSONObject(4).getInt("base_stat");
                int spe = stats.getJSONObject(5).getInt("base_stat");


                runOnUiThread(() -> {
                    txtId.setText("ID: " + id);
                    txtName.setText("Name: " + pName);
                    txtType.setText("Type: " + typeBuilder.toString());
                    txtHP.setText("HP: " + hp);
                    txtAttack.setText("Attack: " + atk);
                    txtDefense.setText("Defense: " + def);
                    txtSpAttack.setText("Sp. Attack: " + spa);
                    txtSpDefense.setText("Sp. Defense: " + spd);
                    txtSpeed.setText("Speed: " + spe);

                    // Load the GIF using Glide
                    Glide.with(MainActivity.this)
                            .asGif()
                            .load(gifUrl)
                            .placeholder(R.drawable.pokeball) // Show pokeball while loading
                            .error(R.drawable.pokeball)       // Show pokeball if GIF fails
                            .into(pokemonImage);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Pokemon not found or no animation!", Toast.LENGTH_SHORT).show();
                    clearData();
                });
            }
        }).start();
    }


    //clear
    private void clearData() {
        searchInput.setText("");
        txtId.setText("ID: ");
        txtName.setText("Name: ");
        txtType.setText("Type: ");
        txtHP.setText("HP: ");
        txtAttack.setText("Attack: ");
        txtDefense.setText("Defense: ");
        txtSpAttack.setText("Sp. Attack: ");
        txtSpDefense.setText("Sp. Defense: ");
        txtSpeed.setText("Speed: ");

    //default pokeball image
        pokemonImage.setImageResource(R.drawable.pokeball);
    }
}