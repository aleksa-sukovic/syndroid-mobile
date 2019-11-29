/*
   This gist is written by: Aleksa Sukovic (https://github.com/aleksa-sukovic)
*/

package com.aleksa.syndroid.library.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.Random;

public class PermissionManager
{

    /**
     * Determines if permissions are granted or not. You should us this method instead
     * of writing logic code inside Activities built in onRequestPermissionsResult method
     *
     * @param requestCode
     *      RequestCode obtained from checkMultiple method
     * @param permissionCode
     *      Code given to Android built-in check permissions method
     * @param permissions
     *      Requested permissions
     * @param grantResults
     *      Grant results
     * @return
     *      True if permissions request with given code is granted, false otherwise
     */
    public boolean permissionsGranted(int requestCode , int permissionCode, String[] permissions, int[] grantResults)
    {
        if(requestCode != permissionCode) {
            return false;
        }

        for(int result : grantResults) {
            if(result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if multiple permissions are denied or not. You should us this method instead
     * of writing logic code inside Activities built in onRequestPermissionsResult method
     *
     * @param requestCode
     *      RequestCode obtained from checkMultiple method
     * @param permissionCode
     *      Code given to Android built-in check permissions method
     * @param permissions
     *      Requested permission
     * @param grantResults
     *      Grant results
     * @return
     *      True if permissions request with given code is denied, false otherwise
     */
    public boolean permissionsDenied(int requestCode, int permissionCode, String[] permissions, int[] grantResults)
    {
        if(requestCode != permissionCode) {
            return false;
        }

        for(int result : grantResults) {
            if(result != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines if permission is granted or not. You should us this method instead
     * of writing logic code inside Activities built in onRequestPermissionsResult method
     *
     * @param requestCode
     *      RequestCode obtained from check method
     * @param permissionCode
     *      Code given to Android built-in check permission method
     * @param permissions
     *      Requested permission
     * @param grantResults
     *      Grant results
     * @return
     *      True if permission request with given code is granted, false otherwise
     */
    public boolean permissionGranted(int requestCode , int permissionCode, String[] permissions, int[] grantResults)
    {
        if(requestCode != permissionCode) {
            return false;
        }

        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Determines if permission is granted or not. You should us this method instead
     * of writing logic code inside Activities built in onRequestPermissionsResult method
     *
     * @param requestCode
     *      RequestCode obtained from check method
     * @param permissionCode
     *      Code given to Android built-in check permission method
     * @param permissions
     *      Requested permission
     * @param grantResults
     *      Grant results
     * @return
     *      True if permission request with given code is denied, false otherwise
     */
    public boolean permissionDenied(int requestCode, int permissionCode, String[] permissions, int[] grantResults)
    {
        if(requestCode != permissionCode) {
            return false;
        }

        return grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Check if single permission is granted
     *
     * @param activity
     *      Activity that is checking permissions
     * @param permission
     *      Permission to be checked
     * @return
     *      True if permission is granted, false otherwise
     */
    public boolean check(Activity activity, String permission)
    {
        int result = ContextCompat.checkSelfPermission(activity, permission);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks multiple permissions at once
     *
     * @param activity
     *      Activity that is checking permissions
     * @param permissions
     *      Array of permissions to be checked
     * @return
     *      True if all permissions from given permissions array are granted, false otherwise
     */
    public boolean checkMultiple(Activity activity, String[] permissions)
    {
        for(String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(activity, permission);

            if(result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * Makes permission request
     *
     * @param activity
     *      Activity that is making permission request
     * @param permission
     *      Permission to be requested
     * @return
     *      RequestID passed to Androids built in request permissions method
     */
    public int request(Activity activity, String permission)
    {
        int permissionRequestCode = generateRequestCode();
        ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionRequestCode);
        return permissionRequestCode;
    }

    /**
     * Makes request for multiple permissions
     *
     * @param activity
     *      Activity that is making permission request
     * @param permissions
     *      Array of permissions to be requested
     * @return
     *      RequestID passed to Androids built in request permissions method
     */
    public int requestMultiple(Activity activity, String[] permissions)
    {
        int permissionRequestCode = generateRequestCode();
        ActivityCompat.requestPermissions(activity, permissions, permissionRequestCode);
        return permissionRequestCode;
    }

    /**
     * Utility method for generating request codes
     *
     * @return
     *      Random integer ranging from 1000 to 5000
     */
    private int generateRequestCode()
    {
        Random random = new Random();
        return random.nextInt(4000) + 1000;
    }
}
