package com.openclassrooms.realestatemanager.utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.openclassrooms.realestatemanager.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    public static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars Prix en dollars
     * @return Prix en Euros
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     * @param euros Prix en euros
     * @return Prix en dollars
     */
    public static int convertEuroToDollar(int euros) { return (int) Math.round(euros / 0.812);}

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return Date au format dd/MM/yyyy
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return Statut de la connexion
     */
    public static Boolean isInternetAvailable(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    public static boolean checkPermission(Context context, String permission) {
        int result = context.checkSelfPermission(permission);
        return result != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Context context, String permission, int requestCode) throws Exception {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, permission)) {
            if(requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE)
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This application requires access to the storage of your device to be able to display the photos of the various properties.")
                    .setPositiveButton("Next", (dialog, which) -> ActivityCompat.requestPermissions((Activity) context,
                            new String[] {permission}, requestCode))
                    .create().show();
            else if (requestCode == CAMERA_REQUEST_CODE)
                new AlertDialog.Builder(context)
                        .setTitle("Permission needed")
                        .setMessage("A camera access permission is needed in order to take a picture with the camera of your device.")
                        .setPositiveButton("Next", (dialog, which) -> ActivityCompat.requestPermissions((Activity) context,
                                new String[] {permission}, requestCode))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create().show();
        } else {
            try {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permission},
                        requestCode);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static void createNotification(Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("New property added !")
                .setContentText("A new property has been successfully added to the database.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "added";
            String description = "notification channel for adding a property";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
