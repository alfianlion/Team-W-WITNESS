package sg.edu.np.mad.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class userDBhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB";
    public static final String TABLE_USER = "Users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_Username = "username";
    public static final String COLUMN_PW = "password";

    public userDBhandler(Context context, String name,SQLiteDatabase.CursorFactory factory,
                         int version){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //do oncreate code
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        //do upgrade code
    }
}
