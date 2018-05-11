package com.example.joshir.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.joshir.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

import static com.example.joshir.criminalintent.database.CrimeDbSchema.CrimeTable;

public class CrimeCursorWrapper extends CursorWrapper{
    public CrimeCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        int isRequiresPolice = getInt(getColumnIndex(CrimeTable.Cols.REQUIRES_POLICE));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setRequiresPolice(isRequiresPolice != 0);

        return crime;
    }
}
