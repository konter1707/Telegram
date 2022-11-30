package org.telegram.SQLite.Fork;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryDialog {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long dialogId;
    public boolean isPinned;
}
