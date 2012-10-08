package ch.zhaw.android.photobyintent;

import java.io.File;

import android.annotation.SuppressLint;
import android.os.Environment;

public final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

	@SuppressLint({ "NewApi", "NewApi" })
	@Override
	public File getAlbumStorageDir(String albumName) {
		// TODO Auto-generated method stub
		return new File(
		  Environment.getExternalStoragePublicDirectory(
		    Environment.DIRECTORY_PICTURES
	  ), 
		  albumName
		);
	}
}
