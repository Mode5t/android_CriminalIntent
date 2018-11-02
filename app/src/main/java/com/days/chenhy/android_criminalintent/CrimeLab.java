package com.days.chenhy.android_criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
    private List<Crime> crimes;


    private CrimeLab() {
        crimes = new ArrayList<>();
    }

    public static CrimeLab get(Context context) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab();
        }
        return crimeLab;
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public void setCrimes(List<Crime> crimes) {
        this.crimes = crimes;
    }

    public Crime getCrime(UUID uuid) {
        for (Crime crime : crimes) {
            if (crime.getmId().equals(uuid))
                return crime;
        }
        return null;
    }
}
