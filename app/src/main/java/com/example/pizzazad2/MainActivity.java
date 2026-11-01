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

import com.google.android.material.snackbar.Snackbar;

import android.app.AlertDialog;

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

    private boolean zamowienieZlozone = false;

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
            textExpressInfo.setVisibility(
                    isChecked ? View.VISIBLE : View.GONE
            );
        });

        buttonOrder.setOnClickListener(v -> sprawdzDane());
    }

    private void sprawdzDane() {
        String imie = editName.getText().toString().trim();
        String telefon = editPhone.getText().toString().trim();
        String adres = editAddress.getText().toString().trim();

        if (imie.isEmpty()) {
            editName.setError("Podaj imię i nazwisko");
            editName.requestFocus();
            return;
        }

        if (telefon.isEmpty()) {
            editPhone.setError("Podaj numer telefonu");
            editPhone.requestFocus();
            return;
        }

        if (adres.isEmpty()) {
            editAddress.setError("Podaj adres dostawy");
            editAddress.requestFocus();
            return;
        }

        if (!checkAgreement.isChecked()) {
            Toast.makeText(
                    this,
                    "Musisz zaakceptować warunki zamówienia",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        pokazPodsumowanie();
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

    private String przygotujPodsumowanie() {
        return "Klient: " + editName.getText().toString() +
                "\nTelefon: " + editPhone.getText().toString() +
                "\nAdres: " + editAddress.getText().toString() +
                "\nPizza: " + spinnerPizza.getSelectedItem().toString() +
                "\nRozmiar: " + pobierzRozmiar() +
                "\nDodatki: " + pobierzDodatki() +
                "\nIlość: " + (seekQuantity.getProgress() + 1) +
                "\nDostawa: " +
                (switchExpress.isChecked() ? "Ekspresowa" : "Standardowa");
    }

    private void pokazPodsumowanie() {
        String podsumowanie = przygotujPodsumowanie();

        new AlertDialog.Builder(this)
                .setTitle("Potwierdzenie zamówienia")
                .setMessage(podsumowanie + "\n\nCzy potwierdzasz zamówienie?")
                .setPositiveButton("Potwierdź", (dialog, which) -> {
                    zamowienieZlozone = true;

                    Snackbar.make(
                            findViewById(R.id.mainLayout),
                            "Zamówienie zostało złożone!",
                            Snackbar.LENGTH_LONG
                    ).setAction("Cofnij", v -> {
                        zamowienieZlozone = false;

                        Toast.makeText(
                                MainActivity.this,
                                "Zamówienie zostało cofnięte",
                                Toast.LENGTH_SHORT
                        ).show();
                    }).show();
                })
                .setNegativeButton("Anuluj", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}