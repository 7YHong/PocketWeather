package cn.qiyanghong.pocketweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import cn.qiyanghong.pocketweather.entity.City;

/**
 * Created by QYH on 2016/5/8.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "pocketweather.db";

    private static DBHelper mInstance;

    private Dao<City,Integer> cityDao;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static synchronized DBHelper getHelper(Context context) {
        if (mInstance == null) {
            synchronized (DBHelper.class) {
                if (mInstance == null)
                    mInstance = new DBHelper(context);
            }
        }
        return mInstance;
    }

    public Dao<City,Integer> getCityDao() throws SQLException {
        if (cityDao==null)
            cityDao=getDao(City.class);
        return cityDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, City.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


}
