package com.example.shoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blackcat.currencyedittext.CurrencyEditText;

public class FormListActivity extends AppCompatActivity {

    private EditText eName, eQuantity;
    private CurrencyEditText ePrice;
    private Button btnSave;
    private String action;
    private ShoppingList list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list);

        eName = findViewById(R.id.eName);
        eQuantity = findViewById(R.id.eQuantity);
        ePrice = findViewById(R.id.ePrice);
        btnSave = findViewById(R.id.btnSave);
        action = getIntent().getStringExtra("action");
        if (action.equals("edit")) {
            loadForm();
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void loadForm() {
        int id = getIntent().getIntExtra("id",0);
        if (id != 0) {
            list = ShoppingListDAO.getById(this,id);
            eName.setText(list.getName());
            eQuantity.setText(String.valueOf(list.getQuantity()));
            ePrice.setText(String.valueOf(list.getPrice()));
        }
    }

    private void save() {
        if (eName.getText().toString().isEmpty() || eQuantity.getText().toString().isEmpty()) {
            Toast.makeText(this,R.string.complete_all_fields,Toast.LENGTH_SHORT).show();
        } else {
            if (action.equals("new")) {
                list = new ShoppingList();
            }

            list.setName(eName.getText().toString());
            list.setQuantity(Integer.parseInt(eQuantity.getText().toString()));
            if (ePrice.getText().toString().isEmpty()) {
                list.setPrice((long) 0);
            } else {
                list.setPrice(ePrice.getRawValue());
            }

            if (action.equals("edit")) {
                ShoppingListDAO.update(list, this);
                finish();
            } else {
                ShoppingListDAO.create(list, this);
                finish();
            }
        }
    }
}
