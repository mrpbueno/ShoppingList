package com.example.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvLists;
    private List<ShoppingList> shoppingLists;
    private AdapterShoppingList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormListActivity.class);
                intent.putExtra("action", "new");
                startActivity(intent);
            }
        });



        lvLists = findViewById(R.id.lvLists);
        loadShoppingList();
        configListView();
    }

    private void loadShoppingList() {
        shoppingLists = ShoppingListDAO.getAll(this);
        adapter = new AdapterShoppingList(this, shoppingLists);
        lvLists.setAdapter(adapter);
    }

    private void configListView() {
        lvLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingList select = shoppingLists.get(position);
                Intent intent = new Intent(MainActivity.this, FormListActivity.class);
                intent.putExtra("action","edit");
                intent.putExtra("id", select.getId());
                startActivity(intent);
            }
        });

        lvLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingList select = shoppingLists.get(position);
                delete(select);
                return true;
            }
        });
    }

    private void delete(ShoppingList list) {
        AlertDialog.Builder warning = new AlertDialog.Builder(this);
        warning.setIcon(android.R.drawable.ic_input_delete);
        warning.setTitle(R.string.warning);
        warning.setMessage(R.string.delete_confirm);
        warning.setNeutralButton(R.string.cancel, null);
        warning.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShoppingListDAO.delete(list.getId(),MainActivity.this);
                loadShoppingList();
            }
        });
        warning.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadShoppingList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_clear_all) {
            ShoppingListDAO.deleteAll(this);
            loadShoppingList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        ShoppingListDAO.setStatus(this, view.getId(), checked);
    }
}