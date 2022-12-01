package org.telegram.SQLite.Fork;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDialogDao {
    @Insert()
    void insert(HistoryDialog historyDialog);

    @Query("SELECT * FROM historydialog")
    List<HistoryDialog> getAll();

    @Query("SELECT EXISTS(SELECT * FROM historydialog WHERE dialogId = :dialogId)")
    boolean isDialogId(long dialogId);

    @Query("UPDATE historydialog SET date = :date WHERE dialogId = :dialogId")
    void updateDate(long date, long dialogId);

    @Query("UPDATE historydialog SET isPinned = :isPinned WHERE dialogId = :dialogId")
    void update(boolean isPinned, long dialogId);

    @Delete
    void deleteAll(List<HistoryDialog> historyDialogs);

    @Query("DELETE FROM historydialog WHERE dialogId = :dialogId AND isPinned=:isPinned")
    void deleteById(long dialogId, boolean isPinned);

    @Delete
    void delete(HistoryDialog historyDialog);
}
