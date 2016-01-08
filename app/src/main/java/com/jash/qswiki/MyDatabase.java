package com.jash.qswiki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jash.qswiki.entities.ArticleItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jash
 * Date: 16-1-8
 * Time: 下午4:04
 */
public class MyDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "qswiki.db";
    private static final int VERSION = 1;
    private static final String ARTICLE_TABLE = "article";
    private static final String ARTICLE_ID = "_id";
    private static final String ARTICLE_CONTENT = "content";
    private static final String ARTICLE_IMAGE = "image";
    private static final String ARTICLE_USER_ID = "user_id";
    private static final String USER_TABLE = "user";
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "name";
    private static final String USER_ICON = "icon";
    private static MyDatabase database;

    public static MyDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(MyDatabase database) {
        MyDatabase.database = database;
    }

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Class<ArticleItem> item = ArticleItem.class;
        StringBuilder builder = builderSQL(item);
        db.execSQL(builder.toString());
        db.execSQL("CREATE TABLE " + ARTICLE_TABLE + "("
                + ARTICLE_ID + " INTEGER PRIMARY KEY,"
                + ARTICLE_CONTENT + " TEXT,"
                + ARTICLE_IMAGE + " TEXT,"
                + ARTICLE_USER_ID + " INTEGER)"
        );
        db.execSQL("CREATE TABLE " + USER_TABLE + "("
                + USER_ID + " INTEGER PRIMARY KEY,"
                + USER_NAME + " TEXT,"
                + USER_ICON + " TEXT)"
        );
    }

    private StringBuilder builderSQL(Class<?> item) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE " + item.getSimpleName() + " (");
        try {
            Field id = item.getDeclaredField("id");
            if (id != null) {
                builder.append(id.getName() + " INTEGER PRIMARY KEY,");
            } else {
                builder.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
            }
            Field[] fields = item.getDeclaredFields();
            for (Field field : fields) {
                if (!field.equals(id)){
                    builder.append(field.getName());
                    Class<?> type = field.getType();
                    if (type.isAssignableFrom(String.class)) {
                        builder.append(" TEXT,");
                    } else if (type.isAssignableFrom(Number.class)){
                        builder.append(" INTEGER,");
                    } else {
                        StringBuilder builder1 = builderSQL(type);
                        getWritableDatabase().execSQL(builder1.toString());
                        builder.append("_id INTEGER,");
                    }
                }
            }
            builder.delete(builder.length() - 2, builder.length() - 1);
            builder.append(")");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return builder;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveAll(List<ArticleItem> list){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        for (ArticleItem item : list) {
            save(db, item);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public void save(ArticleItem item){
        save(getWritableDatabase(), item);
    }
    private void save(SQLiteDatabase db, ArticleItem item) {
        ArticleItem.UserEntity user = item.getUser();
        if (user != null) {
            ContentValues cv = new ContentValues();
            cv.put(USER_ID, user.getId());
            cv.put(USER_NAME, user.getLogin());
            cv.put(USER_ICON, user.getIcon());
            db.replace(USER_TABLE, null, cv);
        }
        ContentValues cv = new ContentValues();
        cv.put(ARTICLE_ID, item.getId());
        cv.put(ARTICLE_CONTENT, item.getContent());
        cv.put(ARTICLE_IMAGE, item.getImage());
        if (user != null) {
            cv.put(ARTICLE_USER_ID, user.getId());
        }
        db.replace(ARTICLE_TABLE, null, cv);
    }
    public List<ArticleItem> findAll(String selection, String[] selectionArgs, String orderBy, int limit){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ARTICLE_TABLE, null, selection, selectionArgs, null, null, orderBy, limit + "");
        List<ArticleItem> list = new ArrayList<>();
        while (cursor.moveToNext()){
            ArticleItem item = new ArticleItem();
            item.setContent(cursor.getString(cursor.getColumnIndex(ARTICLE_CONTENT)));
            item.setImage(cursor.getString(cursor.getColumnIndex(ARTICLE_IMAGE)));
            int userID = cursor.getInt(cursor.getColumnIndex(ARTICLE_USER_ID));
            if (userID != 0) {
                Cursor query = db.query(USER_TABLE, null, USER_ID + " = ?", new String[]{String.valueOf(userID)}, null, null, null, "1");
                query.moveToFirst();
                ArticleItem.UserEntity user = new ArticleItem.UserEntity();
                user.setId(query.getInt(query.getColumnIndex(USER_ID)));
                user.setLogin(query.getString(query.getColumnIndex(USER_NAME)));
                user.setIcon(query.getString(query.getColumnIndex(USER_ICON)));
                item.setUser(user);
            }
            list.add(item);
        }
        return list;
    }
}
