package org.telegram.SQLite.Fork;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryDialog.class}, version = 1)
public abstract class ForkDataBase extends RoomDatabase {
    abstract public HistoryDialogDao historyDialogDao();
}

