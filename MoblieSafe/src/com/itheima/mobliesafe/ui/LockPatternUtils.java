package com.itheima.mobliesafe.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

import com.itheima.mobliesafe.utils.PrefUtils;

public class LockPatternUtils {
	
	private final static String KEY_LOCK_PWD = "lock_pwd";
	private static Context mContext;
	
	
	
	 public LockPatternUtils(Context context) {
	        mContext = context;
	 }
	
	 /**
     * Deserialize a pattern.
     * @param string The pattern serialized with {@link #patternToString}
     * @return The pattern.
     */
    public static List<LockPatternView.Cell> stringToPattern(String string) {
        List<LockPatternView.Cell> result = new ArrayList<LockPatternView.Cell>();

        final byte[] bytes = string.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            result.add(LockPatternView.Cell.of(b / 3, b % 3));
        }
        return result;
    }

    /**
     * Serialize a pattern.
     * @param pattern The pattern.
     * @return The pattern in string form.
     */
    public static String patternToString(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return "";
        }
        final int patternSize = pattern.size();

        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        return Arrays.toString(res);
    }
    
    public void saveLockPattern(List<LockPatternView.Cell> pattern){
    	PrefUtils.setString(mContext, KEY_LOCK_PWD, patternToString(pattern));
    }
    
    public String getLockPaternString(){
    	return PrefUtils.getString(mContext, KEY_LOCK_PWD,"");
    }
    
    public int checkPattern(List<LockPatternView.Cell> pattern) {
    	String stored = getLockPaternString();
    	if(!stored.isEmpty()){
    		return stored.equals(patternToString(pattern))?1:0;
    	}
    	return -1;
    }

    public void clearLock() {
    	saveLockPattern(null);
    }
  

}