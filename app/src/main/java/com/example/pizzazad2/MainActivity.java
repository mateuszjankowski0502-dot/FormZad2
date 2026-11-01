package com.example.pizzazad2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editName, editPhone, editAddress;
    private Spinner spinnerPizza;
    private RadioGroup radioSize;
    private CheckBox checkCheese, checkHam, checkMushrooms,
            checkOlives, checkAgreement;
    private SeekBar seekQuantity;
    private TextView textQuantity, textExpressInfo;
    private Switch switchExpress;
    private Button buttonOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);

        spinnerPizza = findViewById(R.id.spinnerPizza);
        radioSize = findViewById(R.id.radioSize);

        checkCheese = findViewById(R.id.checkCheese);
        checkHam = findViewById(R.id.checkHam);
        checkMushrooms = findViewById(R.id.checkMushrooms);
        checkOlives = findViewById(R.id.checkOlives);
        checkAgreement = findViewById(R.id.checkAgreement);

        seekQuantity = findViewById(R.id.seekQuantity);
        textQuantity = findViewById(R.id.textQuantity);

        switchExpress = findViewById(R.id.switchExpress);
        textExpressInfo = findViewById(R.id.textExpressInfo);

        buttonOrder = findViewById(R.id.buttonOrder);

        seekQuantity.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress, boolean fromUser) {
                        textQuantity.setText("Ilość: " + (progress + 1));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

        switchExpress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                textExpressInfo.setVisibility(View.VISIBLE);
            } else {
                textExpressInfo.setVisibility(View.GONE);
            }
        });

        buttonOrder.setOnClickListener(v -> pokazPodsumowanie());
    }

    private String pobierzRozmiar() {
        int selectedId = radioSize.getCheckedRadioButtonId();

        if (selectedId == R.id.radioSmall) {
            return "Mała";
        } else if (selectedId == R.id.radioLarge) {
            return "Duża";
        } else {
            return "Średnia";
        }
    }

    private String pobierzDodatki() {
        StringBuilder dodatki = new StringBuilder();

        if (checkCheese.isChecked()) {
            dodatki.append("Dodatkowy ser, ");
        }

        if (checkHam.isChecked()) {
            dodatki.append("Szynka, ");
        }

        if (checkMushrooms.isChecked()) {
            dodatki.append("Pieczarki, ");
        }

        if (checkOlives.isChecked()) {
            dodatki.append("Oliwki, ");
        }

        if (dodatki.length() == 0) {
            return "Brak";
        }

        dodatki.setLength(dodatki.length() - 2);
        return dodatki.toString();
    }

    private void pokazPodsumowanie() {
        String imie = editName.getText().toString();
        String telefon = editPhone.getText().toString();
        String adres = editAddress.getText().toString();

        String pizza = spinnerPizza.getSelectedItem().toString();
        String rozmiar = pobierzRozmiar();
        String dodatki = pobierzDodatki();

        int ilosc = seekQuantity.getProgress() + 1;

        String dostawa = switchExpress.isChecked()
                ? "Ekspresowa"
                : "Standardowa";

        String podsumowanie =
                "Klient: " + imie +
                        "\nTelefon: " + telefon +
                        "\nAdres: " + adres +
                        "\nPizza: " + pizza +
                        "\nRozmiar: " + rozmiar +
                        "\nDodatki: " + dodatki +
                        "\nIlość: " + ilosc +
                        "\nDostawa: " + dostawa;

        Toast.makeText(this, podsumowanie, Toast.LENGTH_LONG).show();
    }
}