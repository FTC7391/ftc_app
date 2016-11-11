package org.firstinspires.ftc.team7391;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by April on 10/8/15.
 *
 * Use PrintWriter methods, e.g.
 * print(var) println(var) printf(%s, ..) write(char[], offset, cnt) to output to
 * to output to  /storage/emulated/legacy/FTC7391/subdir/file
 *
 */
public class FTC7391PrintWriter extends PrintWriter{

    private static FileOutputStream filenameToStream(String dir, String filename) {

        FileOutputStream stream = null;

        try {
            //baseDir currently is /storage/emulated/legacy
            File baseDir = Environment.getExternalStorageDirectory();

            File path = new File(baseDir,"FIRST/FTC7391");
            path.mkdir();

            if (dir != null) {
                path = new File(path, dir);
                path.mkdir();
            }

            path = new File(path, filename);
            stream = new FileOutputStream(path);

        } catch (IOException e) {
            Log.e("Exception", "FTC7391 Debug File open failed: " + e.toString());
        }
        return stream;

    }

    public FTC7391PrintWriter(String dir, String filename) {
        super(filenameToStream(dir, filename));
    }

    public FTC7391PrintWriter(String filename) {

        super(filenameToStream(null, filename));
    }

}
