package DAL;

import android.provider.BaseColumns;

/**
 * Created by Aviran on 07/05/16.
 */
public final class DreamContract {
    public DreamContract() {}

    public static abstract class Dream implements BaseColumns {
        public static final String DREAM_TABLE_NAME = "dreamDiary";
        public static final String COLUMN_NAME_DREAM_TITLE = "title";
        public static final String COLUMN_NAME_DREAM_CONTENT = "dreamContent";
    }
}
