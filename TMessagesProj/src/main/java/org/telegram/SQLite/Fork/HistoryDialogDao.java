package org.telegram.SQLite.Fork;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDialogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryDialog historyDialog);

    @Query("SELECT * FROM historydialog")
    List<HistoryDialog> getAll();

    @Delete
    void deleteAll(List<HistoryDialog> historyDialogs);

    @Query("DELETE FROM historydialog WHERE dialogId = :dialogId")
    void deleteById(long dialogId);

    @Delete
    void delete(HistoryDialog historyDialog);
}
