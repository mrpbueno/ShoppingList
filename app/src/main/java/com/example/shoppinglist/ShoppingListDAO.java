package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDAO {

    public static void create(ShoppingList list, Context context) {
        ContentValues values = new ContentValues();
        values.put("name", list.getName());
        values.put("quantity", list.getQuantity());
        values.put("price", list.getPrice());
        values.put("status", 0);
        DbHandler handler = new DbHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        db.insert("list", null, values);
        db.close();
    }

    public static void delete(int id, Context context) {
        DbHandler handler = new DbHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        db.delete("list", " id = " + id , null);
        db.close();
    }

    public static void update(ShoppingList list, Context context) {
        ContentValues values = new ContentValues();
        values.put("name", list.getName());
        values.put("quantity", list.getQuantity());
        values.put("price", list.getPrice());
        values.put("status", list.getStatus());
        DbHandler handler = new DbHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        db.update("list", values, " id = " + list.getId() , null );
        db.close();
    }

    public static List<ShoppingList> getAll(Context context) {
        List<ShoppingList> list = new ArrayList<>();
        DbHandler handler = new DbHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM list ORDER BY name", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(cursor.getInt( 0));
                shoppingList.setName(cursor.getString(1));
                shoppingList.setQuantity(cursor.getInt(2));
                shoppingList.setPrice(cursor.getLong(3));
                shoppingList.setStatus(cursor.getInt(4) == 1);
                list.add(shoppingList);
            } while(cursor.moveToNext());
        }
        return list;
    }

    public static ShoppingList getById(Context context, int id) {
        DbHandler handler = new DbHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM list WHERE id = " + id, null );
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setId(cursor.getInt( 0));
            shoppingList.setName(cursor.getString(1));
            shoppingList.setQuantity(cursor.getInt(2));
            shoppingList.setPrice(cursor.getLong(3));
            shoppingList.setStatus(cursor.getInt(4) == 1);
            return shoppingList;
        } else {
            return null;
        }
    }

    public static void deleteAll(Context context) {
        DbHandler handler = new DbHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        db.execSQL("DELETE FROM list");
        db.execSQL("VACUUM");
        db.close();
    }

    public static void setStatus(Context context, int id, boolean status) {
        ContentValues values = new ContentValues();
        values.put("status", status);
        DbHandler handler = new DbHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();
        db.update("list", values, " id = " + id , null );
        db.close();
    }
}
